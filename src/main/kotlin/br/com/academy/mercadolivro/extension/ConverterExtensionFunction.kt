package br.com.academy.mercadolivro.extension

import br.com.academy.mercadolivro.controller.request.BookRequest
import br.com.academy.mercadolivro.controller.request.CustomerRequest
import br.com.academy.mercadolivro.controller.request.PutBookRequest
import br.com.academy.mercadolivro.controller.response.BookResponse
import br.com.academy.mercadolivro.controller.response.CustomerResponse
import br.com.academy.mercadolivro.controller.response.PageResponse
import br.com.academy.mercadolivro.enums.BookStatus
import br.com.academy.mercadolivro.enums.CustomerStatus
import br.com.academy.mercadolivro.model.Book
import br.com.academy.mercadolivro.model.Customer
import org.springframework.data.domain.Page

fun CustomerRequest.toCustomer(): Customer =
    Customer(name = this.nome, email = this.email, status = CustomerStatus.ATIVO, password = this.password)

fun CustomerRequest.toCustomer(previousValue: Customer): Customer = Customer(
    id = previousValue.id, this.nome, this.email, status = previousValue.status, password = previousValue.password
)

fun BookRequest.toBook(): Book = Book(
    id = null,
    name = this.name,
    price = this.price,
    status = BookStatus.ATIVO,
    customer = null
)

fun PutBookRequest.toBook(previousValue: Book) = Book(
    id = previousValue.id,
    name = this.name ?: previousValue.name,
    price = this.price ?: previousValue.price,
    status = previousValue.status,
    customer = previousValue.customer
)

fun Customer.toCustomerResponse() = CustomerResponse(
    id = this.id,
    name = this.name,
    email = this.email,
    status = this.status
)

fun Book.toBookResponse() = BookResponse(
    id = this.id,
    name = this.name,
    price = this.price,
    customerId = this.customer?.id,
    status = this.status
)

fun <T> Page<T>.toPageResponse(): PageResponse<T> {
    return PageResponse(
        this.content,
        this.number,
        this.totalElements,
        this.totalPages)
}