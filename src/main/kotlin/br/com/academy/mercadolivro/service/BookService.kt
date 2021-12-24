package br.com.academy.mercadolivro.service

import br.com.academy.mercadolivro.enums.BookStatus
import br.com.academy.mercadolivro.model.Book
import br.com.academy.mercadolivro.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(val bookRepository: BookRepository, val customerService: CustomerService) {

    fun createBook(books: Book, customerId: Int): Int {
        val customer = customerService.findCustomerById(customerId)
        books.customer = customer
        return bookRepository.save(books).id!!
    }

    fun findAll(): List<Book> {
        return bookRepository.findAll().toList()
    }

    fun findByStatus(): List<Book> {
        return bookRepository.findByStatus(BookStatus.ATIVO)
    }

    fun findById(id: Int): Book {
        return bookRepository.findById(id).orElseThrow()
    }

    fun deleteById(id: Int) {

        val books = findById(id)

        books.status = BookStatus.CANCELADO

        bookRepository.save(books)
    }

    fun updateBook(book: Book) {
        bookRepository.save(book)
    }
}