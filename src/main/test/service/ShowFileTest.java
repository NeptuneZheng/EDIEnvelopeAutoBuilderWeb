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
        String folder_path = "D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU_UIF\\ExpectedComplete";
//        showFile.showAllFileByPrefixAndLineAndPosition(folder_path,"EXTERNAL REF",0,0,67,showFile.getShowFileList(folder_path,"CANCEL"));
        showFile.showAllFileByPrefixAndLineAndPosition(folder_path,"INTERMODAL",0,153,11,null);
//        showFile.showAllFileByPrefixAndLineAndPosition("D:\\My Documents\\MyJabberFiles\\youal@oocl.com","O/B DOOR",0,1,25);
    }

    @Test
    public void showAllEDIFileByPrefixAndLineAndPosition() throws Exception{
        showFile.showAllEDIFileByPrefixAndLineAndPosition("C:\\Users\\ZHENGNE\\Desktop\\Support\\2019\\DL-20190506111101","B4","~","\\*",20,3);
//        showFile.showAllFileByPrefixAndLineAndPosition("D:\\My Documents\\MyJabberFiles\\youal@oocl.com","O/B DOOR",0,1,25);
    }

}