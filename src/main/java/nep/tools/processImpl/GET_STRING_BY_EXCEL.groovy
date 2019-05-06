package nep.tools.processImpl

import nep.tools.process.AbstractWorkPlan

class GET_STRING_BY_EXCEL extends AbstractWorkPlan {
    String exp_file_name
    String act_file_name

    @Override
    void create(Map basicParams) throws Exception {
    }

    @Override
    void check() throws Exception {
        if(!exp_file_name || !act_file_name){
            throw PIPELINE_RUNTIME_EXCEPTION
        }
    }

    @Override
    void destroy() throws Exception {
    }

    @Override
    String getProcessId() {
        return "Get Tasks By Excel"
    }

    @Override
    List process(List input) throws Exception {
        if(!input || input.size() < 1){
            throw FILE_UNPAIRED_EXCEPTIOM
        }
        this.exp_file_name = input[0]
        this.act_file_name = input[1]

        return [readFileByPath(this.exp_file_name),readFileByPath(this.act_file_name)]
    }

    private String readFileByPath(String fileName){
        StringBuilder sb = new StringBuilder()
        File file = new File(fileName)
        BufferedReader reader = new BufferedReader(new FileReader(file))
        String line = ''
        if(file && file.isFile()){
            while ((line = reader.readLine()) != null){
                sb.append(line)
            }
            return sb.toString()
        }else{
            throw FILE_UNFOUND_EXCEPTIOM
        }
    }
}
