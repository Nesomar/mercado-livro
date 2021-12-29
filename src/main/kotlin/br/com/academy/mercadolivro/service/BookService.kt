package br.com.academy.mercadolivro.service

import br.com.academy.mercadolivro.enums.BookStatus
import br.com.academy.mercadolivro.exception.enums.CommonErrorCode
import br.com.academy.mercadolivro.exception.NotFoundException
import br.com.academy.mercadolivro.model.Book
import br.com.academy.mercadolivro.model.Customer
import br.com.academy.mercadolivro.repository.BookRepository
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(val bookRepository: BookRepository, @Lazy val customerService: CustomerService) {

    fun createBook(books: Book, customerId: Int): Int {
        val customer = customerService.findCustomerById(customerId)
        books.customer = customer
        return bookRepository.save(books).id!!
    }

    fun findAll(pageable: Pageable): Page<Book> {
        return bookRepository.findAll(pageable)
    }

    fun findByStatus(pageable: Pageable): Page<Book> {
        return bookRepository.findByStatus(BookStatus.ATIVO, pageable)
    }

    fun findById(id: Int): Book {
        return bookRepository.findById(id)
            .orElseThrow { NotFoundException(CommonErrorCode.ML101.message.format(id), CommonErrorCode.ML101.code) }
    }

    fun deleteById(id: Int) {
        val books = findById(id)
        books.status = BookStatus.CANCELADO
        bookRepository.save(books)
    }

    fun updateBook(book: Book) {
        bookRepository.save(book)
    }

    fun deleteByCustomer(customer: Customer) {
        val books = bookRepository.findByCustomer(customer)
        for (book in books) {
            book.status = BookStatus.DELETADO
        }
        bookRepository.saveAll(books)
    }

    fun findAllById(bookIds: Set<Int>) = bookRepository.findAllById(bookIds).toList()

    fun purchase(books: MutableList<Book>) {
        books.map { it.status = BookStatus.VENDIDO }
        bookRepository.saveAll(books)
    }
}