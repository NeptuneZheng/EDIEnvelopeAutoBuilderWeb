package service.tools

class UpdateFileName {
    public static void main(String[] args){
        def path = 'D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU_MIGRATION\\ExpectedComplete-ori'
        def des_path = 'D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU_MIGRATION\\ExpectedComplete-O'
//        def des_path_I = 'D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU_UIF\\InputData'
//        def des_path_O = 'D:\\1_B2BEDI_Revamp\\BR\\OUT_UIF\\COSU_UIF\\ExpectedComplete'

//        File out_i = new File(des_path_I)
//        File out_o = new File(des_path_O)

//        out_i.mkdir()
//        out_o.mkdir()

//        updateName(path,des_path_I,des_path_O)
        updateName(path,des_path)
    }

    public static void updateName(String path,String des_path_I, String des_path_O){
        File files = new File(path)

        if(files.listFiles().size() > 0){
            for(File file : files.listFiles()){
                if(file.isDirectory()){
                    updateName(file.path,des_path_I,des_path_O)
                }else if(file.isFile()){
                    def old_file_name = file.getName()
                    def new_file_name = ''
                    boolean a = false
                    if(old_file_name.length() > 24){
                        new_file_name = des_path_O + '/' + old_file_name.replaceAll('_[\\s\\S]*','')

                    }else if(old_file_name.length() == 24){
                        new_file_name = des_path_I + '/' + old_file_name.replaceAll('_[\\s\\S]*','')
                    }
                    a = file.renameTo(new_file_name)
                    if(a){
                        println("update file name sucess form: ${file.getAbsoluteFile()}----> ${new_file_name}")
                    }else{
                        println("update file name fail: ${file.getAbsoluteFile()}, pls have a look!")
                        System.exit(1)
                    }
                }
            }
        }
    }

    public static void updateName(String path, String des_path){
        File files = new File(path)
        if(files.isFile()){
            def newName = des_path + '\\' + files.getName().replaceAll('_[\\s\\S]*','')
            println("Rename Sucess from ${files.getName()} ----> ${newName}")
            files.renameTo(newName)
        }else if(files.isDirectory()){
            files.listFiles().eachWithIndex{ current_File , current_File_idx ->
                updateName(current_File.getAbsolutePath(),des_path)
            }
        }

    }

}
