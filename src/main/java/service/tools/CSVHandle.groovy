package service.tools

import au.com.bytecode.opencsv.CSVReader

class CSVHandle {
    public static void main(String[] args){
        String csvFilePath = 'C:\\Users\\ZHENGNE\\Desktop\\Going\\WorkPlan\\BR_Migration\\WS_Server\\Item\\tables\\bkg_basic_ws.csv'
        String oriFile1 = 'C:\\Users\\ZHENGNE\\Desktop\\Going\\WorkPlan\\BR_Migration\\WS_Server\\Item\\to iris2 file'

        String fileSavePath1 = 'C:\\Users\\ZHENGNE\\Desktop\\Going\\WorkPlan\\BR_Migration\\WS_Server\\Item\\Data\\InputData'
        String fileSavePath2 = 'C:\\Users\\ZHENGNE\\Desktop\\Going\\WorkPlan\\BR_Migration\\WS_Server\\Item\\Data\\ExpectedComplete'
        CSVReader reader = new CSVReader(new FileReader(csvFilePath))
        List<String[]> list = reader.readAll()
        List<String> input_lists = new ArrayList<>()
        List<String> input_files = new ArrayList<>()
        list.eachWithIndex{ current_li , current_li_idx ->
            input_lists.add(current_li[0])
            input_files.add(current_li[39])
        }

        FileWriter fileWriter
        int count = 0
        new File(oriFile1).listFiles().eachWithIndex{ current_file, current_file_idx ->
            String name = current_file.getName().substring(5,12)
            if(input_lists.contains(name)){
                String saveFileName = '\\' + current_file_idx + '_' + name + '.edi'
                current_file.renameTo(fileSavePath2 + saveFileName)
                int idx = input_lists.indexOf(name)

                fileWriter = new FileWriter(new File(fileSavePath1 + saveFileName))
                fileWriter.write(input_files[idx])

                fileWriter.close()

                println("(${count}-${current_file_idx})write matched file pairs**${name}---${current_file.getName()}")
                count++
            }
        }
    }
}
