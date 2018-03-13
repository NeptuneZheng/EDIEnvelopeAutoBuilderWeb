package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileDistribution {
    public void distributeFile(String sourcePath_input, String sourcePath_output){
        BufferedReader bufferedReader = null;
        File fis = new File(sourcePath_output);
        String key = "";

        Map<String,String> input_map = jsonToMap();
        boolean distributeFlag = false;
        for(File file : fis.listFiles()){
            if(file.isDirectory()){
                System.out.println("Can't distribute file folder !");
            }else if(file.isFile() && file.getName().contains("_I_")){
                System.out.println(file.getName());
                String oldName = file.getName();
                String old_O_Name = "";
                File file1 = findRelatedOFileName(fis.listFiles(),oldName.substring(0,8));
                if(file1 != null){
                    old_O_Name = file1.getName();
                }
                String newName = "";
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    String lineTxt = null;
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        if(lineTxt.contains("EXTERNAL REF   70")){
                            key = lineTxt.trim().replaceAll("EXTERNAL REF   70","");
                            if(input_map.get(key) != null){
                                newName = input_map.get(key);
                                if(!old_O_Name.equals("")){
                                    distributeFlag = true;
                                }
                            }
                            break;
                        }

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        bufferedReader.close();
                        if(distributeFlag){
                            final boolean a = file.renameTo(new File(file.getPath().replaceAll(oldName,"UIF/"+newName)));
                            final boolean b = file1.renameTo(new File(file1.getPath().replaceAll(old_O_Name,"EDI/"+newName)));
                            if(a && b){
                                System.out.println("Name Updated Sucess From "+oldName+"###"+old_O_Name+"---------->"+newName);
                            }else if(input_map.get(key) != null){
                                File source_file_uif = new File(file.getParent()+"/UIF/"+input_map.get(key));
                                File source_file_edi = new File(file.getParent()+"/EDI/"+input_map.get(key));
                                File source_file_input = new File(sourcePath_input+"/"+input_map.get(key));
                                final boolean ac = file.renameTo(new File(file.getPath().replaceAll(oldName,"UIF/Duplicate/"+oldName)));
                                final boolean ad = source_file_uif.renameTo(new File(source_file_uif.getPath().replaceAll(source_file_uif.getName(),"UIF/Duplicate/"+source_file_uif.getName())));
                                final boolean bc = file1.renameTo(new File(file1.getPath().replaceAll(old_O_Name,"EDI/Duplicate/"+old_O_Name)));
                                final boolean bd = source_file_edi.renameTo(new File(source_file_edi.getPath().replaceAll(source_file_edi.getName(),"EDI/Duplicate/"+source_file_edi.getName())));
                                final boolean dd = source_file_input.renameTo(new File(source_file_input.getPath().replaceAll(source_file_input.getName(),source_file_input.getName()+"_D")));

                                if(ac && ad && bc && bd && dd){
                                    System.out.println("Name Updated Fail"+oldName+"###"+old_O_Name + "And Move To Duplicate Folder Sucess !");
                                }else{
                                    System.out.println("FFFFF");
                                    System.exit(1);
                                }
                            }
                        }
                        distributeFlag = false;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }else{
                continue;
            }
        }
    }

    public void getInputFileNameAndCSBookingRefNumber(String inputPath){
        Map<String,String> map = new HashMap<String, String>();
        Set<String> set = new HashSet<String>();

        BufferedReader bufferedReader = null;
        File fis = new File(inputPath);

        int index = 1;
        String key = "";
        boolean updateFlag = true;
        for(File file : fis.listFiles()){
            if(file.isDirectory()){
                System.out.println("Can't distribute file folder !");
            }else if(file.isFile()){
                String inputFileName = file.getName();
                String newName =index+"_"+file.getName().substring(0,22)+".edi";
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    String lineTxt = null;
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        if(lineTxt.contains("<CSBookingRefNumber>")){
                            key = lineTxt.trim().replaceAll("<CSBookingRefNumber>","").replaceAll("</CSBookingRefNumber>","");
                            if (map.get(key) == null && !set.contains(key)) {
                                map.put(key,newName);
                            }else{
                                set.add(key);
                                updateFlag = false;
                                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Duplicate CSBookingRefNumber exist between "+map.get(key)+" and "+inputFileName);
                            }
                            break;
                        }
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        bufferedReader.close();
                        if(updateFlag && map.get(key) != null){
                            final boolean b = file.renameTo(new File(file.getPath().replaceAll(inputFileName,newName)));
                            if(b){
                                index ++;
                                System.out.println("Name Updated Sucess From "+inputFileName+"---------->"+newName+"************* CSBookingRefNumber: "+key);
                            }else{
                                System.out.println("Name Updated Fail"+inputFileName);
                                System.exit(1);
                            }
                        }else if(!updateFlag && map.get(key) != null){
                            File file1 = new File((file.getParent()+"/"+map.get(key)));
                            final boolean b = file1.renameTo(new File(file1.getPath()+"_D"));
                            if(b){
                                index ++;
                                System.out.println("Name Rollback Sucess To -------"+file1.getName()+"_D" );
                            }else{
                                System.out.println("Name Rollback Fail"+file1.getName());
                                System.exit(1);
                            }
                            map.remove(key);
                        }
                        updateFlag = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }else{
                continue;
            }
        }
        mapToJsonAndSave(map);
    }

    public File findRelatedOFileName(File file[], String fileName_start){
        File result = null;
        for(File f : file){
            if(f.getName().startsWith(fileName_start) && f.getName().contains("_O_")){
                result = f;
                break;
            }
        }
        return result;
    }

    public void mapToJsonAndSave(Map m){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("D:/CSBookingRefNumber.json"), m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String,String> jsonToMap(){
        ObjectMapper mapper = new ObjectMapper();

        Map<String,String> map = null;
        try {
            map = mapper.readValue(new File("D:/CSBookingRefNumber.json"),new TypeReference<Map<String, Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
