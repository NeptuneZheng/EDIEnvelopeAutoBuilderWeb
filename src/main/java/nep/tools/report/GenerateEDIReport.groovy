package nep.tools.report

import nep.tools.cosco.ExtraEDIPartyInfo

class GenerateEDIReport {

    public String generateReport(Map<String,List<Map<String,String>>> cs2EDI, Map<String,List<Map<String,String>>> coscoEDI){
        String result = "BKGNo@CS2_EDIFiles@CS2_EDIFileName@CS2_EDIPartyInfo@COSCO_EDIFiles@COSCO_EDIFileName@COSCO_EDIPartyInfo\r\n"

        Set<String> BKGNoSet = cs2EDI.keySet() + coscoEDI.keySet()
        for (String BKGNo : BKGNoSet){

            List<Map<String,String>> cs2EDIList = (cs2EDI.get(BKGNo)?:new ArrayList<>())
            List<Map<String,String>> coscoEDIList = (coscoEDI.get(BKGNo)?:new ArrayList<>())

            int loopCount = Math.max(cs2EDIList.size(),coscoEDIList.size())
            for(int i = 0; i < loopCount; i++){
                String CS2_EDIFiles = ""
                String CS2_EDIFileName = ""
                String CS2_EDIPartyInfo = ""

                String COSCO_EDIFiles = ""
                String COSCO_EDIFileName = ""
                String COSCO_EDIPartyInfo = ""

                if(i == 0){
                    CS2_EDIFiles = cs2EDIList.size()
                    COSCO_EDIFiles = coscoEDIList.size()
                }else{
                    BKGNo = ""
                }
                if(cs2EDIList.size() > i && coscoEDIList.size() > i){
                    CS2_EDIFileName = cs2EDIList[i].keySet()[0]
                    CS2_EDIPartyInfo = cs2EDIList[i].get(CS2_EDIFileName)?:""

                    COSCO_EDIFileName = coscoEDIList[i].keySet()[0]
                    COSCO_EDIPartyInfo = coscoEDIList[i].get(COSCO_EDIFileName)?:""
                }else if(cs2EDIList.size() > i){
                    CS2_EDIFileName = cs2EDIList[i].keySet()[0]
                    CS2_EDIPartyInfo = cs2EDIList[i].get(CS2_EDIFileName)?:""
                }else if(coscoEDIList.size() > i){
                    COSCO_EDIFileName = coscoEDIList[i].keySet()[0]
                    COSCO_EDIPartyInfo = coscoEDIList[i].get(COSCO_EDIFileName)?:""
                }
                String model = "${BKGNo}@${CS2_EDIFiles}@${CS2_EDIFileName}@${CS2_EDIPartyInfo}@${COSCO_EDIFiles}@${COSCO_EDIFileName}@${COSCO_EDIPartyInfo}\r\n"

                result +=model
            }

        }

        return  result
    }

    public static void main(String[] args){
        GenerateEDIReport generateEDIReport = new GenerateEDIReport()

        String finalStr = generateEDIReport.generateReport(ExtraEDIPartyInfo.extra("D:\\1_B2BEDI_Revamp\\BC\\OUT_D96B\\SONYMY000001-COSU\\ActualComplete"),ExtraEDIPartyInfo.extra("D:\\1_B2BEDI_Revamp\\BC\\OUT_D96B\\SONYMY000001-COSU\\ExpectedComplete"))

        File file = new File("D:\\1_B2BEDI_Revamp\\BC\\OUT_D96B\\SONYMY000001-COSU\\compare.txt")
        FileWriter writer = new FileWriter(file)

        writer.write(finalStr)
        writer.flush()
        writer.close()
    }
}
