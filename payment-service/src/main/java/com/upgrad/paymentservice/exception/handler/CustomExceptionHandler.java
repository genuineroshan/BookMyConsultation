package com.upgrad.paymentservice.exception.handler;
import com.upgrad.paymentservice.dto.ErrorResponse;
import com.upgrad.paymentservice.enumeration.ErrorCode;
import com.upgrad.paymentservice.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(RecordNotFoundException ex){
        var errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ErrorCode.ERR_RESOURCE_NOT_FOUND.name());
        errorResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }
}
