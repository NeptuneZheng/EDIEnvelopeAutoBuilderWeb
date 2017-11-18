package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by ZHENGNE on 11/13/2017.
 */
public class FileNameUpdate {
    public static  void updateFileName(String path){
        File fis = new File(path);
        for(File file : fis.listFiles()){
            if(file.isDirectory()){
                updateFileName(file.getPath());
            }else if(file.isFile() && file.getName().length()>22){
                String oldName = file.getName();
                String newName =file.getName().substring(1,22)+".edi";
                final boolean b = file.renameTo(new File(file.getPath().replaceAll(oldName,newName)));
                if(b){
                    System.out.println("Name Updated Sucess From "+oldName+"---------->"+newName);
                }else{
                    System.out.println("Name Updated Fail"+oldName);
                    System.exit(1);
                }
            }else{
                continue;
            }
        }
    }
}
