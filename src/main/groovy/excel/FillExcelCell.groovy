package excel

import org.apache.poi.ss.formula.functions.T
import org.apache.poi.ss.usermodel.Row

interface FillExcelCell {
    public void fillCell(Row row, T curr_cell, int curr_cell_idx)
}