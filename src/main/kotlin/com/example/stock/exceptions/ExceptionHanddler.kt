package com.example.stock.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionHanddler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValid(exc: MethodArgumentNotValidException): ResponseEntity<ApiError> {
        val result = exc.bindingResult
        val errors = result.fieldErrors.last()
        val message = errors.defaultMessage ?: ""
        val field = errors.field
        val detail = "$field $message "

        return ResponseEntity(
            ApiError(errorType = "VALIDATION", errorCode = "INVALID_PAYLOAD", detail = detail),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler
    fun handlerApiException(exc: ApiException): ResponseEntity<ApiError> {
        return ResponseEntity(
            ApiError(errorCode = exc.errorCode, errorType = exc.errorType, detail = exc.detail ?: ""),
            HttpStatus.BAD_REQUEST
        )
    }


}