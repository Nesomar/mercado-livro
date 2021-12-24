package br.com.academy.mercadolivro.controller

import br.com.academy.mercadolivro.controller.request.CustomerRequest
import br.com.academy.mercadolivro.extension.toCustomer
import br.com.academy.mercadolivro.model.Customer
import br.com.academy.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customers")
class CustomerController(val customerService: CustomerService) {

    @GetMapping
    fun findByName(@RequestParam name: String?): List<Customer> {
        return customerService.findByName(name)
    }

    @GetMapping("/{id}")
    fun findCustomerById(@PathVariable id: Int): Customer {
        return customerService.findCustomerById(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateCustomer(@PathVariable id: Int, @RequestBody customerRequest: CustomerRequest) {
        return customerService.updateCustomer(customerRequest.toCustomer(id))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable id: Int) {
        customerService.deleteCustomer(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody customerRequest: CustomerRequest): Int? {
        return customerService.createCustomer(customerRequest.toCustomer())
    }
}