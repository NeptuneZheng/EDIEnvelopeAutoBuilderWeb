package pojo

class CopyCell {
    private String oriPath
    private String desPath

    CopyCell(String oriPath, String desPath) {
        this.oriPath = oriPath
        this.desPath = desPath
    }

    String getOriPath() {
        return oriPath
    }

    String getDesPath() {
        return desPath
    }

    void setOriPath(String oriPath) {
        this.oriPath = oriPath
    }

    void setDesPath(String desPath) {
        this.desPath = desPath
    }

    @Override
    String toString() {
        return "oriPath : ${oriPath}, desPath: ${desPath}"
    }
}
