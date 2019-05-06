package service;




import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class DevideFile {
    public static void main(String[] args) throws IOException {
        String data_path = "D:\\1_B2BEDI_Revamp\\EDIREJECT\\XML\\CARGOSMART\\EDIREJECT";
        String dest_path_I = "D:\\1_B2BEDI_Revamp\\EDIREJECT\\XML\\CARGOSMART\\I";
        String dest_path_O = "D:\\1_B2BEDI_Revamp\\EDIREJECT\\XML\\CARGOSMART\\O";
        String dest_path_XML = "D:\\1_B2BEDI_Revamp\\EDIREJECT\\XML\\CARGOSMART\\I";

        createFolder(dest_path_I,dest_path_O,dest_path_XML);

        doit(data_path,dest_path_I,dest_path_O,dest_path_XML);

    }

    private static void createFolder(String... paths){
        File file;
        for (String path : paths){
            file = new File(path);
            file.mkdirs();
        }
    }

    private static void doit(String data_path, String dest_path_i, String dest_path_o, String dest_path_XML) throws IOException {
        File files = new File(data_path);
        System.out.println("total file size: " + files.listFiles().length);
        int count = 0;
        for(File file: files.listFiles()){
            count ++;
            System.out.println("Copy : " + count + ", " + file.getName());
            if(file.isFile()){
                if(file.getName().endsWith(".out")){
                    FileUtils.copyFile(file,new File(dest_path_o+"/"+file.getName()));
                }else if(file.getName().endsWith(".xml")){
                    FileUtils.copyFile(file,new File(dest_path_XML+"/"+file.getName()));
                }else {
                    FileUtils.copyFile(file,new File(dest_path_i+"/"+file.getName()));
                }
            }else if(file.isDirectory()){
                doit(file.getAbsolutePath(),dest_path_i,dest_path_o,dest_path_XML);
            }
        }
    }
}
