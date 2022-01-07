package br.com.academy.mercadolivro.helper

import br.com.academy.mercadolivro.enums.BookStatus
import br.com.academy.mercadolivro.enums.CustomerStatus
import br.com.academy.mercadolivro.enums.UserRoles
import br.com.academy.mercadolivro.model.Book
import br.com.academy.mercadolivro.model.Customer
import br.com.academy.mercadolivro.model.Purchase
import java.math.BigDecimal
import java.util.*

fun buildCustomer(
    id: Int? = (Math.random() * 10).toInt(),
    name: String = "customer name",
    email: String = "${UUID.randomUUID()}@email.com",
    password: String = "password"
) = Customer(
    id = id,
    name = name,
    email = email,
    status = CustomerStatus.ATIVO,
    password = password,
    roles = setOf(UserRoles.CUSTOMER)
)

fun buildBook(
    id: Int = (Math.random() * 10).toInt(),
    name: String = "Book Name",
    price: BigDecimal = BigDecimal.TEN,
    customer: Customer? = buildCustomer()
) = Book(
    id = id,
    name = name,
    price = price,
    customer = customer,
    status = BookStatus.ATIVO
)

fun buildPurchase(
    id: Int? = (Math.random() * 10).toInt(),
    customer: Customer = buildCustomer(),
    books: MutableList<Book> = mutableListOf(buildBook(), buildBook()),
    nfe: String? = UUID.randomUUID().toString(),
    price: BigDecimal = BigDecimal.TEN,
) = Purchase(
    id = id,
    customer = customer,
    books = books,
    nfe = nfe,
    price = price,
)
