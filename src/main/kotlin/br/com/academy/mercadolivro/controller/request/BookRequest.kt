package br.com.academy.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal

data class BookRequest(
    var name: String,
    var price: BigDecimal,
    @JsonAlias("customer_id")
    var customerId: Int
)
