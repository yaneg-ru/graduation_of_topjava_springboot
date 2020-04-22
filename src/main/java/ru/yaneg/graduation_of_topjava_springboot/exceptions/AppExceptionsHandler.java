package ru.yaneg.graduation_of_topjava_springboot.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.ErrorMessage;

import java.util.Date;
import java.util.Locale;

@ControllerAdvice
public class AppExceptionsHandler {

    private final MessageSource messageSource;

    @Autowired
    public AppExceptionsHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {UserServiceException.class})
    public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request, Locale locale)
    {
        String textOfErrorMessage = messageSource.getMessage(ex.getMessage(), null, locale);
        ErrorMessage errorMessage = new ErrorMessage(new Date(), textOfErrorMessage);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request)
    {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

