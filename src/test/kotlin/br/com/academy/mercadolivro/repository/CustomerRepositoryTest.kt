package br.com.academy.mercadolivro.repository

import br.com.academy.mercadolivro.helper.buildCustomer
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerRepositoryTest {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun clearDataBase() {
        customerRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        customerRepository.deleteAll()
    }

    @Test
    fun `should return name containing`() {

        val marcos = customerRepository.save(buildCustomer(name = "Marcos"))

        val matheus = customerRepository.save(buildCustomer(name = "Matheus"))

        val customers = customerRepository.findByNameContaining("Ma")

        assertEquals(listOf(marcos, matheus), customers)
    }

    @Nested
    inner class ExistByEmail {
        @Test
        fun `should return true when email exists`() {

            val email = "email@test.com"

            customerRepository.save(buildCustomer(email = email))

            assertTrue(customerRepository.existsByEmail(email))

        }

        @Test
        fun `should return false when email do not exists`() {

            val email = "noneexist@test.com"

            assertFalse(customerRepository.existsByEmail(email))
        }
    }

    @Nested
    inner class FindByEmail {
        @Test
        fun `should return customer when email exists`() {

            val email = "email@test.com"

            val mockCustomer = buildCustomer(email = email)

            val customer = customerRepository.save(mockCustomer)

            assertNotNull(customerRepository.findByEmail(email))
            assertEquals(email, customer.email)

        }

        @Test
        fun `should return customer when email do not exists`() {

            val email = "noneexist@test.com"

            assertNull(customerRepository.findByEmail(email))
        }
    }
}