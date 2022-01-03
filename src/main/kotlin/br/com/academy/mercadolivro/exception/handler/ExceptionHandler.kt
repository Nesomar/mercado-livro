package br.com.academy.mercadolivro.exception.handler

import br.com.academy.mercadolivro.exception.BadRequestException
import br.com.academy.mercadolivro.exception.NotFoundException
import br.com.academy.mercadolivro.exception.enums.CommonErrorCode
import br.com.academy.mercadolivro.exception.model.ErrorResponse
import br.com.academy.mercadolivro.exception.model.FieldErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun notFoundExceptionHandler(exception: NotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.message, exception.errorCode),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(BadRequestException::class)
    fun badRequestExceptionHandler(exception: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.message, exception.errorCode))
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun accessDeniedExceptionHandler(
        exception: AccessDeniedException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            HttpStatus.FORBIDDEN.value(),
            CommonErrorCode.ML000.message,
            CommonErrorCode.ML000.code,
            null
        )

        return ResponseEntity(error, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(
        exception: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(
                ErrorResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    CommonErrorCode.ML005.message,
                    CommonErrorCode.ML005.code,
                    exception.bindingResult.fieldErrors.map {
                        FieldErrorResponse(
                            it.defaultMessage ?: "invalid",
                            it.field
                        )
                    })
            )
    }
}