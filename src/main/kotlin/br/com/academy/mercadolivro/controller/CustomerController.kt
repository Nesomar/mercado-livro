package br.com.academy.mercadolivro.controller

import br.com.academy.mercadolivro.controller.request.CustomerRequest
import br.com.academy.mercadolivro.controller.response.CustomerResponse
import br.com.academy.mercadolivro.extension.toCustomer
import br.com.academy.mercadolivro.extension.toCustomerResponse
import br.com.academy.mercadolivro.security.UserCanOnlyAccessTheirOwnResources
import br.com.academy.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("customers")
class CustomerController(private val customerService: CustomerService) {

    @GetMapping
    fun findByName(@RequestParam name: String?): List<CustomerResponse> {
        return customerService.findByName(name).map { it.toCustomerResponse() }
    }

    @GetMapping("/{id}")
    @UserCanOnlyAccessTheirOwnResources
    fun findCustomerById(@PathVariable id: Int): CustomerResponse {
        return customerService.findCustomerById(id).toCustomerResponse()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @UserCanOnlyAccessTheirOwnResources
    fun updateCustomer(@PathVariable id: Int, @RequestBody @Valid customerRequest: CustomerRequest) {
        var customer = customerService.findCustomerById(id)
        return customerService.updateCustomer(customerRequest.toCustomer(customer))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @UserCanOnlyAccessTheirOwnResources
    fun deleteCustomer(@PathVariable id: Int) {
        customerService.deleteCustomer(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody @Valid customerRequest: CustomerRequest): Int? {
        return customerService.createCustomer(customerRequest.toCustomer())
    }
}