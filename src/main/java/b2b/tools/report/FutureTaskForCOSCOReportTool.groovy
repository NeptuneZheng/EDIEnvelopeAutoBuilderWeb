package b2b.tools.report

import org.apache.poi.ss.usermodel.IndexedColors

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

class FutureTaskForCOSCOReportTool {

    public static void main(String[] args){
        long startTime = System.currentTimeMillis()
        String path1 = "D:\\A\\COSCO-Report1.xlsx"
        String path2 = "D:\\A\\COSCO-Report2.xlsx"

        List<List<Map<String,Object>>> list = new ArrayList<>()
        for(int i = 0; i < 10; i++){
            List<Map<String,Object>> subList = new ArrayList<>()
            for(int j = 0; j < 20; j++){
                Map<String,Object> map = new HashMap<>()
                if(i == j){
                    map.put('color', IndexedColors.PINK.getIndex())
                }
                map.put('value', "${i}-${j}")
                subList.add(map)
            }
            list.add(subList)
        }

        RunnableCOSCOReportTool tool1 = new RunnableCOSCOReportTool(path1,"FileInfo",list)
        RunnableCOSCOReportTool tool2 = new RunnableCOSCOReportTool(path2,"EDIInfo",list)

        FutureTask futureTask1 = new FutureTask(tool1,null)
        FutureTask futureTask2 = new FutureTask(tool2,null)

        ExecutorService service = Executors.newFixedThreadPool(2)
        service.execute(futureTask1)
        service.execute(futureTask2)

        while (true){
            if(futureTask1.isDone() && futureTask2.isDone()){
                service.shutdown()
                println('all FutureTaskForCOSCOReportTool thread finished ....')
                long endTime = System.currentTimeMillis()
                println("total cost: ${endTime - startTime}")
                break
            }
        }

    }
}
