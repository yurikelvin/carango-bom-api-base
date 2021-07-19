package br.com.caelum.carangobom.validation;

import java.util.List;

public class ErrorListOutputDto {

    private List<ErrorParamsOutputDto> errors;

    public ErrorListOutputDto(List<ErrorParamsOutputDto> errors) {
        this.errors = errors;
    }

    public int getQuantidadeDeErros() {
        return errors.size();
    }

    public List<ErrorParamsOutputDto> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorParamsOutputDto> errors) {
        this.errors = errors;
    }
}
