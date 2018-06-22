package service;

import java.io.*;

public class BuildSQL {
//    public  void buildSQLFile(String int_cdePath, String ext_cdePath, String result_Path ,String rmk_cdePath) {
//        BufferedReader bufferedReader1 = null;
//        BufferedReader bufferedReader2 = null;
//        BufferedReader bufferedReader3 = null;
//
//        FileWriter fw = null;
//        BufferedWriter bw = null;
//
//        File int_fis = new File(int_cdePath);
//        File ext_fis = new File(ext_cdePath);
//        File rmk_fis = new File(rmk_cdePath);
//        File result_fis = new File(result_Path);
//
//        String result_Str = "";
//        String before_str = "insert into b2b_cde_conversion (CONVERT_TYPE_ID, DIR_ID, MSG_TYPE_ID, MSG_FMT_ID, TP_ID, SCAC_CDE, INT_CDE, EXT_CDE, REMARKS, CREATE_TS, UPDATE_TS, UPDATED_BY, REF_TABLE, ACTIVE_FLAG)values ('ITS_VESSEL', 'O', '', 'UIF', 'COSU_UIF', '', ";
//        String after_str = ", '";
//        String rmark_str =  "', sysdate, sysdate, 'Neptune','ITS_VESSEL', 'T');\r\n";
//
//        if (int_fis.isFile()) {
//            try {
//                bufferedReader1 = new BufferedReader(new InputStreamReader(new FileInputStream(int_fis)));
//                String lineTxt1 = null;
//                int line_idx1 = 0;
//                while ((lineTxt1 = bufferedReader1.readLine()) != null) {
//                    if (ext_fis.isFile()) {
//                        bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(ext_fis)));
//                        String lineTxt2 = null;
//                        int line_idx2 = 0;
//                        while ((lineTxt2 = bufferedReader2.readLine()) != null) {
//                            if(rmk_fis.isFile() && line_idx2 == line_idx1){
//                                bufferedReader3 = new BufferedReader(new InputStreamReader(new FileInputStream(rmk_fis)));
//                                String lineTxt3 = null;
//                                int line_idx3 = 0;
//                                while ((lineTxt3 = bufferedReader3.readLine()) != null) {
//                                    if (line_idx3 == line_idx2) {
//                                        result_Str += before_str + "'" + lineTxt1.trim() + "','" + lineTxt2.trim() + "'" + after_str + lineTxt3.trim()+rmark_str;
//                                    }
//                                    line_idx3 ++;
//                                }
//                            }
//                            line_idx2++;
//                        }
//                    }
//                    line_idx1++;
//                    System.out.println(line_idx1);
//                }
//                System.out.println("---------------Total: " + line_idx1);
//
//                fw = new FileWriter(result_fis, false);
//                bw = new BufferedWriter(fw);
//                bw.write(result_Str);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    bw.close();
//                    fw.close();
//                    bufferedReader1.close();
//                    bufferedReader2.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
    public  void buildSQLFile(String int_cdePath, String ext_cdePath, String result_Path ,String rmk_cdePath) {
        BufferedInputStream bis_in = null;
        BufferedInputStream bis_ex = null;
        BufferedInputStream bis_rm = null;

        FileWriter fw = null;
        BufferedWriter bw = null;

        File int_fis = new File(int_cdePath);
        File ext_fis = new File(ext_cdePath);
        File rmk_fis = new File(rmk_cdePath);
        File result_fis = new File(result_Path);

        StringBuilder result_Str = new StringBuilder();
        String before_str = "insert into b2b_cde_conversion (CONVERT_TYPE_ID, DIR_ID, MSG_TYPE_ID, MSG_FMT_ID, TP_ID, SCAC_CDE, INT_CDE, EXT_CDE, REMARKS, CREATE_TS, UPDATE_TS, UPDATED_BY, REF_TABLE, ACTIVE_FLAG)values ('ITS_VESSEL', 'O', '', 'UIF', 'COSU_UIF', '', ";
        String after_str = ", '";
        String rmark_str =  "', sysdate, sysdate, 'Neptune','ITS_VESSEL', 'T');\r\n";

        if (int_fis.isFile() && ext_fis.isFile() && rmk_fis.isFile()) {
            try {
                bis_in = new BufferedInputStream(new FileInputStream(int_fis));
                bis_ex = new BufferedInputStream(new FileInputStream(ext_fis));
                bis_rm = new BufferedInputStream(new FileInputStream(rmk_fis));

                //UTF-8编码中，一个英文字符等于一个字节，一个中文（含繁体）等于三个字节。
                byte[] bytes = new byte[2048];
                int n = -1;

                StringBuilder str_in = new StringBuilder();
                while ((n = bis_in.read(bytes, 0, bytes.length)) > -1){
                    str_in.append(new String(bytes, 0, n, "utf-8"));
                }
                StringBuilder str_ex = new StringBuilder();
                while ((n = bis_ex.read(bytes, 0, bytes.length)) > -1){
                    str_ex.append(new String(bytes, 0, n, "utf-8"));
                }
                StringBuilder str_rm = new StringBuilder();
                while ((n = bis_rm.read(bytes, 0, bytes.length)) > -1){
                    str_rm.append(new String(bytes, 0, n, "utf-8"));
                }

                String[] arr_in = str_in.toString().split("\r\n");
                String[] arr_ex = str_ex.toString().split("\r\n");
                String[] arr_rm = str_rm.toString().split("\r\n");

                if (!(arr_in.length != arr_ex.length && arr_ex.length != arr_rm.length && arr_in.length != arr_rm.length)) {
                    int loop_max = arr_in.length;
                    if(loop_max < arr_ex.length){
                        loop_max = arr_ex.length;
                    }else if(loop_max < arr_rm.length){
                        loop_max = arr_rm.length;
                    }
                    for(int i = 0 ; i < loop_max ; i++){
                        result_Str.append(before_str).append("'").append(getArrAt(arr_in,i)).append("','").append(getArrAt(arr_ex,i)).append("'").append(after_str).append(getArrAt(arr_rm,i)).append(rmark_str);
                        System.out.println("---------------Current point: " + i);
                    }
                    System.out.println("---------------Total: " + loop_max);
                    System.out.println("---------------all build sucess.");
                }else{
                    System.out.println("Failed in verify, pls check if none of int_cde, ext_cde, remarks equals with another!");
                }



                fw = new FileWriter(result_fis, false);
                bw = new BufferedWriter(fw);
                bw.write(result_Str.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                    if (fw != null) {
                        fw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    String getArrAt(String[] arr, int idx){
        if(arr.length <= idx){
            return  arr[arr.length -1].replaceAll("'","''");
        }else{
            return arr[idx].replaceAll("'","''");
        }
    }

}


