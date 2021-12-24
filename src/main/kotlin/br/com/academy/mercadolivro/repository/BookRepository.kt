package br.com.academy.mercadolivro.repository

import br.com.academy.mercadolivro.enums.BookStatus
import br.com.academy.mercadolivro.model.Book
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: CrudRepository<Book, Int> {

     fun findByStatus(status: BookStatus): List<Book>
}