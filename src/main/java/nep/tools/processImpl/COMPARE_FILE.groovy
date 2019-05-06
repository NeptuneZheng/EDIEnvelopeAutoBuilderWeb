package nep.tools.processImpl

import nep.tools.process.AbstractWorkPlan
import service.tools.mode.Segment

class COMPARE_FILE extends AbstractWorkPlan{
    private Segment segment
    private String field_seperater

    public static RuntimeException COMPARE_FIELD_OUT_OF_IG_EXCEPTION = new RuntimeException("Can't find segment in compare IG, pls help to review and confirm.")

    void create(Map basicParams) throws Exception {
        if(basicParams["field_seperater"]){
            this.field_seperater = basicParams["field_seperater"]
        }
        if(basicParams["segment"]){
            this.segment = (Segment)basicParams["segment"]
        }
    }

    void check() throws Exception {
        if(!this.field_seperater || !this.segment){
            throw PIPELINE_RUNTIME_EXCEPTION
        }
    }

    List process(List input) throws Exception {

        return compareSegmentCore((List)input[0],(List)input[1])
    }

    void destroy() throws Exception {

    }

    private List compareSegmentCore(List<String> exps, List<String> acts){
        List<String> list = new ArrayList<>()
        //3 pointer, segment point is the main
        int exp_pointer = 0
        int act_pointer = 0

        def curr_seg_name = this.segment.getName()
        if(exps[exp_pointer].startsWith(curr_seg_name) || acts[act_pointer].startsWith(curr_seg_name)){
            list.addAll(compareFieldsCore(exps[exp_pointer],acts[act_pointer]))

        }
        return list
    }

    private List<String> compareFieldsCore(String exp, String act){
        List<String> list = new ArrayList<>()
        List<String> list_exp = new ArrayList<>()
        List<String> list_act = new ArrayList<>()

        if(exp != act){
            String[] exp_fields = exp.split(this.field_seperater)
            String[] act_fields = act.split(this.field_seperater)

            int loop_counter = Math.max(exp_fields.size(),act.size())
            for(int i = 0; i < loop_counter; i++){
                if(exp_fields.size() > i && act_fields.size() > i){
                    if(exp_fields[i] == act_fields[i]){
                        list_exp.add(null)
                        list_act.add(null)
                    }else{
                        list_exp.add(exp_fields[i])
                        list_act.add(act_fields[i])
                    }
                }else if(exp_fields.size() > i){
                    list_exp.add(exp_fields[i])
                    list_act.add(null)
                }else if(act_fields.size() > i){
                    list_exp.add(null)
                    list_act.add(act_fields[i])
                }else{
                    throw new Exception("compareFieldsCore error")
                }
            }
        }
        list.addAll(list_exp)
        list.addAll(list_act)
        return list
    }
}
