package service.tools;

import junit.framework.TestCase;

import java.text.DecimalFormat;

/**
 * Created by ZHENGNE on 11/18/2017.
 */
public class ExecuteSQLFileTest extends TestCase {
    public void testExecuteRunnerAbleFile() throws Exception {
//        ExecuteSQLFile es = new ExecuteSQLFile();
//        assertTrue(es.executeRunnerAbleFile("D:\\A\\SQL"));
       DecimalFormat df=new DecimalFormat("#.##");
       double d1 = 3.141592;
       double d2 = 3.145592;
       double d3 = 3.1;

       System.out.println(df.format(d1));
       System.out.println(df.format(d2));
       System.out.println(df.format(d3));
    }

}
