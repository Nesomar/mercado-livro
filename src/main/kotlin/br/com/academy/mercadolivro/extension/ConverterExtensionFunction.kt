package br.com.academy.mercadolivro.extension

import br.com.academy.mercadolivro.controller.request.BookRequest
import br.com.academy.mercadolivro.controller.request.CustomerRequest
import br.com.academy.mercadolivro.controller.request.PutBookRequest
import br.com.academy.mercadolivro.enums.BookStatus
import br.com.academy.mercadolivro.enums.CustomerStatus
import br.com.academy.mercadolivro.model.Book
import br.com.academy.mercadolivro.model.Customer

fun CustomerRequest.toCustomer(): Customer =
    Customer(name = this.nome, email = this.email, status = CustomerStatus.ATIVO)

fun CustomerRequest.toCustomer(previousValue: Customer): Customer = Customer(
    id = previousValue.id, this.nome, this.email, status = previousValue.status
)

fun BookRequest.toBook(): Book = Book(
    name = this.name,
    price = this.price,
    status = BookStatus.ATIVO
)

fun PutBookRequest.toBook(previousValue: Book) = Book(
    id = previousValue.id,
    name = this.name ?: previousValue.name,
    price = this.price ?: previousValue.price,
    status = previousValue.status,
    customer = previousValue.customer
)