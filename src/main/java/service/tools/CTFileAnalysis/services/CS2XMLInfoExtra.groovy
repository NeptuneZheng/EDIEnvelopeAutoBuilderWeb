package service.tools.CTFileAnalysis.services

import org.apache.log4j.Logger
import service.tools.CTFileAnalysis.common.ExtraInfoFromFiles

class CS2XMLInfoExtra extends ExtraInfoFromFiles{
    private static Logger logger = Logger.getLogger(this.class)

    private final static String BOOKING_NO = "<ns0:CarrierBookingNumber>([\\s\\S]*?)</ns0:CarrierBookingNumber>"
    private final static String CONTAINER_NO = "<ns0:ContainerNumber>([\\s\\S]*?)</ns0:ContainerNumber>"
    private final static String EVENT = "<ns0:CS1Event>([\\s\\S]*?)</ns0:CS1Event>"
    private final static String MSG_DT = "<ns1:LocDT TimeZone=\"HKT\" CSTimeZone=\"HKT\">([\\s\\S]*?)</ns1:LocDT>"

    //'CS200':'1','CS070':'24',
    private  static Map<String,String> EVENT_CONVEERSION = ['CS040':'1','CS195':'1','CS190':'24',
                                                                 'CS050':'24','CS130':'29','CS950':'48',
                                                                 'CS020':'74','CS210':'82','CS160':'98']

    CS2XMLInfoExtra(String path) {
        super(BOOKING_NO, CONTAINER_NO, EVENT, EVENT_CONVEERSION, MSG_DT, path)
    }

    public static void main(String[] args) {
        ExtraInfoFromFiles extra = new CS2XMLInfoExtra("")
        println(extra.getFilesCTInfo("D:\\1_B2BEDI_Revamp\\CT\\OUT_EDIFACT\\IKEA-COSU\\InputData").toString())
    }
}
