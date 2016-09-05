package hdispatch.core.dispatch.azkaban.util;

/**
 * Created by 邓志龙 on 2016/8/31.
 */
public class ResultObj {
    private Integer code;
    private String message;
    private String status;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
