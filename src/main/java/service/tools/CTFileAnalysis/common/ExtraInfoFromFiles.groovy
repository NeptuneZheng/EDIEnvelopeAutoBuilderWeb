package service.tools.CTFileAnalysis.common

import org.apache.log4j.Logger
import service.tools.CTFileAnalysis.pojo.CTInfo

import java.util.concurrent.Callable
import java.util.regex.Matcher
import java.util.regex.Pattern

abstract class ExtraInfoFromFiles implements Callable<Map<String,CTInfo>> {
    private static Logger logger = Logger.getLogger(this.class)

    public  Map<String,CTInfo> map = new HashMap<>()

    private  String BOOKING_NO
    private  String CONTAINER_NO
    private  String EVENT
    private  String MSG_DT
    private  Map<String,String> EVENT_CONVEERSION
    private  String path

    ExtraInfoFromFiles(String BOOKING_NO, String CONTAINER_NO, String EVENT, Map<String, String> EVENT_CONVEERSION, String MSG_DT, String path) {
        this.BOOKING_NO = BOOKING_NO
        this.CONTAINER_NO = CONTAINER_NO
        this.EVENT = EVENT
        this.MSG_DT = MSG_DT
        this.EVENT_CONVEERSION = EVENT_CONVEERSION
        this.path = path
    }

    public Map<String,CTInfo> getFilesCTInfo(String path){
        File files = new File(path)
        if(files.isFile()){
            extra(files)
        }else if(files.isDirectory()){
            files.listFiles().each {file ->
                getFilesCTInfo(file.getAbsolutePath())
            }
        }

        return map
    }

    void extra(File file) {
//        logger.info("now extra file: ${file.getName()}")
        CTInfo ctInfo = new CTInfo()
        ctInfo.setFileName(file.getName())
        Pattern pattern

        BufferedReader reader = new BufferedReader(new FileReader(file))
        String line = ''
        while ((line = reader.readLine()) != null){
            if(trim(line).matches(BOOKING_NO)){
                pattern = Pattern.compile(BOOKING_NO)
                Matcher matcher = pattern.matcher(line)
                if(matcher.find()){
                    ctInfo.setBkgNo(matcher.group(1))
                }
            }else if(trim(line).matches(CONTAINER_NO)){
                pattern = Pattern.compile(CONTAINER_NO)
                Matcher matcher = pattern.matcher(line)
                if(matcher.find()){
                    ctInfo.setContainerNo(matcher.group(1))
                }
            }else if(trim(line).matches(EVENT)){
                pattern = Pattern.compile(EVENT)
                Matcher matcher = pattern.matcher(line)
                if(matcher.find()){
                    ctInfo.setEvent(EVENT_CONVEERSION.get(matcher.group(1))?:matcher.group(1))
                }
            }else if(trim(line).matches(MSG_DT)){
                pattern = Pattern.compile(MSG_DT)
                Matcher matcher = pattern.matcher(line)
                if(matcher.find()){
                    ctInfo.setDate(matcher.group(1))
                }
            }

        }
        map.put(ctInfo.getBkgNo(),ctInfo)
    }

    static String trim(String input){
        String result = ''
        if(input){
            result = input.replaceAll("^\\s*","").replaceAll("\\s*\$","")
        }
        return result
    }

    Map<String,CTInfo> call(){
        getFilesCTInfo(this.path)
    }

    public static void main(String[] args) {
        println(trim("    \t\t\t<ns0:CarrierBookingNumber>6203786150</ns0:CarrierBookingNumber>     "))
    }
}
