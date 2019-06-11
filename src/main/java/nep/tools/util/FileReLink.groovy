package nep.tools.util

import pojo.Cell

class FileReLink extends AlgorithmUtils {
    private double simility = 1.0
    private String segment_seperater

    FileReLink() {}

    FileReLink(double simility) {
        this.simility = simility
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
                String[] tmp_rows = row[i -1].toString().toCharArray()
                String[] tmp_column = column[j - 1].toString().toCharArray()
                int max_length = Math.max(tmp_column.length,tmp_rows.length)
                if((1 - LevenshteinDistance(tmp_rows,tmp_column)/max_length) >= simility){
                    cells[i][j] = cells[i - 1][j - 1] + 1
                }else{
                    cells[i][j] = Math.max(cells[i - 1][j],cells[i][j - 1])
                }
            }
        }
        return cells
    }

    public static void main(String[] args) {
        String[] a = "aeb\r\ncc\r\ndd\r\nfg".split("\r\n")
        String[] b = "caeb\r\ndd\r\ns\r\ncc\r\nss\r\ndd".split("\r\n")

        FileReLink algorithmUtils = new FileReLink()

//        logger.info(algorithmUtils.SimilityOfArray(a,b))
//        logger.info(NeedlemanWunsch(a,b)[0][1])
        println(algorithmUtils.NeedlemanWunschOptimumSolutionPairValues(algorithmUtils.NeedlemanWunschOptimumSolution(a,b)[0] as Cell[],"_"))
        println(algorithmUtils.NeedlemanWunschOptimumSolutionPairValues(algorithmUtils.NeedlemanWunschOptimumSolution(a,b)[1] as Cell[],"_"))

    }
}
