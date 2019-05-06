package service.tools

import net.lingala.zip4j.core.ZipFile
import net.lingala.zip4j.model.FileHeader
import org.apache.log4j.Logger
import service.tools.mode.ZipFileHandle

class DecompressFiles extends ZipFileHandle{
    private Logger logger = Logger.getLogger(DecompressFiles.class)

    DecompressFiles() {}

    DecompressFiles(String folderPath, String zipMode, String outputPath) {
        super(folderPath, zipMode, outputPath)
    }

    void decompression() {
        if(this.getZipMode() ){

        }
        File files = new File(this.getFolderPath())
        files.listFiles().eachWithIndex{ current_File, current_File_idx ->
            logger.info('decompression file name: ' + current_File.getAbsolutePath())
            if(current_File.isFile()){
                ZipFile zipFile = new ZipFile(current_File)
                zipFile.setFileNameCharset("UTF-8")
                zipFile.extractFile(zipFile.getFileHeaders().get(0) as FileHeader,this.getOutputPath(),null,current_File.getName())

                List<FileHeader> headerList = zipFile.getFileHeaders()
                for(FileHeader header : headerList){
                    logger.info(header.compressionMethod)
                }

            }else{
                decompression(new ZipFileHandle(current_File.getAbsolutePath(),this.getZipMode(),this.getOutputPath()))
            }
        }
    }

    void compression(ZipFileHandle zipFileHandle) {

    }

    public static void main(String[] args) {
        ZipFileHandle handler = new DecompressFiles("D:\\1_B2BEDI_Revamp\\EDIREJECT\\OUT_XML\\CARGOSMART\\resources\\data\\O","zip","D:\\1_B2BEDI_Revamp\\EDIREJECT\\OUT_XML\\CARGOSMART\\ExpectedComplete")
//        ZipFileHandle handler = new DecompressFiles("D:\\A\\T","zip","D:\\A")
        handler.decompression()
    }
}
