package service;

import java.io.*;

public class BuildSQL {
    public  void buildSQLFile(String int_cdePath, String ext_cdePath, String result_Path ,String rmk_cdePath) {
        BufferedReader bufferedReader1 = null;
        BufferedReader bufferedReader2 = null;
        BufferedReader bufferedReader3 = null;

        FileWriter fw = null;
        BufferedWriter bw = null;

        File int_fis = new File(int_cdePath);
        File ext_fis = new File(ext_cdePath);
        File rmk_fis = new File(rmk_cdePath);
        File result_fis = new File(result_Path);

        String result_Str = "";
        String before_str = "insert into b2b_cde_conversion (CONVERT_TYPE_ID, DIR_ID, MSG_TYPE_ID, MSG_FMT_ID, TP_ID, SCAC_CDE, INT_CDE, EXT_CDE, REMARKS, CREATE_TS, UPDATE_TS, UPDATED_BY, REF_TABLE, ACTIVE_FLAG)values ('ITS_VESSEL', 'O', '', 'UIF', 'COSU_UIF', '', ";
        String after_str = ", '";
        String rmark_str =  "', sysdate, sysdate, 'Neptune','ITS_VESSEL', 'T');\r\n";

        if (int_fis.isFile()) {
            try {
                bufferedReader1 = new BufferedReader(new InputStreamReader(new FileInputStream(int_fis)));
                String lineTxt1 = null;
                int line_idx1 = 0;
                while ((lineTxt1 = bufferedReader1.readLine()) != null) {
                    if (ext_fis.isFile()) {
                        bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(ext_fis)));
                        String lineTxt2 = null;
                        int line_idx2 = 0;
                        while ((lineTxt2 = bufferedReader2.readLine()) != null) {
                            if(rmk_fis.isFile() && line_idx2 == line_idx1){
                                bufferedReader3 = new BufferedReader(new InputStreamReader(new FileInputStream(rmk_fis)));
                                String lineTxt3 = null;
                                int line_idx3 = 0;
                                while ((lineTxt3 = bufferedReader3.readLine()) != null) {
                                    if (line_idx3 == line_idx2) {
                                        result_Str += before_str + "'" + lineTxt1.trim() + "','" + lineTxt2.trim() + "'" + after_str + lineTxt3.trim()+rmark_str;
                                    }
                                    line_idx3 ++;
                                }
                            }
                            line_idx2++;
                        }
                    }
                    line_idx1++;
                }
                System.out.println(line_idx1);

                fw = new FileWriter(result_fis, false);
                bw = new BufferedWriter(fw);
                bw.write(result_Str);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bw.close();
                    fw.close();
                    bufferedReader1.close();
                    bufferedReader2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    }


