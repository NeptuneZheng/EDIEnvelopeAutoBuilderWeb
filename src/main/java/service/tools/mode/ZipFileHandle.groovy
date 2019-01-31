package service.tools.mode

class ZipFileHandle {
    enum legalZipMode{zip('zip'),tag('tag'),tar('tar')}
    protected String folderPath
    protected String zipMode
    protected String outputPath

    ZipFileHandle() {
    }

    ZipFileHandle(String folderPath, String zipMode, String outputPath) {
        this.folderPath = folderPath
        this.zipMode = zipMode
        this.outputPath = outputPath
    }

    String getFolderPath() {
        return folderPath
    }

    void setFolderPath(String folderPath) {
        this.folderPath = folderPath
    }

    String getZipMode() {
        return zipMode
    }

    void setZipMode(String zipMode) {
        this.zipMode = zipMode
    }

    String getOutputPath() {
        return outputPath
    }

    void setOutputPath(String outputPath) {
        this.outputPath = outputPath
    }

    void decompression(ZipFileHandle zipFileHandle){
    }

    void compression(ZipFileHandle zipFileHandle){}

    public static void main(String[] args){

        println(new ZipFileHandle('a','a','a'))
    }
}
