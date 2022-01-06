package br.com.academy.mercadolivro.repository

import br.com.academy.mercadolivro.enums.BookStatus
import br.com.academy.mercadolivro.helper.buildBook
import br.com.academy.mercadolivro.helper.buildCustomer
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class BookRepositoryTest {

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun clearDataBase() {
        bookRepository.deleteAll()
        customerRepository.deleteAll()
    }

    @Test
    fun `should return books by status`() {

        val customer = customerRepository.save(buildCustomer())

        val book = bookRepository.save(buildBook(customer = customer))

        val pageable = Pageable.unpaged()

        val books = bookRepository.findByStatus(BookStatus.ATIVO, pageable)

        Assertions.assertNotNull(books)

    }
}