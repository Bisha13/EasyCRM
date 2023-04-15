package ru.bisha.easycrm.restcontroller.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.bisha.easycrm.dto.ApiExceptionDto;
import ru.bisha.easycrm.exception.OrderNotFoundException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class CustomGlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApiExceptionDto onException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ApiExceptionDto("ERROR", "Что-то пошло не так");
    }

    @ResponseBody
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiExceptionDto onException(OrderNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ApiExceptionDto("ERROR", exception.getMessage());
    }

}
