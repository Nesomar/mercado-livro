package br.com.academy.mercadolivro.repository

import br.com.academy.mercadolivro.enums.BookStatus
import br.com.academy.mercadolivro.model.Book
import br.com.academy.mercadolivro.model.Customer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: CrudRepository<Book, Int>, JpaRepository<Book, Int> {

     fun findByStatus(status: BookStatus, pageable: Pageable): Page<Book>
     fun findByCustomer(customer: Customer): List<Book>
}