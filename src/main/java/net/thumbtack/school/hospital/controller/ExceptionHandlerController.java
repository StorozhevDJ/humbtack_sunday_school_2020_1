package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hospital.dto.response.ExceptionDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@ControllerAdvice
@EnableWebMvc
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServerException.class)
    @ResponseBody
    public ExceptionDtoResponse handleServerException(ServerException ex) {
        ErrorDtoResponse err = new ErrorDtoResponse(ex.getError().name(), ex.getError().getField(), ex.getError().getMessage());
        return new ExceptionDtoResponse(Collections.singletonList(err));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ExceptionDtoResponse handleFieldValidation(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
        List<ErrorDtoResponse> errorDtoList = new ArrayList<>();
        for (ConstraintViolation violation : errors) {
            String message = violation.getMessage();
            String field = violation.getPropertyPath().toString();
            field = field.substring(field.indexOf(".") + 1);
            errorDtoList.add(new ErrorDtoResponse("INVALID_REQUEST_PARAM", field, message));
        }
        return new ExceptionDtoResponse(errorDtoList);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ExceptionDtoResponse handleFieldValidation(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        List<FieldError> fields = ex.getBindingResult().getFieldErrors();
        List<ErrorDtoResponse> errorDtoList = new ArrayList<>();
        for (int i = 0; i < errors.size(); i++) {
            String message = errors.get(i).getDefaultMessage();
            String field = fields.get(i).getField();
            errorDtoList.add(new ErrorDtoResponse("INVALID_FIELD", field, message));
        }
        return new ExceptionDtoResponse(errorDtoList);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ExceptionDtoResponse handleFieldValidation(MethodArgumentTypeMismatchException ex) {
        ErrorDtoResponse errorDto = new ErrorDtoResponse("INVALID_URL", "URL", "Invalid URL variable!");
        return new ExceptionDtoResponse(Collections.singletonList(errorDto));
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ExceptionDtoResponse handleNotFoundException() {
        ErrorDtoResponse errorDto = new ErrorDtoResponse("NOT_FOUND", "URL", "Invalid url!");
        return new ExceptionDtoResponse(Collections.singletonList(errorDto));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ExceptionDtoResponse handleBadJsonException(HttpMessageNotReadableException ex) {
        String message = "No JSON found!";
        Throwable exception = ex.getCause();
        if (exception != null) {
            message = exception.getMessage();
        }
        ErrorDtoResponse errorDto = new ErrorDtoResponse("INVALID_JSON", "Request Body", message);
        return new ExceptionDtoResponse(Collections.singletonList(errorDto));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestCookieException.class)
    @ResponseBody
    public ExceptionDtoResponse handleMissingCookieException() {
        ErrorDtoResponse errorDto = new ErrorDtoResponse("MISSING_COOKIE", "cookie", "Cookie required for this operation!");
        return new ExceptionDtoResponse(Collections.singletonList(errorDto));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ExceptionDtoResponse handleMethodNotSupportedException() {
        ErrorDtoResponse errorDto = new ErrorDtoResponse("INVALID_METHOD", "", "HTTP method is not supported for this URL!");
        return new ExceptionDtoResponse(Collections.singletonList(errorDto));
    }

}
