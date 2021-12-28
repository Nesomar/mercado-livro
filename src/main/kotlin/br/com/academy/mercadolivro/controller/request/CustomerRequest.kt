package br.com.academy.mercadolivro.controller.request

import br.com.academy.mercadolivro.validation.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class CustomerRequest(
    @field:NotBlank
    var nome: String,

    @field:Email
    @EmailAvailable
    var email: String
)
