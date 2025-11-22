package ru.practicum.main.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return buildApiError(
            HttpStatus.BAD_REQUEST,
            "Incorrectly made request.",
            e.getMessage()
        );
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolation(ConstraintViolationException e) {
        return buildApiError(
            HttpStatus.BAD_REQUEST,
            "Incorrectly made request.",
            e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        return buildApiError(
            HttpStatus.BAD_REQUEST,
            "Incorrectly made request.",
            e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        return buildApiError(
            HttpStatus.BAD_REQUEST,
            "Incorrectly made request.",
            e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(NotFoundException e) {
        return buildApiError(
            HttpStatus.NOT_FOUND,
            "The required object was not found.",
            e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbidden(ForbiddenException e) {
        return buildApiError(
            HttpStatus.FORBIDDEN,
            "For the requested operation the conditions are not met.",
            e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleAlreadyExists(AlreadyExists e) {
        return buildApiError(
            HttpStatus.CONFLICT,
            "Integrity constraint has been violated.",
            e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleNotAvailable(NotAvailable e) {
        return buildApiError(
            HttpStatus.CONFLICT,
            "For the requested operation the conditions are not met.",
            e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflict(ConflictException e) {
        return buildApiError(
            HttpStatus.CONFLICT,
            "For the requested operation the conditions are not met.",
            e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowable(Throwable e) {
        return buildApiError(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Error occurred.",
            e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(BadRequestException e) {
        return buildApiError(
            HttpStatus.BAD_REQUEST,
            "Incorrectly made request.",
            e.getMessage()
        );
    }

    private ApiError buildApiError(HttpStatus httpStatus, String reason, String message) {
        return ApiError.builder()
            .status(httpStatus.name())
            .reason(reason)
            .message(message)
            .timestamp(LocalDateTime.now())
            .errors(List.of())
            .build();
    }
}
