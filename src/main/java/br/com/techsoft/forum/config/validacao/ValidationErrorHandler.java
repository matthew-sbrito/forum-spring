package br.com.techsoft.forum.config.validacao;

import br.com.techsoft.forum.utils.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse<FormErrorDto> handle(MethodArgumentNotValidException exception) {
        List<FormErrorDto> dto = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach( error -> {
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            FormErrorDto errorDto = new FormErrorDto(error.getField(), message);
            dto.add(errorDto);
        });

        ErrorResponse<FormErrorDto> response = new ErrorResponse<FormErrorDto>(
                HttpStatus.BAD_REQUEST, "Field validation error!", dto
        );

        return response;
    }
}
