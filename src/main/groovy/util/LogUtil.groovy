package util

import org.apache.log4j.Logger

public class LogUtil {
    private static Logger logger = null

    public static Logger getLogger(Object obj){
        if(logger == null){
            logger = Logger.getLogger(obj.getClass())
            logger.debug("调用类: " + obj.class)
        }
        return logger
    }
}
