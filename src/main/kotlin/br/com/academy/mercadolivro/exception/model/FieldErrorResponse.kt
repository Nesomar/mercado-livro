package br.com.academy.mercadolivro.exception.model

data class FieldErrorResponse(
    var message: String,
    var field: String
)
