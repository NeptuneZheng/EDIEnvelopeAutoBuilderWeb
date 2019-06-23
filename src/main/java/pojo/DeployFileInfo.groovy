package pojo

class DeployFileInfo {
    static String SQL_PATH = "/resources/deploy/SQL"
    static String JSON_PATH = "/resources/deploy/api_sample"

    private String rootPath
    private String[] tps
    private String sqlTargetPath
    private String apiTargetPath

    DeployFileInfo(String rootPath, String[] tps, String sqlTargetPath, String apiTargetPath) {
        this.rootPath = rootPath
        this.tps = tps
        this.sqlTargetPath = sqlTargetPath
        this.apiTargetPath = apiTargetPath
    }

    public void extraCopyCellToQueue(Queue<CopyCell> Queue, String oriPath){

        if(tps.contains(oriPath.replaceAll(".*[/|\\\\]",""))){
            addToQueue(Queue,oriPath + SQL_PATH, this.sqlTargetPath)
            addToQueue(Queue,oriPath + JSON_PATH, this.apiTargetPath)
        }else {
            new File(oriPath).listFiles().each {
                extraCopyCellToQueue(Queue,it?.getAbsolutePath())
            }
        }

    }

    private void addToQueue(Queue<CopyCell> queue, String oriPath, String desPath){
//        Thread.sleep(300)
        if(oriPath){
            CopyCell cell
            new File(oriPath).listFiles().each {
                cell = new CopyCell(it.getAbsolutePath(),desPath + "/" + it?.getName())
                queue.offer(cell)
                println("add message to Queue: " + it?.getName())
            }
        }
    }

}
