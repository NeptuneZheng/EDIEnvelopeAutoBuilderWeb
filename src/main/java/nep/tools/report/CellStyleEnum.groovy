package nep.tools.report

import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.model.StylesTable

public enum CellStyleEnum {
    GREY_25_PERCENT(createCellStyle(0),0),
    WHITE(createCellStyle(1),1)

    private final CellStyle style
    private final int index


    private CellStyleEnum(CellStyle style, int index){
        this.style = style
        this.index = index
    }
    public int getIndex() {
        return this.index
    }
    public CellStyle getStyle() {
        return this.style
    }

    private static CellStyle createCellStyle(int code){
        CellStyle style = new StylesTable().createCellStyle()
        initCommonCellStyle(style)
        switch (code){
            case 0: style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                ;break
            case 1: style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
                ;break
            default:break
        }
        return style
    }
    private static void initCommonCellStyle(CellStyle style){
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        style.setAlignment(HorizontalAlignment.CENTER);
    }
}
