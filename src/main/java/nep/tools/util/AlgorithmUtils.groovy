package nep.tools.util

import org.apache.log4j.Logger
import pojo.Cell

/*
 * Needleman–Wunsch algorithm
 * https://www.cnblogs.com/milton/p/9258506.html
 * https://w-sl.iteye.com/blog/2306072
 * https://blog.csdn.net/cao478208248/article/details/39029565
 * https://blog.csdn.net/shijing_0214/article/details/53100992
 * https://www.cnblogs.com/houkai/p/3973207.html
 */
class AlgorithmUtils {
    private static Logger logger = Logger.getLogger(this.class)
    static ArrayUtils utils = new ArrayUtils()


    protected void dataCheck(Object[] row, Object[] column){
        if(row == null || column == null){
            throw new RuntimeException("AlgorithmUtils request analysis array size must greater than 0")
        }
    }

    private static int min(int... values){
        int min = Integer.MAX_VALUE
        for(int i : values){
            if(min > i){
                min = i
            }
        }

        return min
    }

    public  int LevenshteinDistance(Object[] row, Object[] column){
        dataCheck(row, column)

        int[][] diff = new int[row.length + 1][column.length + 1]

        for(int i = 0; i <= row.length ; i++){
            diff[i][0] = i
        }
        for(int j = 0; j <= column.length ; j++){
            diff[0][j] = j
        }

        int ld = 0
        for(int i = 1; i <= row.length; i++){
            for(int j = 1; j <= column.length; j++){
                if(row[i - 1] == column[j - 1]){
                    ld = 0
                }else {
                    ld = 1
                }
                diff[i][j] = min(diff[i - 1][j -1] + ld, diff[i - 1][j] +1, diff[i][j - 1] + 1)
            }
        }

//        println(utils.arrayToString((Integer[][])diff))

        return diff[row.length][column.length]
    }

    public Integer[][] NeedlemanWunsch(Object[] row, Object[] column){
        dataCheck(row, column)

        int row_len = row.length
        int column_len = column.length

        Integer[][] cells = new Integer[row_len + 1][column_len + 1]
        //init 1st row/column value
        cells[0] = (utils.BatchAssignment(cells[0], 0, 0, cells[0].length)) as Integer[]
        for(int i = 0; i < row_len + 1; i++){
            cells[i][0] = 0
        }

        //core compare
        int score = 0
        for(int i = 1 ; i <= row_len; i++){
            for(int j = 1; j <= column_len; j++){
                if(row[i -1] == column[j - 1]){
                    cells[i][j] = cells[i - 1][j - 1] + 1
                }else{
                    cells[i][j] = Math.max(cells[i - 1][j],cells[i][j - 1])
                }
            }
        }

//        println(utils.arrayToString(cells))
        return cells
    }

    public Object[][] NeedlemanWunschOptimumSolution(Object[] row, Object[] column){
        Object[][] result = new Object[2]

        Integer[][] solution = this.NeedlemanWunsch(row,column)

        List<Cell> row_cells = new ArrayList<>()
        List<Cell> column_cells = new ArrayList<>()

        int row_len = row.length
        int column_len = column.length

        while (row_len > 0 || column_len > 0){
            Cell row_cell
            Cell column_cell
            if(row_len == 0){
                row_cell = new Cell(row_len,column_len,null,solution[row_len][column_len - 1])
                column_cell = new Cell(row_len,column_len,column[column_len - 1],solution[row_len][column_len - 1])
                column_len--
            }else if(column_len == 0){
                row_cell = new Cell(row_len,column_len,row[row_len - 1],solution[row_len - 1][column_len])
                column_cell = new Cell(row_len,column_len,null,solution[row_len - 1][column_len])
                row_len--
            }else if(row[row_len - 1] == column[column_len - 1]){
                row_cell = new Cell(row_len,column_len,row[row_len - 1],solution[row_len][column_len])
                column_cell = new Cell(row_len,column_len,column[column_len - 1],solution[row_len][column_len])
                row_len--
                column_len--
            }else{
                int maxGrade = Math.max(Math.max(solution[row_len - 1][column_len - 1],solution[row_len - 1][column_len]),solution[row_len][column_len - 1])

                if(solution[row_len - 1][column_len - 1] == maxGrade){
                    row_cell = new Cell(row_len,column_len,row[row_len - 1],solution[row_len][column_len])
                    column_cell = new Cell(row_len,column_len,column[column_len - 1],solution[row_len][column_len])
                    row_len--
                    column_len--
                }else if(solution[row_len - 1][column_len] == maxGrade){
                    row_cell = new Cell(row_len,column_len,row[row_len - 1],solution[row_len - 1][column_len])
                    column_cell = new Cell(row_len,column_len,null,solution[row_len - 1][column_len])
                    row_len--
                }else if(solution[row_len][column_len - 1] == maxGrade){
                    row_cell = new Cell(row_len,column_len,null,solution[row_len][column_len - 1])
                    column_cell = new Cell(row_len,column_len,column[column_len - 1],solution[row_len][column_len - 1])
                    column_len--
                }
            }



            row_cells.add(row_cell)
            column_cells.add(column_cell)
        }

        result[0] = row_cells
        result[1] = column_cells

        return result
    }

    public List<Object> NeedlemanWunschOptimumSolutionPairValues(Cell[] cells, Object nullReplacer){
        List<Object> result = new ArrayList<>()
        for(int i = cells.length; i > 0; i--){
            if(cells[i - 1].getValue() == null){
                cells[i - 1].setValue(nullReplacer)
            }
            result.add(cells[i - 1].getValue())
        }

        return result
    }


    public static void main(String[] args) {
        String[] a = "cabccddfg".toCharArray()
        String[] b = "aebddscssdd".toCharArray()

        AlgorithmUtils algorithmUtils = new AlgorithmUtils()

//        logger.info(algorithmUtils.SimilityOfArray(a,b))
//        logger.info(NeedlemanWunsch(a,b)[0][1])
        println(algorithmUtils.NeedlemanWunschOptimumSolutionPairValues(algorithmUtils.NeedlemanWunschOptimumSolution(a,b)[0] as Cell[],"_"))
        println(algorithmUtils.NeedlemanWunschOptimumSolutionPairValues(algorithmUtils.NeedlemanWunschOptimumSolution(a,b)[1] as Cell[],"_"))

    }
}
