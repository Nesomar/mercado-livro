package br.com.academy.mercadolivro.controller

import br.com.academy.mercadolivro.controller.request.BookRequest
import br.com.academy.mercadolivro.helper.buildCustomer
import br.com.academy.mercadolivro.repository.BookRepository
import br.com.academy.mercadolivro.repository.CustomerRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
@WithMockUser
internal class BookControllerTest {

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun clearDataBase() {
        bookRepository.deleteAll()
        customerRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        bookRepository.deleteAll()
        customerRepository.deleteAll()
    }

    @Test
    fun `should create book`() {

        val customer = customerRepository.save(buildCustomer())

        val bookRequest = BookRequest("Livro de Teste", BigDecimal.TEN, customer.id!!)

        mockMvc.perform(post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookRequest)))
            .andExpect(status().isCreated)

        val books = bookRepository.findAll().toList()

        assertEquals(1, books.size)
        assertEquals(bookRequest.name, books[0].name)
        assertEquals(bookRequest.customerId, books[0].customer?.id)
    }
}