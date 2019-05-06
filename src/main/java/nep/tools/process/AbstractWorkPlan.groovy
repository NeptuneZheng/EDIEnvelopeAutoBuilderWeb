package nep.tools.process

abstract class AbstractWorkPlan implements Pipeline {
    static final RuntimeException FILE_UNPAIRED_EXCEPTIOM = new RuntimeException("File is not paired before handling")
    static final RuntimeException FILE_UNFOUND_EXCEPTIOM = new RuntimeException("File is not found")
    
    @Override
    String getProcessId() {
        return "Process step - ${this.getClass().getName()}"
    }
}
