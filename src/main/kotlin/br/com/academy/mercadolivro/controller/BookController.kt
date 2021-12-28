package br.com.academy.mercadolivro.controller

import br.com.academy.mercadolivro.controller.request.BookRequest
import br.com.academy.mercadolivro.controller.request.PutBookRequest
import br.com.academy.mercadolivro.controller.response.BookResponse
import br.com.academy.mercadolivro.extension.toBook
import br.com.academy.mercadolivro.extension.toBookResponse
import br.com.academy.mercadolivro.service.BookService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
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
    fun findAll(@PageableDefault(page = 0, size = 10) pageable: Pageable)
            : Page<BookResponse> = bookService.findAll(pageable).map { it.toBookResponse() }

    @GetMapping("/actives")
    fun findByStatus(@PageableDefault(page = 0, size = 10) pageable: Pageable)
            : Page<BookResponse> = bookService.findByStatus(pageable).map { it.toBookResponse() }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): BookResponse = bookService.findById(id).toBookResponse()

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

