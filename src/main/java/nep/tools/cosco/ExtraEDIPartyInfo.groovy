package nep.tools.cosco

import java.util.regex.Matcher
import java.util.regex.Pattern

class ExtraEDIPartyInfo {

    public static Map<String,List<Map<String,String>>> extra(String path){
        File files = new File(path)

        Map<String,List<Map<String,String>>> result = new HashMap<>()
        List<Map<String,String>> list

        files.listFiles().eachWithIndex{ curr_File, curr_File_idx ->
            Map<String,String> subMap = new HashMap<>()

            String bkgNo = ""
            String fileName = curr_File.getName()
            String partyInfo = ""

            BufferedReader bufferedReader = new BufferedReader(new FileReader(curr_File))
            String line

            while ((line = bufferedReader.readLine()) != null){
                //get bkgNo
                if(line.contains("RFF+BN")){
                    bkgNo = getMatchedStr(line,"\\+BN:(.*?)'")
                }
                //get partyInfo
                if(line.contains("NAD+")){
                    String partyType = getPositionStr(line.replaceAll("'",""),"\\+",1)
                    String partyName = getPositionStr(line.replaceAll("'",""),"\\+",4)
                    partyInfo += partyType + ": " + partyName + "#"
                }
            }

            subMap.put(fileName,partyInfo)

            list = result.get(bkgNo)
            if(!list){
                list = new ArrayList<>()
            }
            list.add(subMap)
            result.put(bkgNo,list)
        }

        return result
    }

    static String getMatchedStr(String source, String rgex) {
        Pattern pattern = Pattern.compile(rgex)
        Matcher m = pattern.matcher(source)

        while (m.find()) {
            return m.group(1)
        }
        return ""
    }

    static String getPositionStr(String source, String spliter, int position) {

        String[] segs = source.split(spliter)
        if(segs.size() > position && segs[position]){
            return segs[position]
        }else{
            return ""
        }
    }

    public static void main(String[] args){
        println(ExtraEDIPartyInfo.extra("D:\\1_B2BEDI_Revamp\\BC\\OUT_D96B\\IKEA-COSU\\resource\\COSU-OUTPUT\\V2\\IKEA_BKGUIF_201902021548-201902181506\\BKGUIF\\O"))
    }
}
