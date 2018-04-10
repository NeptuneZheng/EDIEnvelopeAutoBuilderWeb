package service;

import junit.framework.TestCase;

/**
 * Created by ZHENGNE on 11/13/2017.
 */
public class FileNameUpdateTest extends TestCase {
    FileNameUpdate fu = new FileNameUpdate();
    public void testUpdateFileName() throws Exception {
        fu.updateFileName("D:\\My Documents\\MyJabberFiles\\XIAOTR@oocl.com\\RICOH");
    }

    public void testSelectFile() throws Exception {
        fu.selectFile("D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\TEST\\EDI");
    }


}