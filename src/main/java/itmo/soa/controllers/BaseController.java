package itmo.soa.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import itmo.soa.dto.ErrorDto;
import itmo.soa.exceptions.DragonsServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

public class BaseController {

    @ExceptionHandler({ InvalidFormatException.class, InstantiationException.class})
    public ResponseEntity<ErrorDto> handleBaseExceptions() {
        return new ResponseEntity<>(new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Wrong input"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NullPointerException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorDto> handleNullPointerException() {
        return new ResponseEntity<>(new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Bad Request"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<ErrorDto> handleIOException() {
        return new ResponseEntity<>(new ErrorDto(HttpStatus.SERVICE_UNAVAILABLE.value(), "Bad Request - Dragons service is offline"), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler({DragonsServiceException.class})
    public ResponseEntity<ErrorDto> requestToFirstServiceFailed(DragonsServiceException e) {
        return new ResponseEntity<>(new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Bad Request - from dragons service:  " + e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
