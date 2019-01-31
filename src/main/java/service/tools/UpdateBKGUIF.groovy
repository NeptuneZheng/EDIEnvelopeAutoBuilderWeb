package service.tools

class UpdateBKGUIF {
    static void main(String[] args){
        String fileDir = "D:\\1_B2BEDI_Revamp\\BR\\IN_XML\\TPV-COSU\\InputData"  //Dir

        def fileList = new ArrayList<String>()

        new File(fileDir).eachFile {file->
            println(file.getName())
            file.eachLine {line->
//                if(line.contains('       AAAA    ')){
//                    line = line.padRight(100,' ')
//                }
                int i = new Random().nextInt(10000)
                if(line.contains('</ShipmentID>')){
                    line = line.replaceAll(".{20}</ShipmentID>",  i + "NeptuneA</ShipmentID>")
                }
//                int i = new Random().nextInt(100)
//                if(line.contains('</ShipmentIdentifier>')){
//                    line = line.replaceAll('.{4}</ShipmentIdentifier>','') + i + 'NP2</ShipmentIdentifier>'
//                }
//                if(line.contains('</DocumentIdentifier>')){
//                    line = line.replaceAll('.{4}</DocumentIdentifier>','') + i + 'NP2</DocumentIdentifier>'
//                }
                fileList.add(line)
            }
            file.withPrintWriter {printWriter->
                for(String line : fileList){
                    printWriter.println(line)
                }
            }
            fileList.clear()
        }
    }
}
