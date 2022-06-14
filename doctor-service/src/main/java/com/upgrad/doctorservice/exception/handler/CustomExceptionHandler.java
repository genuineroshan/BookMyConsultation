package com.upgrad.doctorservice.exception.handler;

import com.upgrad.doctorservice.dto.ErrorResponse;
import com.upgrad.doctorservice.enumeration.ErrorCode;
import com.upgrad.doctorservice.exception.RecordNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        List<String> fieldDetails = new ArrayList<>();
        ErrorResponse errorResponse = new ErrorResponse();
        List<FieldError> fieldErrors = ex.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            fieldDetails.add(fieldError.getField());
        }
        errorResponse.setErrorCode(ErrorCode.ERR_INVALID_INPUT.name());
        errorResponse.setErrorMessage("Invalid Input. Parameter name:");
        errorResponse.setErrorFields(fieldDetails);
        return  new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(RecordNotFoundException ex){
        var errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ErrorCode.ERR_RESOURCE_NOT_FOUND.name());
        errorResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }
}
