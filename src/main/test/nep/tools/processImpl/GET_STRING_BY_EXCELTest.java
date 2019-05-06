package nep.tools.processImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GET_STRING_BY_EXCELTest {
    GET_STRING_BY_EXCEL task1 = null;
    PASER_FILE task2 = null;
    static String exp_path = null;
    static String act_path = null;

    static Map basicParams = new HashMap();

    @Before
    public void init() throws Exception {
        task1 = new GET_STRING_BY_EXCEL();
        task2 = new PASER_FILE();

        exp_path = "D:\\1_B2BEDI_Revamp\\BC\\OUT_D96B\\IKEA-COSU\\ExpectedComplete\\";
        act_path = "D:\\1_B2BEDI_Revamp\\BC\\OUT_D96B\\IKEA-COSU\\ActualComplete\\";

        basicParams.put("segment_seperater","\\'");
        basicParams.put("field_seperater","\\+");
        basicParams.put("sub_field_segment","\\:");
        basicParams.put("segment_escape_character","\\?'");

        task2.create(basicParams);
        task2.check();
    }
    @Test
    public void process() throws Exception {
        List list = new ArrayList();
        list.add(exp_path + "00000001_EDIIKEA20190218084124376003.EDI.UIF");
        list.add(act_path + "00000001_I_C97E4BF572389E44D0F4BA13716D8D6D");
//        task1.process(list);
        System.out.println(task2.process(task1.process(list)).get(0));
        System.out.println(null == null);
    }

}