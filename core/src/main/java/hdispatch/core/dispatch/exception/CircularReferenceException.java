package hdispatch.core.dispatch.exception;

/**
 * Created by hasee on 2016/10/14.
 */
public class CircularReferenceException extends RuntimeException {
    public CircularReferenceException() {
    }

    public CircularReferenceException(String message) {
        super(message);
    }

    public CircularReferenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CircularReferenceException(Throwable cause) {
        super(cause);
    }
}
