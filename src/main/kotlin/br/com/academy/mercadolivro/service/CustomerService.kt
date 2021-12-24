package br.com.academy.mercadolivro.service

import br.com.academy.mercadolivro.model.Customer
import br.com.academy.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(val customerRepository: CustomerRepository) {

    val customers = mutableListOf<Customer>();

    fun findByName(name: String?): List<Customer> {
        name?.let {
            return customerRepository.findByNameContaining(name)
        }
        return customerRepository.findAll().toList()
    }

    fun findCustomerById(id: Int): Customer {
        return customerRepository.findById(id).orElseThrow()

    }

    fun updateCustomer(customer: Customer) {
        if (customerRepository.existsById(customer.id!!)) {
            customerRepository.save(customer)
        }
    }

    fun deleteCustomer(id: Int) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id)
        }
    }

    fun createCustomer(customer: Customer): Int? {
        return customerRepository.save(customer).id
    }
}