package multi.thread

import org.apache.log4j.Logger

class CreateMultThreadByExtendThread extends Thread {
    static Map map = Collections.synchronizedMap(new HashMap())
    static int threadIdx = 1
    Logger logger = Logger.getLogger(this.class)

    CreateMultThreadByExtendThread() {}

    public void run(){
        logger.info("ThreadName<${this.getName()}> ** threadIdx[${threadIdx}]++,get current map size: ${map.size()}, details: ${map.toString()}")
        map.put(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime().format("hhMMssSSS"),"CreateMultThreadByExtendThread")
        sleep(3000%threadIdx)
        logger.info("ThreadName<${this.getName()}> // threadIdx[${threadIdx}]--,finish current map size: ${map.size()}")
        threadIdx ++
    }

    public static void main(String[] args){
        String [] t = new int[8]
        println(t.size())
        long startTime = System.nanoTime()
        for(int i=0; i < 50; i ++ ){
            println("create thread:***********" + i)
            CreateMultThreadByExtendThread createMultThreadByExtendThread = new CreateMultThreadByExtendThread()
            createMultThreadByExtendThread.setName("Thread-${i}")
            createMultThreadByExtendThread.start()
        }
        long endTime = System.nanoTime()
        println("Take Time: ${endTime - startTime}")
    }
}
