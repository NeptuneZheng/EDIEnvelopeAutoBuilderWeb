package nep.tools.process;

import java.util.List;
import java.util.Map;

public interface Pipeline {
    static final String PROCESS_ID = "PROCESS_ID";
    static final String BASIC_CONFIGURATION = "BASIC_CONFIGURATION";
    static final String RUNTIME_CONFIGRATION = "RUNTIME_CONFIGRATION";

    static final RuntimeException PIPELINE_RUNTIME_EXCEPTION = new RuntimeException("Please chece runtime params");

    void create(Map basicParams) throws Exception;
    void check() throws Exception;
    List process(List input) throws Exception;
    void destroy() throws Exception;

    String getProcessId();

}
