package scrapper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import scrapper.DTOs.responses.ApiErrorResponse;
import scrapper.Exceptions.ScrapperBadRequestException;
import scrapper.Exceptions.ScrapperNotFoundException;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ScrapperBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBadRequestException(Exception ex){
        return ApiErrorResponse.builder()
                .code("400")
                .description("Test description")
                .exceptionMessage(ex.getMessage())
                .exceptionName("Bad Request")
                .stacktrace(Arrays.stream(ex.getStackTrace()).map(Object::toString).collect(Collectors.toList()))
                .build();
    }
    @ExceptionHandler(ScrapperNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFoundException(Exception ex){
        return ApiErrorResponse.builder()
                .code("404")
                .description("Test description")
                .exceptionMessage(ex.getMessage())
                .exceptionName("Not found")
                .stacktrace(Arrays.stream(ex.getStackTrace()).map(Object::toString).collect(Collectors.toList()))
                .build();
    }
}
