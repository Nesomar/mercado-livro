package br.com.academy.mercadolivro.exception.enums

enum class CommonErrorCode(val code: String, val message: String) {
    ML000("ML_000", "Unauthorized"),
    ML001("ML_001", "Token inválido"),
    ML002("ML_002", "Falha ao tentar autenticar"),
    ML005("ML_005", "Invalid payload."),
    ML101("ML_101", "Book [%s] not exists"),
    ML102("ML_102", "Cannot update with status [%s]"),
    ML201("ML_201", "Customer [%s] not exists")
}