package br.com.academy.mercadolivro.exception.enums

enum class CommonErrorCode(val code: String, val message: String) {
    ML001("ML_001", "Invalid payload."),
    ML101("ML_101", "Book [%s] not exists"),
    ML102("ML_102", "Cannot update with status [%s]"),
    ML201("ML_201", "Customer [%s] not exists")
}