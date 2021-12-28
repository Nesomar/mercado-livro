package br.com.academy.mercadolivro.controller.response

import br.com.academy.mercadolivro.enums.BookStatus
import br.com.academy.mercadolivro.model.Customer
import java.math.BigDecimal

data class BookResponse(
    val id: Int? = null,
    val name: String,
    val price: BigDecimal,
    val customerId: Int? = null,
    val status: BookStatus? = null
) {

}
