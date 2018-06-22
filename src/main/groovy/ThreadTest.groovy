class ThreadTest extends Thread{
    private int thread_id;
    private static int thread_counter;
    private static boolean isWorking;
    private static boolean isRunning;

    public ThreadTest(int id, boolean isw) {
        this.thread_id = id
        this.isWorking = isw
    }

    boolean getIsWorking() {
        return isWorking
    }

    public void run(){
        if(thread_id%2 == 0){
            Thread.sleep(2000)
        }
        System.out.println('running thread: ' + thread_id)
        Thread.sleep(10)
        System.out.println('running thread222: ' + thread_id)
        thread_counter--
    }

    public static void stopService(){
        while(thread_counter > 0){
            System.out.println("ThreadTest still working now, pls wait.")
            Thread.sleep(100)
        }
        System.out.println("ThreadTest service stop.")
        this.isRunning = false
    }
    public static void startService(int counter){
        System.out.println("ThreadTest service start.")
        this.thread_counter = counter
        this.isRunning = true
    }
}
