package hdispatch.core.dispatch.dto;

public class ExecutionJobsWithBLOBs extends ExecutionJobs {
    private byte[] input_params;

    private byte[] output_params;

    private byte[] attachments;

    public byte[] getInput_params() {
        return input_params;
    }

    public void setInput_params(byte[] input_params) {
        this.input_params = input_params;
    }

    public byte[] getOutput_params() {
        return output_params;
    }

    public void setOutput_params(byte[] output_params) {
        this.output_params = output_params;
    }

    public byte[] getAttachments() {
        return attachments;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }
}