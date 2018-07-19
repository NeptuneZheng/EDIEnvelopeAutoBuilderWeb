package service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ShowFile {
    public void showAllFileByLine(String folderPath,int line){
        BufferedReader bufferedReader = null;
        File fis = new File(folderPath);

        for(File file : fis.listFiles()){
            int count = 1;
            if(file.isDirectory()){
                System.out.println("Can't Analysis file folder !");
            }else if(file.isFile()){
                String inputFileName = file.getName();
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    String lineTxt = null;
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        if(count==line){
                            System.out.println(count+"-File Name : "+inputFileName+"*****"+lineTxt);
                            break;
                        }
                        count++;
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                continue;
            }
        }
    }

    public void showAllFileByPrefixAndLine(String folderPath, String prefix, int line) {
        BufferedReader bufferedReader = null;
        File fis = new File(folderPath);

        for(File file : fis.listFiles()){
            int count = 1;
            if(file.isDirectory()){
                System.out.println("Can't Analysis file folder !");
            }else if(file.isFile()){
                String inputFileName = file.getName();
                if(line == 0){
//                    System.out.println("File Name : "+inputFileName+"*****");
                }
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    String lineTxt = null;
                    int count_out = 0;
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        if(lineTxt.startsWith(prefix)){
                            if(line==0){
                                if(count_out > 2){
                                System.out.println(inputFileName+"~~~~~~~~~~~~~~~~~~~~~~~~~"+lineTxt);
                                }
                                count_out ++;
                            }else if(count == line){
                                    System.out.println(count+"-File Name : "+inputFileName+"/***/~~~~~~~~~~~~~~~~~~~~"+lineTxt);
                            }else if(count>line && line>0){
                                break;
                            }
                            count++;
                        }
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                continue;
            }
        }
    }

    public void showAllFileByPrefixAndLineAndPosition(String folderPath, String prefix, int line,int start,int len) {
        BufferedReader bufferedReader = null;
        File fis = new File(folderPath);

        for(File file : fis.listFiles()){
            int count = 1;
            if(file.isDirectory()){
                System.out.println("Can't Analysis file folder !");
            }else if(file.isFile()){
                String inputFileName = file.getName();
//                System.out.println("File Name : "+inputFileName+"*****");
//                if(line == 0){
//                    System.out.println("File Name : "+inputFileName+"*****");
//                }
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    String lineTxt = null;
                    int c = 0;
                    int not_null_count = 0;
                    int total_count = 0;
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        if(lineTxt.startsWith(prefix)){
                            String sub = lineTxt.substring(start,start+len);
                            c++;
                            if(line==0){
                                total_count ++;
                                if(sub.trim().length() > 2){
                                    not_null_count ++;
                                }
                                System.out.println(inputFileName+"~~~~~~~~~~~~~~~~~~~~~~~~~"+lineTxt);
//                            }else if(count == line && sub.trim().length() > 2){
                            }else if(count == line && !sub.trim().equals("")){
                                //&& sub.trim().equals("DSP")  && !sub.trim().equals("")
                                if(start>=0){
                                    System.out.println(count+"-File Name : "+inputFileName+"/***/~~~~~~~~~~~~~~~~~~~~"+sub);
//                                    System.exit(1);
                                }else{
                                    System.out.println(count+"-File Name : "+inputFileName+"/***/~~~~~~~~~~~~~~~~~~~~"+lineTxt);
                                }
                            }else if(count>line && line>0){
                                break;
                            }
                            count++;
                        }
                    }
//                    if( not_null_count > 0 && total_count > 0 && !file.getName().endsWith("boxml")){
//                        System.out.println(inputFileName+"~~~~~~~~~~~~~~~~~~~~~~~~~"+"(total_count: "+total_count+"),(not_null_count: "+not_null_count+")");
//                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                continue;
            }
        }
    }

    public void showAllEDIFileByPrefixAndLineAndPosition(String folderPath,String prefix, String seg_seperater,String field_seperater, int line,int position) {
        BufferedReader bufferedReader = null;
        File fis = new File(folderPath);
        Map<String,String> distint_case = new HashMap<String, String>();

        for(File file : fis.listFiles()){
            int count = 1;
            if(file.isDirectory()){
                System.out.println("Can't Analysis file folder !");
            }else if(file.isFile()){
                String inputFileName = file.getName();
//                System.out.println("File Name : "+inputFileName+"*****");
//                if(line == 0){
//                    System.out.println("File Name : "+inputFileName+"*****");
//                }
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    String lineTxt = null;
                    int c = 0;
                    int not_null_count = 0;
                    int total_count = 0;
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        String[] segment_arrs = lineTxt.split(seg_seperater);
                        for(String segment : segment_arrs){
                            if(segment.startsWith(prefix)){
                                String[] field_arrs = segment.split(field_seperater);
                                if(position == 0){
                                    System.out.println(inputFileName+"~~~~~~~~~~~~~~~~~~~~~~~~~"+segment);
                                }else if(field_arrs.length > position){
                                    if(distint_case.get(field_arrs[position]) == null){
                                        distint_case.put(field_arrs[position],inputFileName);
                                    }
                                    System.out.println(inputFileName+"~~~~~~~~~~~~~~~~~~~~~~~~~"+field_arrs[position]);
                                }else{
                                    System.out.println(inputFileName+ "!!!!!!!!!" +"seems your position beyond segment fields, pls check with your whole segment: " + segment);
                                }
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                continue;
            }
        }
        System.out.println("all distint value: " + distint_case);
    }

}
