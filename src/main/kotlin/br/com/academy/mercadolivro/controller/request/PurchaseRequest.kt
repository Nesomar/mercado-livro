package br.com.academy.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class PurchaseRequest(

    @field:NotNull
    @field:Positive
    @JsonAlias("customers_id")
    val customerId: Int,

    @field:NotNull
    @JsonAlias("books_ids")
    val bookIds: Set<Int>
)
