package br.com.academy.mercadolivro.service

import br.com.academy.mercadolivro.enums.CustomerStatus
import br.com.academy.mercadolivro.exception.enums.CommonErrorCode
import br.com.academy.mercadolivro.exception.NotFoundException
import br.com.academy.mercadolivro.model.Customer
import br.com.academy.mercadolivro.repository.CustomerRepository
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

@Service
class CustomerService(val customerRepository: CustomerRepository, @Lazy val bookService: BookService) {

    fun findByName(name: String?): List<Customer> {
        name?.let {
            return customerRepository.findByNameContaining(name)
        }
        return customerRepository.findAll().toList()
    }

    fun findCustomerById(id: Int): Customer {
        return customerRepository.findById(id)
            .orElseThrow { NotFoundException(CommonErrorCode.ML201.message.format(id), CommonErrorCode.ML201.code) }

    }

    fun updateCustomer(customer: Customer) {
        if (customerRepository.existsById(customer.id!!)) {
            customerRepository.save(customer)
        }
    }

    fun deleteCustomer(id: Int) {
        val customer = findCustomerById(id)
        bookService.deleteByCustomer(customer)

        customer.status = CustomerStatus.INATIVO

        customerRepository.save(customer)
    }

    fun createCustomer(customer: Customer): Int? {
        return customerRepository.save(customer).id
    }

    fun emailAvailable(value: String?): Boolean {
        return !customerRepository.existsByEmail(value)
    }
}