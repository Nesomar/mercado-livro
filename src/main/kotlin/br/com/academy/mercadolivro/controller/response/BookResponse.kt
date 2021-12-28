package br.com.academy.mercadolivro.controller.response

import br.com.academy.mercadolivro.enums.BookStatus
import br.com.academy.mercadolivro.model.Customer
import java.math.BigDecimal

data class BookResponse(
    var id: Int? = null,
    var name: String,
    var price: BigDecimal,
    var customerId: Int? = null,
    var status: BookStatus? = null
)
