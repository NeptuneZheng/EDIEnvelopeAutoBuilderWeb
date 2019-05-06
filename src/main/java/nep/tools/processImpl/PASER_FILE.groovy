package nep.tools.processImpl

import nep.tools.process.AbstractWorkPlan

class PASER_FILE extends AbstractWorkPlan {
    String segment_seperater
    String segment_escape_character
    Set ig_set = new LinkedHashSet()

    void create(Map basicParams) throws Exception {
        if(basicParams["segment_seperater"]){
            this.segment_seperater =  basicParams["segment_seperater"]
        }
        if(basicParams["segment_escape_character"]){
            this.segment_escape_character = basicParams["segment_escape_character"]
        }
    }

    void check() throws Exception {
        if(!this.segment_seperater){
            throw PIPELINE_RUNTIME_EXCEPTION
        }
    }

    List process(List input) throws Exception {
        List list = new ArrayList()
        if(input && input.size() > 1){
            list.add(((String)input[0]).replaceAll(this.segment_escape_character,"").split(this.segment_seperater))
            list.add(((String)input[1]).replaceAll(this.segment_escape_character,"").split(this.segment_seperater))
        }else{
            throw FILE_UNPAIRED_EXCEPTIOM
        }
        return list
    }

    void destroy() throws Exception {
    }

}
