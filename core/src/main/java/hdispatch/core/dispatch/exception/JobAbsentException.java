package hdispatch.core.dispatch.exception;

/**
 * Created by liuneng on 2016/11/9.
 */
public class JobAbsentException extends RuntimeException{
    public JobAbsentException() {
    }

    public JobAbsentException(String message) {
        super(message);
    }
}
