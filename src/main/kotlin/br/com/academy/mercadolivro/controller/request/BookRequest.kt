package br.com.academy.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class BookRequest(
    @field:NotBlank
    var name: String,
    @field:NotNull
    var price: BigDecimal,
    @JsonAlias("customers_id")
    @field:NotNull
    var customerId: Int
)
