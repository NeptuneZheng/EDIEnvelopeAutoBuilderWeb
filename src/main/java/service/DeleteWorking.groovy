package service

/**
 * Created by CAIKE2 on 11/23/2017.
 */
class DeleteWorking {


    public static void main(String[] args) throws Exception {
          deleteWorkingStatus("D:\\home\\tibco\\B2BEDI\\mirgration\\BR\\LEPRINOAMBER\\InputData\\")
    }


    static String[] listDirectory(File dir)throws IOException{
        if(!dir.exists()){
            throw new IllegalArgumentException("File"+dir+"not exists")
        }
        if(!dir.isDirectory()){
            throw new IllegalArgumentException(dir+"not exists")
        }

        String [] filenames = dir.list()
        return filenames
    }

    static boolean deleteWorkingStatus(String srcFile) throws IOException{
        boolean b = true
        String [] filenames = listDirectory(new File(srcFile))
        for (String string : filenames) {
            String delete_Working = ""
            if(string.contains("_working")){
                delete_Working = string.replace("_working", "")
                try{
                    new File(srcFile+string)?.renameTo( new File(srcFile+delete_Working))
                }catch (Exception e){
                    b = false
                    throw new RuntimeException(e)
                }
            }
        }
        return b
    }
}
