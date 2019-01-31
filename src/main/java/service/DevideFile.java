package service;

import java.io.File;

public class DevideFile {
    public static void main(String[] args){
        String data_path = "D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\TEST\\All";
        String dest_path_I = "D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU_UIF\\InputData";
        String dest_path_O = "D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU_UIF\\ExpectedComplete";

        doit(data_path,dest_path_I,dest_path_O);

    }

    private static void doit(String data_path, String dest_path_i, String dest_path_o) {
        File files = new File(data_path);
        for(File file: files.listFiles()){
            if(file.isFile()){
                if(file.getName().length() > 24){
                    file.renameTo(new File(dest_path_o+"/"+file.getName().substring(0,22)));
                }else {
                    file.renameTo(new File(dest_path_i+"/"+file.getName().substring(0,22)));
                }
            }else if(file.isDirectory()){
                doit(file.getAbsolutePath(),dest_path_i,dest_path_o);
            }
        }
    }
}
