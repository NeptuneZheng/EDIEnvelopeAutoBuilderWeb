package service.tools

import org.apache.log4j.Logger
import service.tools.mode.ZipFileHandle

class DecompressFiles extends ZipFileHandle{
    private Logger logger = Logger.getLogger(DecompressFiles.class)

    void decompression(ZipFileHandle zipFileHandle) {
        if(zipFileHandle.getZipMode() ){

        }
        File files = new File(zipFileHandle.getFolderPath())
        files.listFiles().eachWithIndex{ current_File, current_File_idx ->
            logger.info('decompression file name: ' + current_File.getAbsolutePath())
            if(current_File.isFile()){


            }else{
                decompression(new ZipFileHandle(current_File.getAbsolutePath(),zipFileHandle.getZipMode(),zipFileHandle.getOutputPath()))
            }
        }
    }

    void compression(ZipFileHandle zipFileHandle) {

    }
}
