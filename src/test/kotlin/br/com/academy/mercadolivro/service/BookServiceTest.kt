package br.com.academy.mercadolivro.service

import br.com.academy.mercadolivro.enums.BookStatus
import br.com.academy.mercadolivro.exception.NotFoundException
import br.com.academy.mercadolivro.helper.buildBook
import br.com.academy.mercadolivro.helper.buildCustomer
import br.com.academy.mercadolivro.repository.BookRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

@ExtendWith(MockKExtension::class)
internal class BookServiceTest {

    @MockK
    private lateinit var bookRepository: BookRepository

    @MockK
    private lateinit var customerService: CustomerService

    @InjectMockKs
    @SpyK
    private lateinit var bookService: BookService

    @Test
    fun `should create book`() {

        val bookMock = buildBook()

        val customerId = (Math.random().toInt() * 50)

        every { customerService.findCustomerById(customerId) } returns buildCustomer()

        every { bookRepository.save(bookMock) } returns bookMock

        val bookId = bookService.createBook(bookMock, customerId)

        assertEquals(bookMock.id, bookId)

        verify(exactly = 1) { bookRepository.save(bookMock) }
        verify(exactly = 1) { customerService.findCustomerById(customerId) }

    }

    @Test
    fun `should return all books`() {

        val booksMock = listOf(buildBook(), buildBook())

        val pageable = Pageable.unpaged()

        every { bookRepository.findAll(pageable) } returns PageImpl(booksMock)

        val books = bookService.findAll(pageable)

        assertEquals(booksMock, books.content)

        verify(exactly = 1) { bookRepository.findAll(pageable) }
    }

    @Test
    fun `should return all books by status`() {

        val booksMock = listOf(buildBook(), buildBook())

        val pageable = Pageable.unpaged()

        every { bookRepository.findByStatus(BookStatus.ATIVO, pageable) } returns PageImpl(booksMock)

        val books = bookService.findByStatusActive(pageable)

        assertEquals(booksMock, books.content)

        verify(exactly = 1) { bookRepository.findByStatus(BookStatus.ATIVO, pageable) }
    }

    @Test
    fun `should return book by id`() {

        val bookId = (Math.random().toInt() * 50)

        every { bookRepository.findById(bookId) } returns Optional.of(buildBook(id = bookId))

        val book = bookService.findById(bookId)

        assertEquals(bookId, book.id)

        verify(exactly = 1) { bookRepository.findById(bookId) }
    }

    @Test
    fun `should return exception not found book by id`() {

        val bookId = (Math.random().toInt() * 50)

        every { bookRepository.findById(bookId) } returns Optional.empty()

        val error = assertThrows<NotFoundException> { bookService.findById(bookId) }

        assertEquals("ML_101", error.errorCode)
        assertEquals("Book [${bookId}] not exists", error.message)

        verify(exactly = 1) { bookRepository.findById(bookId) }
    }

    @Test
    fun `should deleted book by id`() {

        val bookId = (Math.random().toInt() * 50)

        val bookMock = buildBook(id = bookId)

        every { bookService.findById(bookId) } returns bookMock

        every { bookRepository.save(bookMock) } returns bookMock

        assertDoesNotThrow { bookService.deleteById(bookId) }

        verify(exactly = 1) { bookService.findById(bookId) }
        verify(exactly = 1) { bookRepository.save(bookMock) }
    }

    @Test
    fun `should update book by id`() {

        val bookId = (Math.random().toInt() * 50)

        val bookMock = buildBook(id = bookId)

        every { bookRepository.save(bookMock) } returns bookMock

        assertDoesNotThrow { bookService.updateBook(bookMock) }

        verify(exactly = 1) { bookRepository.save(bookMock) }
    }

    @Test
    fun `should deleted by customer`() {

        val customerMock = buildCustomer()

        val booksMock = listOf(buildBook(), buildBook())

        every { bookRepository.findByCustomer(customerMock) } returns booksMock

        every { bookRepository.saveAll(booksMock) } returns booksMock.toMutableList()

        assertDoesNotThrow { bookService.deleteByCustomer(customerMock) }

        verify(exactly = 1) { bookRepository.findByCustomer(customerMock) }
        verify(exactly = 1) { bookRepository.saveAll(booksMock) }
    }

    @Test
    fun `should return books by list of ids`() {

        val booksIds = setOf((Math.random().toInt() * 50), (Math.random().toInt() * 50))

        every { bookRepository.findAllById(booksIds) } returns listOf(buildBook(), buildBook())

        val books = bookService.findAllById(booksIds)

        assertTrue(books.isNotEmpty())

        verify(exactly = 1) { bookRepository.findAllById(booksIds) }

    }

    @Test
    fun `should purchase books`() {

        val booksMock = mutableListOf(buildBook(), buildBook())

        every { bookRepository.saveAll(booksMock) } returns booksMock

        assertDoesNotThrow { bookService.purchase(booksMock) }

        verify(exactly = 1) { bookRepository.saveAll(booksMock) }
    }
}