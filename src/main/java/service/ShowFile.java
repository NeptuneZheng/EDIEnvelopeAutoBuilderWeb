package service;

import java.io.*;

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
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        if(lineTxt.startsWith(prefix)){
                            String sub = lineTxt.substring(start,start+len);
                            if(line==0){
                                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~"+lineTxt);
//                            }else if(count == line ){
                            }else if(count == line && !sub.trim().equals("") && sub.trim().equals("FI") ){
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
}
