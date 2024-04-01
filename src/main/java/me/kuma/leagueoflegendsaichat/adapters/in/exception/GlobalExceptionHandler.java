package me.kuma.leagueoflegendsaichat.adapters.in.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleDomainException(Exception unexpectedError){
        String message = "Ocorreu um erro inesperado.";
        logger.error(message, unexpectedError);
    return ResponseEntity
            .internalServerError()
            .body(new ApiError(message));
    }

    public record ApiError(String message){}
}
