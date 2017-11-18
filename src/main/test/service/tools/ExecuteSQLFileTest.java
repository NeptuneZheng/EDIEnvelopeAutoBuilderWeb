package service.tools;

import junit.framework.TestCase;

/**
 * Created by ZHENGNE on 11/18/2017.
 */
public class ExecuteSQLFileTest extends TestCase {
    public void testExecuteRunnerAbleFile() throws Exception {
        ExecuteSQLFile es = new ExecuteSQLFile();
        assertTrue(es.executeRunnerAbleFile("D:\\A\\SQL"));
    }

}