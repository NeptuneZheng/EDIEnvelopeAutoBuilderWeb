import org.junit.Test;

public class ThreadTestTest {
    @Test
    public void threadTest(){
        int counter = 30 ;
        boolean isw = true;
        ThreadTest.startService(counter);
        while(counter > 0){
            ThreadTest tt = new ThreadTest(counter,isw);
            tt.start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter --;
            System.out.println(counter);
        }
        ThreadTest.stopService();
    }


}