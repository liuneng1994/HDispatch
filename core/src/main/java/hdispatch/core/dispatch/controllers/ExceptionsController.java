package hdispatch.core.dispatch.controllers;

import com.hand.hap.system.dto.ResponseData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liuneng on 2016/11/3.
 */
@ControllerAdvice
public class ExceptionsController {
    @ExceptionHandler(RuntimeException.class)
    public ResponseData handleRuntimeException(HttpServletRequest request, RuntimeException e, HttpServletResponse response) throws IOException {
        response.sendError(500,e.getMessage());
        return null;
    }
}
