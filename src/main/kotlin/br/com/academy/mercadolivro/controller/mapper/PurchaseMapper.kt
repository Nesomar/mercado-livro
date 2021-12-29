package br.com.academy.mercadolivro.controller.mapper

import br.com.academy.mercadolivro.controller.request.PurchaseRequest
import br.com.academy.mercadolivro.model.Purchase
import br.com.academy.mercadolivro.service.BookService
import br.com.academy.mercadolivro.service.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(private val bookService: BookService, private val customerService: CustomerService) {

    fun toModel(request: PurchaseRequest): Purchase {
        val customer = customerService.findCustomerById(request.customerId)
        val books = bookService.findAllById(request.bookIds)

        return Purchase(customer = customer, books = books.toMutableList(), price = books.sumOf { it.price })
    }
}