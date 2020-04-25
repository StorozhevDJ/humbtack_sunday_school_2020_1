package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hospital.dto.response.ExceptionDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;


@ControllerAdvice
@EnableWebMvc
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServerException.class)
    @ResponseBody
    public ExceptionDtoResponse handleServerException(ServerException ex) {
        ErrorDtoResponse err = new ErrorDtoResponse(ex.getError().name(), ex.getError().getField(), ex.getError().getMessage());
        return new ExceptionDtoResponse(Collections.singletonList(err));
    }
// TODO add other handler exception


}
