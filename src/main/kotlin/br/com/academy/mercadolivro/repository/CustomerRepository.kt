package br.com.academy.mercadolivro.repository

import br.com.academy.mercadolivro.model.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : CrudRepository<Customer, Int> {

    fun findByNameContaining(name: String): List<Customer>

}