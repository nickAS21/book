package com.lardi.phonebook.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Exception handler for
     * INTERNAL_SERVER_ERROR(500, "Internal Server Error")
     * @param ex
     * @param request
     * @return  Error format message  and HttpStatus value
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
//        (ex.getLocalizedMessage() != null && !ex.getLocalizedMessage().isEmpty())? details.add(ex.getLocalizedMessage() : details.add(ex.getLocalizedMessage();
        if (ex.getLocalizedMessage() != null && !ex.getLocalizedMessage().isEmpty() ){
            details.add(ex.getLocalizedMessage());
        }
        else {
            details.add(ex.toString());
        }

        ErrorResponse error = new ErrorResponse("Server Error", details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Exception handler for
     * NOT_FOUND(404, "Not Found")
     * @param ex
     * @param request
     * @return Error format message  and HttpStatus value
     */
    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Record Not Found", details);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for
     * Report after user contact validation before registration
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return List Details {error.getDefaultMessage()}  and HttpStatus value
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add("</br>" + error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Validation Failed", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
