package br.com.academy.mercadolivro.service

import br.com.academy.mercadolivro.model.Customer
import org.springframework.stereotype.Service

@Service
class LibraryService(val customerService: CustomerService, val bookService: BookService) {

    fun findCustomerById(customerId: Int): Customer? {
        return customerService.findCustomerById(customerId)
    }

    fun deleteBookByCustomer(customer: Customer) {
        bookService.deleteByCustomer(customer)
    }
}