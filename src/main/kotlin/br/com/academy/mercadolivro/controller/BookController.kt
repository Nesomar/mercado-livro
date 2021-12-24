package br.com.academy.mercadolivro.controller

import br.com.academy.mercadolivro.controller.request.BookRequest
import br.com.academy.mercadolivro.controller.request.PutBookRequest
import br.com.academy.mercadolivro.extension.toBook
import br.com.academy.mercadolivro.model.Book
import br.com.academy.mercadolivro.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(val bookService: BookService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@RequestBody bookRequest: BookRequest): Int =
        bookService.createBook(bookRequest.toBook(), bookRequest.customerId)

    @GetMapping
    fun findAll(): List<Book> = bookService.findAll()

    @GetMapping("/actives")
    fun findByStatus(): List<Book> = bookService.findByStatus()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int) : Book = bookService.findById(id)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable id: Int) = bookService.deleteById(id)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateBook(@PathVariable id: Int, @RequestBody putBookRequest: PutBookRequest) {
        val bookSaved = bookService.findById(id)
        bookService.updateBook(putBookRequest.toBook(bookSaved))
    }

}

