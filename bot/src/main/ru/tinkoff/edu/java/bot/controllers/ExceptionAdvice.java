package bot.controllers;

import bot.DTOs.responses.ApiErrorResponse;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleConflict(Exception ex) {
        return ApiErrorResponse.builder()
                .code("400")
                .description("Invalid request parameters")
                .exceptionMessage(ex.getMessage())
                .exceptionName("Bad Request")
                .stacktrace(Arrays.stream(ex.getStackTrace()).map(Object::toString).collect(Collectors.toList()))
                .build();
    }


}
