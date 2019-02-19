package excel

import com.google.common.io.Files
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.formula.functions.T
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

public abstract class ExcelUtils{
    private static String EXCEL_XLS = '.xls'
    private static String EXCEL_XLSX = '.xlsx'
    public synchronized void writeExcel(List<List<T>> rows, String sheetName, String filePath){
        Workbook workbook = getWrokbookWhenWrite(filePath)
        Sheet sheet = null
        if(workbook.getSheet(sheetName)){
            sheet = workbook.getSheet(sheetName)
        }else{
            workbook.createSheet(sheetName)
        }
        rows?.eachWithIndex{ curr_row, curr_row_idx ->
            Row row = null
            if(sheet.getRow(curr_row_idx)){
                row = sheet.getRow(curr_row_idx)
            }else{
                row = sheet.createRow(curr_row_idx)
            }
            curr_row?.eachWithIndex{ curr_cell, curr_cell_idx ->
                CellStyle style = row.getRowStyle()
                if (curr_cell instanceof Map) {
                    row.createCell(curr_cell_idx).setCellValue(curr_cell.get('value') ? curr_cell.get('value').toString() : '')
                    if (curr_cell.get('color')) {
                        style = workbook.createCellStyle()
                        style.setFillForegroundColor(curr_cell.get('color') as short)
                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND)
                        row.getCell(curr_cell_idx).setCellStyle(style)
                    } else {
                        row.getCell(curr_cell_idx).setCellStyle(style)
                    }
                } else {
                    row.createCell(curr_cell_idx).setCellValue(curr_cell.toString())
                    row.getCell(curr_cell_idx).setCellStyle(style)
                }
            }
        }
        createFile(filePath)
        OutputStream outputStream = new FileOutputStream(filePath)
        workbook.write(outputStream)

        workbook.close()
        outputStream.close()
    }

    public List<List<T>> readeExcel(T sheet_info, String filePath){
        Workbook workbook = getWorkbookWhenRead(filePath)
        List<List<T>> rows = new ArrayList<List<T>>()
        Sheet sheet = null
        if(sheet_info instanceof Integer){
            sheet = workbook.getSheetAt(sheet_info)
        }else if(sheet_info instanceof String){
            sheet = workbook.getSheet(sheet_info)
        }

        for(int i = 0; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i)
            List<T> cols = new ArrayList<T>()
            for(int j = 0; j < row.getLastCellNum(); j++) {
                cols.add(row.getCell(j).getStringCellValue() as T);
            }
            rows.add(cols)
        }
        return rows;
    }

    public abstract void  designExcel(Workbook workbook, String sheetName, int rows, File file)

    public  Workbook getWorkbookWhenRead(String filePath) throws IOException {
        File file = new File(filePath)
        createFile(filePath)

        InputStream inputStream = new FileInputStream(file);
        Workbook workbook = null;
        if(filePath.endsWith(EXCEL_XLS)) {
            workbook = new HSSFWorkbook(inputStream)
        }
        else if(filePath.endsWith(EXCEL_XLSX)) {
            workbook = new XSSFWorkbook(inputStream)
        }
        else {
            throw new IllegalArgumentException("Error ${filePath}: the file is not Excel file, pls check again. ");
        }

        inputStream.close()

        return workbook;
    }

    public   Workbook getWrokbookWhenWrite(String filePath) throws IllegalArgumentException{
        Workbook workbook = null;
        if(new File(filePath).isFile()){
            workbook = getWorkbookWhenRead(filePath)
        }
        else if(filePath.endsWith(EXCEL_XLS)) {
            workbook = new SXSSFWorkbook(1000);
        }
        else if(filePath.endsWith(EXCEL_XLSX)) {
            workbook = new SXSSFWorkbook(1000);
        }
        else {
            throw new IllegalArgumentException("Error ${filePath}: the file is not Excel file, pls check again. ")
        }
        return workbook;
    }

    public void createFile(String path){
        File file = new File(path)
        File tempFile
        if(!file.exists()){
            if(path.endsWith(EXCEL_XLS)) {
                tempFile = new File(this.getClass().getClassLoader().getResource("xls_template.xls").path)
            } else if(path.endsWith(EXCEL_XLSX)) {
                tempFile = new File(this.getClass().getClassLoader().getResource("xlsx_template.xlsx").path)
            } else {
                throw new IllegalArgumentException("Error ${path}: the file is not Excel file, pls check again. ");
            }
            Files.copy(tempFile,file)
        }
    }

}
