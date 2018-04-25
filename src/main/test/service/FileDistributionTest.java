package service;

import org.junit.Test;

public class FileDistributionTest {
    FileDistribution fd = new FileDistribution();
    @Test
    public void distributeFile() throws Exception {
        fd.distributeFile("D:\\Data\\output","D:\\Data\\output");
    }

    @Test
    public void getInputFileName() throws Exception {
        fd.getInputFileNameAndCSBookingRefNumber("C:\\Users\\ZHENGNE\\Desktop\\1~\\input");
    }

    @Test
    public void selectReleatedFile() throws Exception {
        fd.selectReleatedFile("D:\\Data\\input\\input","D:\\Data\\output\\UIF");
    }
}