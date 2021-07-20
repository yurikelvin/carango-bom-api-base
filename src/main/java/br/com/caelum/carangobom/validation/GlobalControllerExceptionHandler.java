package br.com.caelum.carangobom.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorListOutputDto validate(MethodArgumentNotValidException exception) {
        List<ErrorParamsOutputDto> errorsList = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            ErrorParamsOutputDto outputDto = new ErrorParamsOutputDto(fieldError);
            errorsList.add(outputDto);
        });

        return new ErrorListOutputDto(errorsList);
    }
}
