package hdispatch.core.dispatch.dto;

public class ExecutionJobsWithBLOBs extends ExecutionJobs {
    private byte[] inputParams;

    private byte[] outputParams;

    private byte[] attachments;

    public byte[] getInput_params() {
        return inputParams;
    }

    public void setInput_params(byte[] input_params) {
        this.inputParams = input_params;
    }

    public byte[] getOutput_params() {
        return outputParams;
    }

    public void setOutput_params(byte[] output_params) {
        this.outputParams = output_params;
    }

    public byte[] getAttachments() {
        return attachments;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }
}