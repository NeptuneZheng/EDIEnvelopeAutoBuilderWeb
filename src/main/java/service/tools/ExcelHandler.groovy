package service.tools

import org.apache.log4j.Logger
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory

class ExcelHandler {
    private static Logger logger = Logger.getLogger(this.class)
    protected static String Record = "CS_BKG_REF_NUM,SENDR_ID,MSG_GMT_D,DATA_SOURCE,PTY,CARRIER_CUST_CDE,OFFICE_CDE,ConvertByCCC,duplicate_CS_BKG_REF_NUM,ConflictConvert\r\n"
    private static def legalCCC = ['1000906000':'LEH','1000907000':'ANR','1000908000':'LEH','1001795000':'AKL',
                                        '1002744000':'LEH','1002751000':'ANR','1003873000':'BKK','1004773000':'VNA',
                                        '1005214000':'HOU','1005623000':'LEH','1005814000':'OSA','1006966000':'HOU',
                                        '1007298000':'GOT','1007753000':'NAP','1007754000':'NAP','1009577000':'OSL',
                                        '1009579000':'CPH','1009911000':'BKG','1010041000':'TOR',
                                        '1010628000':'HOU','1010707000':'HAM','1010744000':'BRE','1012176000':'HOU',
                                        '1014667000':'DUS','101608000 ':'BKG','1016080000':'BKG','1016259000':'HOU',
                                        '1017543000':'LAX','1017608000':'VNA','1017750000':'HOU','1017820000':'RTM',
                                        '1017821000':'VNA','1019434000':'BKG','1019480000':'BKG','1019937000':'OPO',
                                        '1022051000':'ANR','1022209000':'HOU','1023390000':'VNA','1025790000':'VAN',
                                        '1025822000':'HOU','1026775000':'TYO','1031709000':'TOR','1031710000':'VAN',
                                        '1032860000':'HOU','1033079000':'GVA','1033205000':'HOU','1033596000':'LAX',
                                        '1033758000':'VNA','1034241000':'HRB','1036250000':'BKG','1036327000':'DLC',
                                        '1036751000':'SHY','1037716000':'HOU','1039745000':'BKG','1040464000':'BKG',
                                        '1041593000':'HOU','1041598000':'HOU','1041903000':'HOU','1042006000':'HOU',
                                        '1042287000':'HOU','1042288000':'HOU','1042290000':'HOU','1042291000':'HOU',
                                        '1042292000':'HOU','1042293000':'HOU','1042297000':'HOU','1042298000':'HOU',
                                        '1044177000':'HOU','1044610000':'MIL','1044640000':'SIN','1046012000':'GVA',
                                        '1046952000':'VNA','1048751000':'HOU','1049111000':'JKT','1049229000':'GOT',
                                        '1051054000':'AKL','1051086000':'IST','105117100 ':'BKG','1051171000':'BKG',
                                        '1052128000':'DUB','1052595000':'GVA','1052605000':'GVA','1052974000':'CPH',
                                        '1053414000':'ZRH','1053452000':'HOU','1054362000':'LEH','1056134000':'MIL',
                                        '1058267000':'AKL','1060677000':'HOU','1061086000':'CPH','1066033000':'CPH',
                                        '1070061000':'ZRH','1070498000':'LEH','1072093000':'HOU','1072189000':'TPE',
                                        '1075208000':'BKG','1076825000':'OSL','1080253000':'PRG','1086767000':'GVA',
                                        '1093163000':'GVA','1094841000':'GVA','1096619000':'SEL','1097341000':'ZRH',
                                        '1097775000':'PRG','1101907000':'LEH','1103060000':'ZRH','1103061000':'GVA',
                                        '1103404000':'HOU','1104706000':'RTM','1106604000':'IZM','1107717000':'ANR',
                                        '1113013000':'HEL','1114641000':'GOT','1119425000':'SGN','1121239000':'GVA',
                                        '1121247000':'GVA','1121248000':'GVA','1121270000':'LEH','1121580000':'PSD',
                                        '1127005000':'BKG','1127105000':'BKG','1128934000':'RTM','1131730000':'GOT',
                                        '1133842000':'BKG','1134534000':'MIL','1135101000':'LEH','1136427000':'VNA',
                                        '1136444000':'VNA','1139479000':'HEL','1147474000':'VNA','1147642000':'HOU',
                                        '1148061000':'DUS','1149905000':'LEH','1150577000':'CPH','1150807000':'GDY',
                                        '1152027000':'HAM','1154376000':'ZRH','1154462000':'BKG','1158703000':'HOU',
                                        '1159850000':'DXB','1160090000':'HOU','1160810000':'IZM','1170600000':'KUL',
                                        '1180700000':'PEN','1182361000':'HEL','1185148000':'JNB','1189441000':'LEH',
                                        '1191003000':'FRA','1196283000':'TPE','1196723000':'ZRH','1202208000':'OSL',
                                        '1215507000':'HEL','1219003000':'VNA','1220528000':'DLC','1220813000':'BUE',
                                        '1227423000':'YIK','1235193000':'CMB','1236596000':'SHY','1239802000':'VNA',
                                        '1241101000':'LAX','1244718000':'HOU','1249501000':'BKG','1252053000':'VNA',
                                        '1258404000':'TPE','1278546000':'ZRH','1280626000':'JNB','1280647000':'ZRH',
                                        '1285811000':'BUD','1292100000':'PIR','1305466000':'GDY','1310931000':'HPC',
                                        '4510243980':'KOP','6004731000':'MNL','6010602001':'VNA','6035214000':'LEH',
                                        '6035214002':'LEH','6035214003':'LEH','6050556000':'PSD','6069847000':'VNA',
                                        '6072036000':'JKT','6075011000':'SUB','6091208000':'GDY','6091208001':'GDY',
                                        '6091208003':'GDY','6210814000':'JNB','6216179000':'BUE','6233437000':'VNA',
                                        '6234349000':'BKK','6236990000':'JKT','6242321000':'VNA','6243236000':'CPH',
                                        '6249372000':'DUS','6254033000':'YNJ','6259380000':'HRB','6259522000':'JIL',
                                        '6259528000':'YNJ','6271786000':'HOU','6281582000':'SHA','6297131000':'HOU',
                                        '6307581000':'HEL','6307757000':'BKK','6312391000':'SHY','6342804000':'SEL',
                                        '6348366000':'YNJ','6349806000':'ZRH','6353508000':'LEH','6354040000':'JNB',
                                        '6356359000':'VNA','6359408000':'HRB','6368389000':'MIL','6371342000':'ZRH',
                                        '6383664000':'LEH','6384711000':'RIJ','6393485000':'BLW','6394581000':'HOU',
                                        '6394585000':'HOU','6394587000':'HOU','6400088000':'LAX','6400681000':'JNB',
                                        '6409155000':'SEL','6414949000':'VNA','6428766000':'HEL','6433770000':'PRG',
                                        '6438336000':'HOU','6441843000':'VNA','6444676000':'LEH','6446507000':'LEH',
                                        '6449200000':'PRG','6450373000':'NAP','6454822000':'PRG','6454824000':'PRG',
                                        '6462261000':'HPC','6476756000':'CGP','6482735000':'SUB','6488741000':'LIS',
                                        '6489763000':'HRB','6493486000':'JNZ','6496255000':'MVD','6525187000':'DUS',
                                        '6525734000':'BKG','6532418000':'HOU','6534165000':'JKT','6540717000':'AKL',
                                        '6543532000':'TSN','6544480000':'BUD','6567060000':'MAD','6567060001':'BLA',
                                        '6567060002':'VDA','6567060003':'BLA','6567060004':'BIL','6567060005':'VDA',
                                        '6567060006':'MAD','6567060008':'BLA','6567355000':'HRB','6568140000':'LEH',
                                        '6571224000':'BKG','6572111000':'SIN','6572698000':'CPH','6575379000':'LEH',
                                        '6584628000':'LEH','6587301000':'OSL','6588033000':'HOU','6588392000':'OSL',
                                        '6590862000':'CGQ','6598454000':'DUB','6608729000':'LEH','6613824000':'BKG',
                                        '6615600000':'ALG','6617241000':'LEH','6620351000':'GOT','6621862000':'BTS',
                                        '6622014000':'SEL','6629120000':'CTG','6629120001':'CTG','6630862000':'HEL',
                                        '6638559000':'LEH','6654402000':'TPE','6676667000':'SHY','6677300000':'HOU',
                                        '6683233000':'TPE','6684466000':'VNA','6687085000':'KOP','6688334000':'MIL',
                                        '6689741000':'KUL','6690125000':'TPE','6691651000':'IST','6696868000':'CTG',
                                        '6697170000':'SHY','6717668000':'DUB','6717691000':'VNA','6724379000':'HEL',
                                        '6734641000':'NAP','6735543000':'SPB','6752158000':'SIN','6759233000':'BED',
                                        '6785678000':'VNA','6786015000':'SHY','6790144000':'HOU','6797801000':'ZRH',
                                        '6799412000':'HEL','6802601000':'SIN','6803393000':'HRB','6804306001':'JNB',
                                        '6824080000':'VNA','6829469000':'LEH','6837450000':'HAM',
                                        '6865770000':'RIJ','6867610000':'DXB','6890836000':'PRG','6896490000':'CND',
                                        '6903549000':'VNA','6904255000':'VNA','6905372000':'DDG','6905941000':'JKT',
                                        '6908478000':'LEH','6918820000':'HFA','6949910000':'RTM','6976214000':'PIR',
                                        '6985490000':'CTG','6988238000':'FRA','6996177000':'JNB','6997391000':'CPH',
                                        '7008144000':'PRG','7011163000':'ZRH','7016218000':'SEL','7026380000':'LEH',
                                        '7029618000':'VNA','7035395000':'CTG','7038379000':'VNA','7049789000':'SEL',
                                        '7063875000':'CTG','7118825000':'SHY','7135448000':'SHY','7136775000':'JIL',
                                        '7174670000':'SHY','7217376000':'SHY','7242659000':'SHY','7246292000':'PRG',
                                        '7255266000':'HRB','7257495000':'HOU','7314672000':'MIL','7356660000':'JIL',
                                        '7389501000':'SHY','7400854000':'SHY','7427789000':'VNA','7545657000':'ANR',
                                        '7558381000':'HOU','7558383000':'HOU','7558387000':'HOU','7558402000':'HOU',
                                        '7558403000':'HOU','7558415000':'HOU','7558416000':'HOU','7558418000':'HOU',
                                        '7558419000':'HOU']

    public void readExcel(String path, int ...parms){
        File excel = new File(path)
        if(excel.isFile() && excel.exists()){
            Workbook wb
            Sheet sheet
            wb = WorkbookFactory.create(new FileInputStream(excel))
            sheet = wb.getSheetAt(parms[0])
            handleSheet(sheet)
        }else{
            logger.warn("input excel path not exists or not a file, pls check!")
        }

    }

    protected void handleSheet(Sheet sheet, int ...parms){

        String lastSeq = ""
        def lastOfficeCde = []

        sheet.eachWithIndex{ current_Row, current_Row_idx ->
            logger.info("reading Raw ${current_Row_idx}")
            if(current_Row_idx < 4 || current_Row[0].toString() == "BKG_RQST_ID")return


            String current_CCC = new BigDecimal(current_Row[79].toString()).toPlainString()
            String OFFICE_CDE = ""
            boolean ConvertByCCC = false
            boolean duplicate_CS_BKG_REF_NUM = false
            boolean ConflictConvert = false

            OFFICE_CDE = legalCCC.get(current_CCC)
            if(OFFICE_CDE){
                ConvertByCCC = true
                if(lastSeq == current_Row[1].toString()){
                    duplicate_CS_BKG_REF_NUM = true
                    if((lastOfficeCde.size() > 0 && !lastOfficeCde.contains(OFFICE_CDE))){
                        ConflictConvert = true
                    }
                }else if(sheet.size() >= (current_Row_idx + 1) && sheet[current_Row_idx + 1] && sheet[current_Row_idx + 1][1].toString() == current_Row[1].toString()){
                    duplicate_CS_BKG_REF_NUM = true
                    String nextOFFICE_CDE = legalCCC.get(new BigDecimal(sheet[(current_Row_idx + 1)].getCell(79).toString()).toPlainString())
                    if((nextOFFICE_CDE && nextOFFICE_CDE != OFFICE_CDE)){
                        ConflictConvert = true
                    }
                }
            }
            String sub_str = "${current_Row[1]},${current_Row[43]},${current_Row[44]},${current_Row[45]},${current_Row[75]},${current_CCC},${OFFICE_CDE},${ConvertByCCC},${duplicate_CS_BKG_REF_NUM},${ConflictConvert}\r\n"
            Record += sub_str
            lastSeq = current_Row[1].toString()
            if(sheet.size() >= (current_Row_idx + 1) && sheet[current_Row_idx + 1] && sheet[current_Row_idx + 1].getCell(1).toString() == current_Row[1].toString()){
                lastOfficeCde.add(OFFICE_CDE)
            }else{
                lastOfficeCde = []
            }
        }
    }

    public static void main(String[] args){
        ExcelHandler eh = new ExcelHandler()
        eh.readExcel("D:\\A\\CCC\\result_8462p\\result_8462p1.xls", 0)
//        println(Record)

        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\A\\CCC\\result_8462p\\result.txt"))
        bw.write(Record)
        bw.close()

        logger.info("all finished.")
    }
}
