package ru.yaneg.graduation_of_topjava_springboot.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
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

    private final MessageSourceAccessor messageSource;

    @Autowired
    public AppExceptionsHandler(MessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
    }


    @ExceptionHandler(value = {NotFoundEntityException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage handleNotFoundEntityException(NotFoundEntityException ex, HttpServletRequest request, Locale locale) {
        return new ErrorMessage(request.getRequestURI(),
                ErrorType.DATA_NOT_FOUND,
                getMessageViaMessageSource(ErrorType.DATA_NOT_FOUND.getErrorCode(),locale),
                getMessageViaMessageSource(ex.getMessage(),locale)
        );
    }


    @ExceptionHandler(value = {UserServiceException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage handleUserServiceException(UserServiceException ex, HttpServletRequest request, Locale locale) {
        return new ErrorMessage(request.getRequestURI(),
                ErrorType.VALIDATION_ERROR,
                getMessageViaMessageSource(ErrorType.VALIDATION_ERROR.getErrorCode(),locale),
                getMessageViaMessageSource(ex.getMessage(),locale)
        );
    }


    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY) // 422
    public ErrorMessage handleMethodArgumentNotValidException(Exception ex, HttpServletRequest request, Locale locale) {

        BindingResult result = ex instanceof BindException ?
                ((BindException) ex).getBindingResult()
                : ((MethodArgumentNotValidException) ex).getBindingResult();

        List<String> details = new ArrayList<>();

        for (FieldError error : result.getFieldErrors()) {
            details.add(error.getField() + " - " + getMessageViaMessageSource(error.getDefaultMessage(),locale));
        }

        ErrorMessage errorMessage = new ErrorMessage(
                request.getRequestURI(),
                ErrorType.VALIDATION_ERROR,
                getMessageViaMessageSource(ErrorType.VALIDATION_ERROR.getErrorCode(),locale),
                details.stream()
                        .map(msg -> messageSource.getMessage(msg, msg, locale))
                        .toArray(String[]::new)
        );

        return errorMessage;
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request, Locale locale) {

        String textMsg = ex.getMessage();

        if (ex.getMessage().contains("menu_items_unique_menu_name_idx")) {
            textMsg = "menuItem.fields.constrains.eateryID&date&nameMustBeUnique";
        }

        return new ErrorMessage(request.getRequestURI(),
                ErrorType.VALIDATION_ERROR,
                getMessageViaMessageSource(ErrorType.VALIDATION_ERROR.getErrorCode(),locale),
                getMessageViaMessageSource(textMsg,locale)
        );
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getMessageViaMessageSource(String msg, Locale locale) {
        return messageSource.getMessage(msg, msg, locale);
    }

}

