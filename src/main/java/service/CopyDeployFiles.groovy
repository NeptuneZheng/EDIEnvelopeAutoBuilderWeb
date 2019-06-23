package service

import org.eclipse.jetty.util.ConcurrentArrayQueue
import pojo.CopyCell
import pojo.DeployFileInfo
import service.multithreading.SingleFileCopyThread

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * https://www.cnblogs.com/wanqieddy/p/3853863.html
 * https://www.cnblogs.com/stonefeng/p/5967451.html
 */

class CopyDeployFiles {
    static final Queue<CopyCell> QUEUE = new ConcurrentArrayQueue<>()

    static volatile boolean status = true


    public static void main(String[] args) {

        long startTime = System.currentTimeMillis()

        ExecutorService modelExecutor = Executors.newFixedThreadPool(3)
        ExecutorService copyExecutor = Executors.newFixedThreadPool(2)


        String rootPath = "D:\\1_B2BEDI_Revamp\\DOCUIF\\OUT_310"
        String[] tps = ['NESTLE','RHODIA','CANTIRE','BRIDGESTONE','CEVA','GOODYEAR','DAELIM','CHH','ASCENA','TOFASCO','HEINZ','EXXONMOBIL','HASHASUK']

        String targetSqlPath = "D:\\A\\Week25-DOCUIF-V2\\sql"
        String targetApiPath = "D:\\A\\Week25-DOCUIF-V2\\api"

        //---step1 copy model to all TPs
        String modleTP = "HELLMAN"
        String modleFolderPath = "D:\\1_B2BEDI_Revamp\\DOCUIF\\OUT_310\\HELLMAN\\resources\\deploy"
        String targetFolder = "D:\\1_B2BEDI_Revamp\\DOCUIF\\OUT_310"
//
        modelToAll(modleFolderPath, modleTP, targetFolder, tps, modelExecutor)
        //--- step2 copy ready SQL/JSON to a folder
//        finalProdFilesCopy(rootPath, tps, targetSqlPath, targetApiPath)

        QueueTaskExecutor(copyExecutor)

        if(copyExecutor.terminated){
            println("--------all job finished------------ ")
            long ednTime = System.currentTimeMillis()
            println("--------------------")
            println("***program start at: " + startTime)
            println("***program end at: " + ednTime)
            println("***total cost: " + (ednTime - startTime)/1000 + "s")
            println("--------------------")
        }
    }

    public static void modelToAll(String modleFolderPath, String modleTP, String targetFolder, String[] tps, ExecutorService executor) {
        List<String> modelFiles = new ArrayList<>()
        collectFolderFiles(modelFiles, modleFolderPath)
        if(modelFiles.size() == 0){
            throw new Exception("model files can't be null")
        }
        new Thread({
            copyModelFile(modleTP, modelFiles, targetFolder, tps, executor)
            executor.shutdown()

            executor.awaitTermination(10,TimeUnit.MINUTES)
            if(executor.terminated){
                println("modelToAll executor.shutdown()")
                status = false
            }
        }).start()

    }

    public static void copyModelFile(String modelTP,List<String> modelFiles, String targetRoot, String[] tps, ExecutorService executor){
        CopyCell cell
        String target = ""
        File root = new File(targetRoot)
        if(tps.contains(root.getName())){
//            println(executor)
            executor.execute(new Thread({
                println("copy for: " + root.getName())
                for(String model : modelFiles){
                    target = model.replaceAll("(.*\\\\|/)${modelTP}","").replaceAll("${modelTP}",root.getName())
                    cell = new CopyCell(model, targetRoot+target)
                    boolean result = QUEUE.offer(cell)
                    println(result.toString() + "---" + QUEUE.size() + "add task to QUEUE: " + cell)

//                    Thread.sleep(2000)

                }
            }))
        }else if(root.isDirectory()){
            println("scanning target path at: " + root.getName())
            root.listFiles().each {
                copyModelFile(modelTP,modelFiles,it.getAbsolutePath(),tps,executor)
            }
        }
    }

    public static void finalProdFilesCopy(String rootPath, String[] tps, String targetSqlPath, String targetApiPath) {
        DeployFileInfo info = new DeployFileInfo(rootPath, tps, targetSqlPath, targetApiPath)

        new File(targetSqlPath).mkdirs()
        new File(targetApiPath).mkdirs()

        new Thread({
            info.extraCopyCellToQueue(QUEUE, rootPath)
            status = false
        }).start()
    }

    public static void QueueTaskExecutor(ExecutorService executor) {
        CopyCell Cell
        while (true) {
            if (QUEUE.size() == 0 && !status) {
                println("QueueTaskExecutor end serve, goodbye")
                break
            }
            Cell = QUEUE.poll()
            if (Cell) {
                executor.submit(new SingleFileCopyThread(new CopyCell(Cell.getOriPath(), Cell.getDesPath())))
            }

        }
        executor.shutdown()
        executor.awaitTermination(20,TimeUnit.MINUTES)
    }

    private static void collectFolderFiles(List<String> list, String path){
        File file = new File(path)
        if((file.isDirectory() && file.listFiles().size() == 0) || file.isFile()){
            list.add(path)
        }else {
            file.listFiles().each {
                collectFolderFiles(list,it.getAbsolutePath())
            }
        }
    }

}
