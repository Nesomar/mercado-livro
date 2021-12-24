package br.com.academy.mercadolivro.extension

import br.com.academy.mercadolivro.controller.request.CustomerRequest
import br.com.academy.mercadolivro.model.Customer

fun CustomerRequest.toCustomer(): Customer = Customer(name = this.nome, email = this.email)

fun CustomerRequest.toCustomer(id: Int): Customer = Customer(id, this.nome, this.email)