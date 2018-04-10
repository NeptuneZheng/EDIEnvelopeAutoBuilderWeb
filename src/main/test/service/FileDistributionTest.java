package service;

import org.junit.Test;

public class FileDistributionTest {
    FileDistribution fd = new FileDistribution();
    @Test
    public void distributeFile() throws Exception {
        fd.distributeFile("D:\\My Documents\\MyJabberFiles\\luilo@oocl.com\\BR_6Jan_16Jan2018\\TEST\\input","D:\\My Documents\\MyJabberFiles\\luilo@oocl.com\\BR_6Jan_16Jan2018\\TEST\\output");
    }

    @Test
    public void getInputFileName() throws Exception {
        fd.getInputFileNameAndCSBookingRefNumber("D:\\My Documents\\MyJabberFiles\\luilo@oocl.com\\TT\\input");
    }

}