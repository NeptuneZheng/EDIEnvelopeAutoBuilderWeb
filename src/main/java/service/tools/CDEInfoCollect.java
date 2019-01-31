package service.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CDEInfoCollect {
    public CDEInfoCollect() {
    }

    public  List<String> extraField(File file, String segment, int start, int length){

        List<String> list = new ArrayList<String>();
        BufferedReader br = null;
        try {
             br = new BufferedReader(new FileReader(file));
            String line = "";
             while (( line = br.readLine()) != null){
                 if(line.startsWith(segment)){
                     list.add(line.substring(start,start+length));
                 }
             }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br !=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return  list;
    }

     public static void main (String[] args) throws IOException {
        String exp_file_path = "D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU_UIF\\ExpectedComplete";
        String act_file_path = "D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU_UIF\\ActualComplete";
        String rep_file_path = "D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU_UIF\\report_fnd_QSK.txt";
        CDEInfoCollect cd = new CDEInfoCollect();

        cd.generateReport(exp_file_path,act_file_path,rep_file_path);
    }

    public void generateReport(String exp_file_path, String act_file_path,String rep_file_path) throws IOException {
         File exp_file = new File(exp_file_path);
         File act_file = new File(act_file_path);
         File rep_file = new File(rep_file_path);

         String result = handleItem(exp_file,act_file);

         FileWriter fw = new FileWriter(rep_file);
         fw.write(result);
         fw.close();
    }

    public String handleItem(File exp_file, File act_file) {
        StringBuilder sb = new StringBuilder();
        sb.append("file_name@code_type@souce_code@exp_cde@act_cde\r\n");
        Map<String, List<String>> exp_map = new HashMap<String, List<String>>();
        Map<String, List<String>> act_map = new HashMap<String, List<String>>();

        if(exp_file.isDirectory()){
            for(File file: exp_file.listFiles()){
                exp_map.put(file.getName(),extraField(file,"INTERMODAL",325,3));
            }
        }
        if(act_file.isDirectory()){
            for(File file: act_file.listFiles()){
                act_map.put(file.getName(),extraField(file,"INTERMODAL",325,50));
            }
        }

        if(exp_file.isDirectory()){
            for(File file : exp_file.listFiles()){
                sb.append(buildResult(file.getName(),exp_map.get(file.getName()),act_map.get(file.getName())));
            }
        }

        return sb.toString();

    }

    public String buildResult(String file_name,List<String> exp_strs, List<String> act_strs) {
        StringBuilder result = new StringBuilder();
        if(exp_strs == null || act_strs == null || exp_strs.size() != act_strs.size()){
            System.out.println("file_name:" + file_name + ">>>exp_strs != act_strs, pls check! ");
//            System.exit(1);
        }else {
            for(int i=0; i< exp_strs.size(); i++){
                String ori_act_str = act_strs.get(i);
                String exp_cde_3 = exp_strs.get(i);

                String act_cde_3 = ori_act_str.substring(0,5);
                String act_source_cde = ori_act_str.substring(ori_act_str.length()-5,ori_act_str.length());

                if(!exp_cde_3.equals(act_cde_3)){
                    System.out.println("catah a difference file: " +file_name);
                    result.append(file_name)
                            .append("@FND@")
                            .append(act_source_cde)
                            .append("@")
                            .append(exp_cde_3)
                            .append("@")
                            .append(act_cde_3)
                            .append("\r\n");
                }

            }
        }


        return result.toString();
    }
}
