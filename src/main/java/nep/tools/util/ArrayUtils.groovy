package nep.tools.util

class ArrayUtils {
    public Object[] copyArray(Object[] arrs,int start, int end){
        Object[] result = []

        if(arrs && arrs.length > start){
            end = Math.min(arrs.length,end)
            result = Arrays.copyOfRange(arrs,start,end)
        }
        return result
    }

    public Object[] BatchAssignment(Object[] source,Object common_value, int start_idx, int end_idx){
        if(source && source.length > start_idx+1){
            if(source.length < end_idx){
                end_idx = source.length
            }
            for(; start_idx < end_idx; start_idx++){
                source[start_idx] = common_value
            }
        }
        return source
    }

    public String arrayToString(Object[][] arrs){
        StringBuilder result = new StringBuilder()

        if(arrs && arrs.length > 0){
            for(int i = 0; i < arrs.length; ){
                result.append("[")
                if(arrs[i] && arrs[i].length > 0){
                    for(int j = 0; j < arrs[i].length;){
                        result.append(arrs[i][j].toString().padLeft(4,' '))
                        if(++j < arrs[i].length){
                            result.append(", ")
                        }
                    }
                    ++i
                }else{
                    result.append(arrs[i].toString().padLeft(4,' '))
                    if(++i < arrs.length){
                        result.append(", ")
                    }
                }
                result.append("]\r\n")
            }
        }

        return result.toString()
    }

    public int countCommonValueofArrays(Object[] target, Object[] source){

        int count = 0
        int anchor = 0
        for (int i = 0; i < target.length; i++){
            for (int j = anchor; j < source.length;j++){
                if(source[j]==target[i]){
                    count++
                    anchor = j+1
                    break
                }
            }
        }

        return count
    }

    public static void main(String[] args) {
        ArrayUtils utils = new ArrayUtils()
        Integer[][] test = new Integer[2][3]

        println(utils.arrayToString(test))
    }
}
