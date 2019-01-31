package service.tools

class CORIS {
    public static void main(String[] args ){
        String file_path = 'D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU_MIGRATION\\Data\\CORIS_VOY_Regression_Report.txt'
        String exp_files_pre = 'D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU_MIGRATION\\ExpectedComplete'
        String out_path = 'D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU_MIGRATION\\Data\\CORIS_VOY_Regression_Report_Final.txt'

        BufferedReader br = new BufferedReader(new FileReader(file_path))
        int x = 0
        String last_line_pre = ''
        br.readLines().eachWithIndex{ current_line, current_line_idx ->
            println("@@"+current_line)
            BufferedWriter bw = new BufferedWriter(new FileWriter(out_path,true))
            if(current_line_idx > 0 && current_line){
                String sub_final_line = ''
                String current_line_pre = current_line.split('\\$')[1]
                String exp_file_path = exp_files_pre + "\\" + current_line_pre

                if(last_line_pre == current_line_pre){
                    x++
                }else{
                    x = 1
                }
                String exp_voy = showAllFileByPrefixAndLineAndPosition(exp_file_path,'SHIPMENT',x,67,3,null)
                sub_final_line = current_line.replaceAll('\r\n','') + "," + exp_voy + '\r\n'
                bw.append(sub_final_line)
                bw.close()
            }
            last_line_pre = current_line
        }
    }

    public static String showAllFileByPrefixAndLineAndPosition(String folderPath, String prefix, int line, int start, int len, List<String> showFileLists) {
        String exp = 'null'
        BufferedReader bufferedReader = null;
        File fis = new File(folderPath);
        boolean use_showFileLists = false;
        if(showFileLists != null && showFileLists.size()>0 ){
            use_showFileLists = true;
        }
            int count = 1;
            if(fis.isDirectory()){
                System.out.println("Can't Analysis file folder !");
            }else if(fis.isFile()){
                String inputFileName = fis.getName();
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fis)));
                    String lineTxt = null;
                    int c = 0;
                    int d = 0;
                    int not_null_count = 0;
                    int total_count = 0;
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        d++;
                        if(lineTxt.startsWith(prefix)){
                            String sub = lineTxt.substring(start,start+len);
                            c++;
                            if(line==0){
                                total_count ++;
                                if(sub.trim().length() > 2){
                                    not_null_count ++;
                                }
                                System.out.println(inputFileName+"~~~~~~~~~~~~~~~~~~~~~~~~~"+sub);
                            }else if(count == line && !sub.trim().equals("")){
                                if(start>=0){
                                    exp = sub
                                    System.out.println(count+"-File Name : "+inputFileName+"/***/~~~~~~~~~~~~~~~~~~~~"+sub);
                                }else{
                                    System.out.println("***************************************");
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
            }
        return exp
    }

}
