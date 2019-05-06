package service.tools.CTFileAnalysis.services

import org.apache.log4j.Logger
import service.tools.CTFileAnalysis.common.ExtraInfoFromFiles

class EDIInfoExtra extends ExtraInfoFromFiles{
    private static Logger logger = Logger.getLogger(this.class)

    private final  static String BOOKING_NO = "RFF\\+BM:([\\s\\S]*?)'"
    private final  static String CONTAINER_NO = "EQD\\+CN\\+([\\s\\S]*?)\\+.*"
    private final  static String EVENT = "STS\\+1\\+([\\s\\S]*?)'"
    private final static String MSG_DT = ".*?IKEA\\.EBCCNS1:ZZ\\+([\\s\\S]*?)\\+.*"

    private final  static Map<String,String> EVENT_CONVEERSION = [:]

    EDIInfoExtra(String path) {
        super(BOOKING_NO, CONTAINER_NO, EVENT, EVENT_CONVEERSION, MSG_DT, path)
    }


    public static void main(String[] args) {
        ExtraInfoFromFiles extra = new EDIInfoExtra("")
        println(extra.getFilesCTInfo("D:\\1_B2BEDI_Revamp\\CT\\OUT_EDIFACT\\IKEA-COSU\\ExpectedComplete").toString())
    }
}
