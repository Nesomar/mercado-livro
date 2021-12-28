package br.com.academy.mercadolivro.exception.model

data class ErrorResponse(
    var code: Int,
    var message: String,
    var internalCode: String,
    var errors: List<FieldErrorResponse>? = null
)
