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
//        showFile.showAllFileByPrefixAndLineAndPosition("D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU\\ExpectedComplete","SHIPMENT",1,15,17);
        //OBAPPOINTMENTADDRESS
//        showFile.showAllFileByPrefixAndLineAndPosition("D:\\My Documents\\MyJabberFiles\\luilo@oocl.com\\BR_6Jan_16Jan2018_1\\TEST\\output\\UIF","APPOINTMENT",1,15,120);
//        showFile.showAllFileByPrefixAndLineAndPosition("D:\\My Documents\\MyJabberFiles\\luilo@oocl.com\\BR_6Jan_16Jan2018_1\\TEST\\output\\Else","APPOINTMENT",1,15,120);
//        //all file of BU_IBDOOR_HAULAGE output Y
//        showFile.showAllFileByPrefixAndLineAndPosition("D:\\My Documents\\MyJabberFiles\\luilo@oocl.com\\BR_6Jan_16Jan2018_1\\TEST\\output\\UIF","I/B DOOR",1,525,1);
//        showFile.showAllFileByPrefixAndLineAndPosition("D:\\My Documents\\MyJabberFiles\\luilo@oocl.com\\BR_6Jan_16Jan2018_1\\TEST\\output\\Else","I/B DOOR",1,525,1);
        //all prod data of CARGOWEIGHT is 00000000
//        showFile.showAllFileByPrefixAndLineAndPosition("D:\\My Documents\\MyJabberFiles\\luilo@oocl.com\\BR_6Jan_16Jan2018_1\\TEST\\output\\UIF","CARGO",1,127,9);
//        showFile.showAllFileByPrefixAndLineAndPosition("D:\\My Documents\\MyJabberFiles\\luilo@oocl.com\\BR_6Jan_16Jan2018_1\\TEST\\output\\Else","CARGO",1,127,9);
        //BU_DANGEROUS_FINAL_SHIPNME
//        showFile.showAllFileByPrefixAndLineAndPosition("D:\\My Documents\\MyJabberFiles\\luilo@oocl.com\\BR_6Jan_16Jan2018_1\\TEST\\output\\UIF","DANGEROUS",1,1324,180);
//        showFile.showAllFileByPrefixAndLineAndPosition("D:\\My Documents\\MyJabberFiles\\luilo@oocl.com\\BR_6Jan_16Jan2018_1\\TEST\\output\\Else","DANGEROUS",1,1324,180);
        //
        showFile.showAllFileByPrefixAndLineAndPosition("D:\\My Documents\\MyJabberFiles\\luilo@oocl.com\\300(1)\\300","REMARKS        01 EXTVOY:",1,0,50);
//        showFile.showAllFileByPrefixAndLineAndPosition("D:\\My Documents\\MyJabberFiles\\youal@oocl.com","O/B DOOR",0,1,25);
    }

}