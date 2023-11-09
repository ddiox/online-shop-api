package com.domain.onlineshoppingapi.controllers.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.domain.onlineshoppingapi.dtos.ResponseData;
import com.domain.onlineshoppingapi.exception.OrderNotFoundException;
import com.domain.onlineshoppingapi.exception.PaymentNotFoundException;
import com.domain.onlineshoppingapi.exception.ProductNotFoundException;
import com.domain.onlineshoppingapi.exception.ShippingNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception ex) {
        ProblemDetail errorDetail = null;
        if(ex instanceof BadCredentialsException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Authentication failed: bad credentials");
        }
        else if(ex instanceof AccessDeniedException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Not Authorized");
        }
        else if(ex instanceof SignatureException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Invalid Token");
        }
        else if(ex instanceof ExpiredJwtException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Token Expired");
        }
        else{
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), ex.getMessage());
        }
        return errorDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseData<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ResponseData<Object> responseData = new ResponseData<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            responseData.getMessage().add(error.getDefaultMessage());
        });
        responseData.setStatus(false);
        responseData.setPayload(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseData<Object>> handleProductNotFoundException(ProductNotFoundException ex) {
        ResponseData<Object> responseData = new ResponseData<>();
        responseData.setStatus(false);
        responseData.getMessage().add(ex.getMessage());
        responseData.setPayload(null);
        return ResponseEntity.badRequest().body(responseData);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseData<Object>> handleOrderNotFoundException(OrderNotFoundException ex) {
        ResponseData<Object> responseData = new ResponseData<>();
        responseData.setStatus(false);
        responseData.getMessage().add(ex.getMessage());
        responseData.setPayload(null);
        return ResponseEntity.badRequest().body(responseData);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseData<Object>> handlePaymentNotFoundException(PaymentNotFoundException ex) {
        ResponseData<Object> responseData = new ResponseData<>();
        responseData.setStatus(false);
        responseData.getMessage().add(ex.getMessage());
        responseData.setPayload(null);
        return ResponseEntity.badRequest().body(responseData);
    }

    @ExceptionHandler(ShippingNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseData<Object>> handleShippingNotFoundException(ShippingNotFoundException ex) {
        ResponseData<Object> responseData = new ResponseData<>();
        responseData.setStatus(false);
        responseData.getMessage().add(ex.getMessage());
        responseData.setPayload(null);
        return ResponseEntity.badRequest().body(responseData);
    }
}
