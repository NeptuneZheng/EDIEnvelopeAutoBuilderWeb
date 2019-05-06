package service.tools.CTFileAnalysis.services

import org.apache.commons.io.FileUtils
import org.apache.log4j.Logger
import org.dom4j.Document
import org.dom4j.DocumentHelper
import org.dom4j.Element
import org.dom4j.Node
import org.dom4j.io.SAXReader

class SplitCS2XML {
    private static Logger logger = Logger.getLogger(SplitCS2XML.class)

    public static void main(String[] args) {
        String source_path = "D:\\1_B2BEDI_Revamp\\CT\\OUT_EDIFACT\\IKEA-COSU\\resources\\data\\V1\\CS2\\DL-20190415092913\\"
        String dest_path = "D:\\1_B2BEDI_Revamp\\CT\\OUT_EDIFACT\\IKEA-COSU\\resources\\data\\V1\\CS2\\SingleFile\\"

        executor(source_path,dest_path)
    }

    static void executor(String source_path, String dest_path) {
        File files = new File(source_path)

        if(files.isDirectory()){
            files.listFiles().each { curr_File ->
                executor(curr_File.getAbsolutePath(),dest_path)
            }
        }else if(files.isFile()){
            splitByBody(files,dest_path)
        }
    }

    static void splitByBody(File file, String dest_path) {
        logger.info("now splitting file: " + file.getName())

        SAXReader reader = new SAXReader()
        Document document = reader.read(file)

        FileWriter writer

        Node header = (Node)document.getRootElement().element("Header").clone()
        def bodys = document.getRootElement().elements("Body")

        if(bodys.size() > 1){
            bodys.eachWithIndex { curr_body, curr_body_idx ->
                Document atom = DocumentHelper.createDocument()
                Element root = (Element)document.getRootElement().clone()

                int before = curr_body_idx - 0
                int last = bodys.size() - curr_body_idx
                for(int i =curr_body_idx + 1; i < bodys.size(); i++){
                    root.elements("Body").remove(curr_body_idx + 1)
                }
                for(int i =0; i < curr_body_idx; i++){
                    root.elements("Body").remove(0)
                }
                atom.add(root)

                String fileName = dest_path+ file.getName() + "_${curr_body_idx}"
                writer = new FileWriter(new File(fileName))

                logger.info(curr_body_idx + " --- spltting subFile: " + fileName)
                writer.write(atom.asXML())
                writer.flush()

            }
        }else{
            logger.info("single file already, copy to dest folder....")
            FileUtils.copyFile(file,new File(dest_path+file.getName()))
        }
        if(writer){
            writer.close()
        }

    }
}
