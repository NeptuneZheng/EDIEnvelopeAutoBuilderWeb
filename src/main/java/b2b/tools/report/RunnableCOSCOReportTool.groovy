package b2b.tools.report

import excel.ExcelUtils
import org.apache.poi.ss.formula.functions.T
import org.apache.poi.ss.usermodel.*

class RunnableCOSCOReportTool extends ExcelUtils implements Runnable {
    private String filePath, sheetName
    private List<List<T>> rows

    RunnableCOSCOReportTool() {
    }

    RunnableCOSCOReportTool(String filePath, String sheetName, List<List<T>> rows) {
        this.filePath = filePath
        this.sheetName = sheetName
        this.rows = rows
    }

    String getFilePath() {
        return filePath
    }

    void setFilePath(String filePath) {
        this.filePath = filePath
    }

    String getSheetName() {
        return sheetName
    }

    void setSheetName(String sheetName) {
        this.sheetName = sheetName
    }

    List<List<T>> getRows() {
        return rows
    }

    void setRows(List<List<T>> rows) {
        this.rows = rows
    }

    synchronized void designExcel(Workbook workbook, String sheetName, int rows, File file) {
        if(rows > 0 && workbook != null){
            Sheet sheet = null
            if(workbook.getSheet(sheetName) != null){
                sheet = workbook.getSheet(sheetName)
            }else if(sheetName){
                sheet = workbook.createSheet(sheetName)
            }

            CellStyle style = workbook.createCellStyle()
            initCommonCellStyle(style)
            style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex())
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND)

            CellStyle style1 = workbook.createCellStyle()
            initCommonCellStyle(style1)
            style1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex())
            style1.setFillPattern(FillPatternType.SOLID_FOREGROUND)

            CellStyle style2 = workbook.createCellStyle()
            initCommonCellStyle(style2)
            style2.setFillForegroundColor(IndexedColors.WHITE.getIndex())
            style2.setFillPattern(FillPatternType.SOLID_FOREGROUND)

            sheet.createRow(0)
            sheet.getRow(0).setRowStyle(style)

            for(int i= 1; i <= rows; i++){
                sheet.createRow(i)
                if(i % 2 == 1){
                    sheet.getRow(i).setRowStyle(style1)
                }else{
                    sheet.getRow(i).setRowStyle(style2)
                }
            }
            workbook.write(new FileOutputStream(file))
            workbook.close()
        }
    }

    private void initCommonCellStyle(CellStyle style){
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        style.setAlignment(HorizontalAlignment.CENTER);
    }

    void run() {
        println(">> new thread ${Thread.getName()} runnable ....")
        this.designExcel(this.getWrokbookWhenWrite(this.filePath),this.sheetName,this.rows.size(), new File(this.filePath))
        this.writeExcel(this.rows,this.sheetName,this.filePath)
        println(">> thread ${Thread.getName()} finished ....")
    }

    public static void main(String[] args){
        String path = "D:/A/test.xlsx"
        ExcelUtils tool = new RunnableCOSCOReportTool()
        tool.designExcel(tool.getWrokbookWhenWrite(path),"zhengne0",10,new File(path))
        tool.designExcel(tool.getWrokbookWhenWrite(path),"zhengne1",10,new File(path))

    }
}
