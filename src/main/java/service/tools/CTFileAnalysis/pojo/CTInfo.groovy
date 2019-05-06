package service.tools.CTFileAnalysis.pojo

class CTInfo {
    private String bkgNo
    private String fileName
    private String containerNo
    private String event
    private String date

    CTInfo() {}

    CTInfo(String bkgNo, String fileName, String containerNo, String event, String date) {
        this.bkgNo = bkgNo
        this.fileName = fileName
        this.containerNo = containerNo
        this.event = event
        this.date = date
    }

    String getBkgNo() {
        return bkgNo
    }

    void setBkgNo(String bkgNo) {
        this.bkgNo = bkgNo
    }

    String getFileName() {
        return fileName
    }

    void setFileName(String fileName) {
        this.fileName = fileName
    }

    String getContainerNo() {
        return containerNo
    }

    void setContainerNo(String containerNo) {
        this.containerNo = containerNo
    }

    String getEvent() {
        return event
    }

    void setEvent(String event) {
        this.event = event
    }

    String getDate() {
        return date
    }

    void setDate(String date) {
        this.date = date
    }

    @Override
    String toString() {
        return "${fileName?:''}@${bkgNo?:''}@${containerNo?:''}@${event?:''}@${date?:''}"
    }
}
