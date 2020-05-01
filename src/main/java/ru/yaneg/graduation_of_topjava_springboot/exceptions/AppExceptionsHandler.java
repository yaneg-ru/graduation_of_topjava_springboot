package ru.yaneg.graduation_of_topjava_springboot.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
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
import ru.yaneg.graduation_of_topjava_springboot.shared.Messages;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
@ResponseBody
public class AppExceptionsHandler {

    private final Messages messages;

    @Autowired
    public AppExceptionsHandler(Messages messages) {
        this.messages = messages;
    }

    @ExceptionHandler(value = {NotFoundEntityException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage handleNotFoundEntityException(NotFoundEntityException ex, HttpServletRequest request, Locale locale) {
        return new ErrorMessage(request.getRequestURI(),
                ErrorType.DATA_NOT_FOUND,
                messages.getMessage(ErrorType.DATA_NOT_FOUND.getErrorCode(),locale),
                messages.getMessage(ex.getMessage(),locale)
        );
    }


    @ExceptionHandler(value = {UniqueFieldException.class, VoteException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage handleUniqueFieldException(Exception ex, HttpServletRequest request, Locale locale) {
        return new ErrorMessage(request.getRequestURI(),
                ErrorType.VALIDATION_ERROR,
                messages.getMessage(ErrorType.VALIDATION_ERROR.getErrorCode(),locale),
                messages.getMessage(ex.getMessage(),locale)
        );
    }


    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage handleMethodArgumentNotValidException(Exception ex, HttpServletRequest request, Locale locale) {

        BindingResult result = ex instanceof BindException ?
                ((BindException) ex).getBindingResult()
                : ((MethodArgumentNotValidException) ex).getBindingResult();

        List<String> details = new ArrayList<>();

        for (FieldError error : result.getFieldErrors()) {
            details.add(error.getField() + " - " + messages.getMessage(error.getDefaultMessage(),locale));
        }

        ErrorMessage errorMessage = new ErrorMessage(
                request.getRequestURI(),
                ErrorType.VALIDATION_ERROR,
                messages.getMessage(ErrorType.VALIDATION_ERROR.getErrorCode(),locale),
                details.stream()
                        .map(msg -> messages.getMessage(msg, locale))
                        .toArray(String[]::new)
        );

        return errorMessage;
    }
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request, Locale locale) {

        String textMsg = "";

        if (ex.getMessage().contains("menu_items_unique_menu_name_idx")) {
            textMsg = "constraints.menuItem.eateryIDAndDateAndNameMustBeUnique";
        }

        textMsg = "constraints.dataBase.DataIntegrityViolationException";

        return new ErrorMessage(request.getRequestURI(),
                ErrorType.VALIDATION_ERROR,
                messages.getMessage(ErrorType.VALIDATION_ERROR.getErrorCode(),locale),
                messages.getMessage(textMsg,locale)
        );
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

