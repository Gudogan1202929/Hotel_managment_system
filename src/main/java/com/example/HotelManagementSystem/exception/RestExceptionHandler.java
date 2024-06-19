package com.example.HotelManagementSystem.exception;


import com.example.HotelManagementSystem.dto.response.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Handle NoHandlerFoundException.
     *
     * @param ex        NoHandlerFoundException
     * @param headers   HttpHeaders
     * @param status    HttpStatus
     * @param request   WebRequest
     * @return the ResponseEntity object with the APIResponse object in the body
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("No handler found for the request: {}", ex.getMessage());
        APIResponse<Object> response = APIResponse.badRequest(null, String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handle NoResourceFoundException when a resource is not found.
     * @param ex NoResourceFoundException
     * @param request WebRequest
     * @param status HttpStatusCode
     * @param headers HttpHeaders
     * @return the ResponseEntity object with the APIResponse object in the body
     */
    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Resource not found: {}", ex.getMessage());
        APIResponse<Object> response = APIResponse.notFound(null, ex.getMessage());
        return ResponseEntity.status(status).body(response);
    }



    /**
     * Handle HttpRequestMethodNotSupportedException. Happens when you send a request with an unsupported HTTP method.
     *
     * @param ex        HttpRequestMethodNotSupportedException
     * @param headers   HttpHeaders
     * @param status    HttpStatus
     * @param request   WebRequest
     * @return the ResponseEntity object with the APIResponse object in the body
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Method not allowed: {}", ex.getMessage());
        APIResponse<Object> response = APIResponse.methodNotAllowed(null, ex.getMessage());
        return ResponseEntity.status(status).body(response);
    }


    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when a client POSTs, PUTs, or PATCHes
     * content of a type not supported by request handler.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ResponseEntity object with the APIResponse object in the body
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Media type not supported: {}", ex.getMessage());

        StringBuilder message = new StringBuilder();
        message.append("Media type not supported. Supported media types are: ");
        ex.getSupportedMediaTypes().forEach(mediaType -> message.append(mediaType).append(", "));
        APIResponse<Object> response = APIResponse.unsupportedMediaType(null, message.toString());
        return ResponseEntity.status(status).body(response);
    }




    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ResponseEntity object with the APIResponse object in the body
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Missing request parameter: {}", ex.getMessage());
        APIResponse<Object> response = APIResponse.badRequest(null, "The request parameter '" + ex.getParameterName() + "' is missing.");
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex        MethodArgumentNotValidException
     * @param headers   HttpHeaders
     * @param status    HttpStatus
     * @param request   WebRequest
     * @return the ResponseEntity object with the APIResponse object in the body
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Method argument not valid: {}", ex.getMessage());
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .reduce("", (acc, error) -> acc + error + ", ");
        APIResponse<Object> response = APIResponse.badRequest(null, message);
        return ResponseEntity.status(status).body(response);
    }


    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param ex        HttpMessageNotReadableException
     * @param headers   HttpHeaders
     * @param status    HttpStatus
     * @param request WebRequest
     * @return the ResponseEntity object with the APIResponse object in the body
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        APIResponse<Object> response = APIResponse.badRequest(null, "The request body is not readable.");
        return ResponseEntity.status(status).body(response);
    }



    /**
     * Handle all other internal exceptions. This is a fallback for all other internal exceptions that don't have a specific handler.
     *
     * @param ex        Exception
     * @param body      Object
     * @param headers   HttpHeaders
     * @param status    HttpStatus
     * @param request   WebRequest
     * @return the ResponseEntity object with the APIResponse object in the body
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Internal server error: {}", ex.getMessage());
        APIResponse<Object> response = APIResponse.internalServerError(null, "An internal server error occurred.");
        return ResponseEntity.status(status).body(response);
    }


    /**
     * Handle MethodArgumentTypeMismatchException. Happens when a method argument is not the expected type.
     *
     * @param ex        MethodArgumentTypeMismatchException
     * @param request   WebRequest
     * @return the ResponseEntity object with the APIResponse object in the body
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        log.error("Method argument type mismatch: {}", ex.getMessage());
        APIResponse<Object> response = APIResponse.badRequest(null, String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }


    /**
     * Handle all other exceptions. This is a fallback for all other exceptions that don't have a specific handler.
     *
     * @param ex    Exception
     * @return the ResponseEntity object with the APIResponse object in the body
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
        log.error("Internal server error: {}", ex.getMessage());
        APIResponse<Object> response = APIResponse.internalServerError(null, "An internal server error occurred.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }




    //----------------------------------- Custom Exceptions -----------------------------------


    /**
     * Handle ResourceNotFoundException. Triggered when a resource is not found.
     *
     * @param ex ResourceNotFoundException
     * @return the ResponseEntity object with the APIResponse object in the body
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        log.error("the needed Resource not found: {}", ex.getMessage());
        APIResponse<Object> response = APIResponse.notFound(null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    /**
     * Handle BadRequestException. Triggered when a bad request is made.
     *
     * @param ex BadRequestException
     * @return the ResponseEntity object with the APIResponse object in the body
     */
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest(BadRequestException ex) {
        log.error("Bad request: {}", ex.getMessage());
        APIResponse<Object> response = APIResponse.badRequest(null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }







}