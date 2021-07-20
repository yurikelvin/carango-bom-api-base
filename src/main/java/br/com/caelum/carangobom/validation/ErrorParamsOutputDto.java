package br.com.caelum.carangobom.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.FieldError;

@AllArgsConstructor
@Data
public class ErrorParamsOutputDto {

    private String param;
    private String message;

    public ErrorParamsOutputDto(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }

}
