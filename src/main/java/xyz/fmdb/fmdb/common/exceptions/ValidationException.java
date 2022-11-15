package xyz.fmdb.fmdb.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.net.BindException;

@ControllerAdvice
public class ValidationException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        System.out.print("Here1");

        //Returning password error message as a response.
        return new ResponseEntity<String>(String.join(",", e.getBindingResult().getFieldError().getDefaultMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        if(e.getMessage().toString().contains("multipartFile"))
            return new ResponseEntity<String>("Please select an image",HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<String>(e.getMessage().toString(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleMultipartException(MultipartException e) {
            return new ResponseEntity<String>("Please select an image",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException e) {
            return new ResponseEntity<String>("e.getMessage().toString()",HttpStatus.BAD_REQUEST);
    }


}
