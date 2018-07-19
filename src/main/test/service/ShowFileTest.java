package service;

import org.junit.Test;

public class ShowFileTest {
    ShowFile showFile = new ShowFile();

    @Test
    public void showAllFileByLine() throws Exception {
        showFile.showAllFileByLine("D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU\\ExpectedComplete",1);
    }

    @Test
    public void showAllFileByPrefixAndLine() throws Exception{
//        showFile.showAllFileByPrefixAndLine("D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU\\ExpectedComplete","EXTERNAL REF",5);
        showFile.showAllFileByPrefixAndLine("D:\\My Documents\\MyJabberFiles\\luilo@oocl.com\\BR_6Jan_16Jan2018_1\\TEST\\output\\UIF","CONTAINER GRP  ",0);
    }

    @Test
    public void showAllFileByPrefixAndLineAndPosition() throws Exception{
        showFile.showAllFileByPrefixAndLineAndPosition("D:\\1_B2BEDI_Revamp\\BR\\IN_D99B\\IXORP-COSU\\ExpectedComplete","I/B DOOR",0,22,13);
//        showFile.showAllFileByPrefixAndLineAndPosition("D:\\My Documents\\MyJabberFiles\\youal@oocl.com","O/B DOOR",0,1,25);
    }

    @Test
    public void showAllEDIFileByPrefixAndLineAndPosition() throws Exception{
        showFile.showAllEDIFileByPrefixAndLineAndPosition("D:\\1_B2BEDI_Revamp\\BR\\IN_D99B\\DAKOSY-COSU\\InputData","FTX","'","\\+",100,1);
//        showFile.showAllFileByPrefixAndLineAndPosition("D:\\My Documents\\MyJabberFiles\\youal@oocl.com","O/B DOOR",0,1,25);
    }
}