package ru.yaneg.graduation_of_topjava_springboot.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
@ResponseBody
public class AppExceptionsHandler {

    private final MessageSource messageSource;

    @Autowired
    public AppExceptionsHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {UserServiceException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage handleUserServiceException(UserServiceException ex, HttpServletRequest request, Locale locale) {
        String textOfErrorMessage = messageSource.getMessage(ex.getMessage(), null, locale);
        return new ErrorMessage(request.getRequestURI(), ErrorType.VALIDATION_ERROR, textOfErrorMessage);
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request, Locale locale) {

        List<FieldError> errors = (((MethodArgumentNotValidException) ex).getBindingResult()).getFieldErrors();

        List<String> details = new ArrayList<>();

        for (FieldError error : errors) {
            details.add(error.getField() + " - " + error.getDefaultMessage());
        }

        return new ErrorMessage(
                request.getRequestURI(),
                ErrorType.VALIDATION_ERROR,
                String.join(System.lineSeparator(),details)
        );
    }


    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

