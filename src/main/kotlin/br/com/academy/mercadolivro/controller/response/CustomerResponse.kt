package br.com.academy.mercadolivro.controller.response

import br.com.academy.mercadolivro.enums.CustomerStatus

data class CustomerResponse(
    val id: Int? = null,
    val name: String,
    val email: String,
    val status: CustomerStatus
) {

}
