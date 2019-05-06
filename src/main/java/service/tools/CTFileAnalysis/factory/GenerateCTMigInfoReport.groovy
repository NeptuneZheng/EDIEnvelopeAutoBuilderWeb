package service.tools.CTFileAnalysis.factory

import org.apache.log4j.Logger
import service.tools.CTFileAnalysis.common.ExtraInfoFromFiles
import service.tools.CTFileAnalysis.pojo.CTInfo
import service.tools.CTFileAnalysis.services.CS2XMLInfoExtra
import service.tools.CTFileAnalysis.services.EDIInfoExtra

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

class GenerateCTMigInfoReport {
    private static final Logger logger = Logger.getLogger(this.class)
    private static StringBuilder stringBuilder = new StringBuilder()

    public static void main(String[] args) {
        long start_time = System.currentTimeMillis()

        //1, init extra folder path
        String cs2xmlFolder = "D:\\1_B2BEDI_Revamp\\CT\\OUT_EDIFACT\\IKEA-COSU\\resources\\data\\V1\\CS2\\SingleFile\\"
        String cosuEDIFolder = "D:\\1_B2BEDI_Revamp\\CT\\OUT_EDIFACT\\IKEA-COSU\\resources\\data\\V1\\COSU\\O\\"
        String report_save_path = "D:\\1_B2BEDI_Revamp\\CT\\OUT_EDIFACT\\IKEA-COSU\\resources\\data\\V1\\CTInfoReport.txt"

        //2, add report header line
        String reportHeader = "CS2_fileName@CS2_bkgNo@CS2_containerNo@CS2_event@CS2_MsgDT@COSU_fileName@COSU_bkgNo@COSU_containerNo@COSU_event@COSU_MsgDT\r\n"
        stringBuilder.append(reportHeader)
        //2.1, define unlink string
        String half_unlink_str = "@@@@"

        //3, extra CT info
        ExtraInfoFromFiles cs2xml_extra = new CS2XMLInfoExtra(cs2xmlFolder)
        ExtraInfoFromFiles cosu_extra = new EDIInfoExtra(cosuEDIFolder)

        FutureTask<Map<String,CTInfo>> futureTask1 = new FutureTask<>(cs2xml_extra)
        FutureTask<Map<String,CTInfo>> futureTask2 = new FutureTask<>(cosu_extra)

        ExecutorService executor = Executors.newFixedThreadPool(2)
        executor.execute(futureTask1)
        executor.execute(futureTask2)

        while (!(futureTask1.isDone() && futureTask2.isDone())){
            sleep(3000)
            logger.warn("future tasks still under handling, pls wait ..... ")
        }

        Map<String,CTInfo> cs2xml_map = futureTask1.get()
        Map<String,CTInfo> cosu_map = futureTask2.get()

        executor.shutdown()
        //4, link the info
        Set<String> set = cs2xml_map.keySet() + cosu_map.keySet()
        logger.info("total distinct booking number size is: ${set.size()}")

        int i = 0
        for(String bkgNo : set){
            logger.info("${i} -- now handling link process bkgNo: ${bkgNo}")
            stringBuilder.append((cs2xml_map.get(bkgNo)?:half_unlink_str))
            stringBuilder.append("@")
            stringBuilder.append((cosu_map.get(bkgNo)?:half_unlink_str))
            stringBuilder.append("\r\n")

            i++
        }

        //5, save report
        logger.info("writing report ... ")
        File report = new File(report_save_path)
        FileWriter writer = new FileWriter(report)
        writer.write(stringBuilder.toString())

        writer.flush()
        writer.close()

        long end_time = System.currentTimeMillis()
        logger.info("all tasks finished, total cost: ${end_time - start_time} ms")
    }
}
