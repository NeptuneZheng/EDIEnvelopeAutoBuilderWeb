package groovy

import hadoop.util.HadoopUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HadoopUtilTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void CREATE_FOLDER() throws Exception {
        System.out.println(System.getenv("HADOOP_HOME"));
        HadoopUtil.CREATE_FOLDER("Carrier BR")
    }

    @Test
    public void UPLOAD() throws Exception {
        HadoopUtil.UPLOAD('D:\\Data\\BR_April\\300 Package1.zip','/usr/local/hadoop/tmp/dfs/data/test/300 Package1.zip')
    }

    @Test
    public void DOWNLOAD_DATA() throws Exception {
        HadoopUtil.DOWNLOAD_DATA('/usr/local/hadoop/tmp/dfs/data/300 Package1.zip','D:/300 Package1.zip')
    }

    @Test
    public void REMOVE_DATA() throws Exception {
        HadoopUtil.REMOVE_DATA('/usr/local/hadoop/tmp/dfs/data')
    }

    @Test
    public void newSolutionTest() throws Exception{
        HadoopUtil hdUtil = new HadoopUtil()
        hdUtil.doSomething(hdUtil.connectToHDFS("hdfs://192.168.47.129:9000/usr/local/hadoop/tmp/dfs/data/Carrier BR/read_me.txt"))
    }

}