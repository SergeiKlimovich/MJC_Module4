package com.epam.esm.web.exceptionHandler;

import com.epam.esm.persistence.repository.exception.DuplicateNameException;
import com.epam.esm.service.exception.InvalidDataException;
import com.epam.esm.service.exception.NotExistEntityException;
import com.epam.esm.web.exception.RemoveCertificateException;
import com.epam.esm.web.exception.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String INVALID_DATA_MESSAGE = "Invalid data";
    private static final String ACCESS_DENY_MESSAGE = "Access denied";

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleNotExistIdEntityException(NotExistEntityException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 40401), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleDuplicateNameException(DuplicateNameException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 40004), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleInvalidDataException(InvalidDataException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 50), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleRemoveCertificateException(RemoveCertificateException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 40), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleUserAlreadyExistException(UserAlreadyExistException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 40), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleBadCredentialsException(BadCredentialsException exception) {
        return new ResponseEntity<>(new ErrorHandler(INVALID_DATA_MESSAGE, 60), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleAuthenticationException(AuthenticationException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 60), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleAccessDeniedException(AccessDeniedException exception) {
        return new ResponseEntity<>(new ErrorHandler(ACCESS_DENY_MESSAGE, 70), HttpStatus.FORBIDDEN);
    }

    // handling custom validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorHandler> customValidationErrorException(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getBindingResult().getFieldError().getDefaultMessage(), 40001), HttpStatus.BAD_REQUEST);
    }

}
