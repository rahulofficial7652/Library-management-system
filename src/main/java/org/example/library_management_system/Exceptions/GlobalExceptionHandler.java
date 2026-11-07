package org.example.library_management_system.Exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.library_management_system.Dtos.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private ResponseEntity<ErrorResponseDTO> buildResponse(HttpStatus status, String message, HttpServletRequest request) {
        String traceId = UUID.randomUUID().toString();
        if (status.is5xxServerError()) {
            log.error("Internal Server Error [TraceId: {}] on path: {} -message: {}", traceId, request.getRequestURI(), message
            );
        } else {
            log.warn("Client Error [TraceId: {}] on path: {} -status: {} -message: {}", traceId, request.getRequestURL(), status.value(), message);
        }
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .traceId(traceId)
                .timeStamp(LocalDateTime.now())
                .statusCode(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(errorResponseDTO, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(ResourceNotFoundException resourceNotFoundException, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, resourceNotFoundException.getMessage(), request);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorized(UnauthorizedException unauthorizedException, HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, unauthorizedException.getMessage(), request);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceConflict(ResourceConflictException resourceConflictException, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, resourceConflictException.getMessage(), request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalAccessException illegalAccessException, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, illegalAccessException.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String defaultMessage = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .orElse("Validation failed");

        return buildResponse(HttpStatus.BAD_REQUEST, defaultMessage, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception exception, HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request);
    }
}
