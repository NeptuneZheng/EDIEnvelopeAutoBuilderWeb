package service.multithreading

import pojo.CopyCell

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

class SingleFileCopyThread implements Callable {
    private CopyCell cell

    SingleFileCopyThread() {
    }

    SingleFileCopyThread(CopyCell cell) {
        this.cell = cell
    }

    Object call() throws Exception {
        copy()
        return null
    }

    private void copy(){
//        println(Thread.currentThread().getName() + " working now ")
        File defFile= new File(this.cell.desPath)
        if(defFile.isDirectory() && !defFile.exists()){
            println(Thread.currentThread().getName() + " is creating Folder " + defFile.getName())
            defFile.mkdirs()
        }else if(!new File(defFile.getParent()).exists()){
            println(Thread.currentThread().getName() + " is creating parent Folder " + defFile.getParent())
            new File(defFile.getParent()).mkdirs()
            println(Thread.currentThread().getName() + "---" + this.cell.oriPath + "---" + this.cell.desPath)
            Files.copy(Paths.get(this.cell.oriPath),Paths.get(this.cell.desPath),StandardCopyOption.REPLACE_EXISTING)
        }else {
            println(Thread.currentThread().getName() + "---" + this.cell.oriPath + "---" + this.cell.desPath)
            Files.copy(Paths.get(this.cell.oriPath),Paths.get(this.cell.desPath),StandardCopyOption.REPLACE_EXISTING)
        }

    }


    public static void main(String[] args) {

        long start = System.currentTimeMillis()
        ExecutorService service = Executors.newFixedThreadPool(10)
        new File("D:\\1_B2BEDI_Revamp\\DOCUIF\\OUT_D99B\\IXORP\\InputData").listFiles().eachWithIndex { curr_File, curr_File_idx ->
            String tar = "D:\\A\\T\\tar\\"
            tar += curr_File_idx + curr_File.getName()
            service.execute(new FutureTask(new SingleFileCopyThread(new CopyCell(curr_File.getAbsolutePath(),tar))))
        }


        service.shutdown()
        while (!service.terminated){

        }
//        new File("D:\\1_B2BEDI_Revamp\\DOCUIF\\OUT_D99B\\IXORP\\InputData").listFiles().eachWithIndex { curr_File, curr_File_idx ->
//            String tar = "D:\\A\\T\\tar\\"
//            tar += curr_File_idx + curr_File.getName()
//            Files.copy(Paths.get(curr_File.getAbsolutePath()),Paths.get(tar),StandardCopyOption.REPLACE_EXISTING)
//        }


        println("CPU: " + Runtime.getRuntime().availableProcessors())
        long end = System.currentTimeMillis()
        println("start: " + start)
        println("end: " + end)
        println("NIO Multi thread total time cost: " + ((end - start)/1000) + "s")

    }
}
