package br.com.academy.mercadolivro.service

import br.com.academy.mercadolivro.enums.CustomerStatus
import br.com.academy.mercadolivro.enums.UserRoles
import br.com.academy.mercadolivro.exception.NotFoundException
import br.com.academy.mercadolivro.model.Customer
import br.com.academy.mercadolivro.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var bookService: BookService

    @MockK
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @InjectMockKs
    @SpyK
    private lateinit var customerService: CustomerService

    @Test
    fun `should return all customers`() {

        val mockCustomers = listOf(buildCustomer(), buildCustomer())

        every { customerRepository.findAll() } returns mockCustomers

        val customers = customerService.findByName(null)

        assertEquals(mockCustomers, customers)

        verify(exactly = 1) { customerRepository.findAll() }

        verify(exactly = 0) { customerRepository.findByNameContaining(any()) }
    }

    @Test
    fun `should return customers when name is informed`() {

        val name = UUID.randomUUID().toString()

        val mockCustomers = listOf(buildCustomer())

        every { customerRepository.findByNameContaining(name) } returns mockCustomers

        val customers = customerService.findByName(name)

        assertEquals(mockCustomers, customers)

        verify(exactly = 0) { customerRepository.findAll() }
        verify(exactly = 1) { customerRepository.findByNameContaining(name) }

    }

    @Test
    fun `not found customer by name`() {

        val name = UUID.randomUUID().toString()

        every { customerRepository.findByNameContaining(name) } returns emptyList()

        val customers = customerService.findByName(name)

        assertTrue(customers.isEmpty())

        verify(exactly = 0) { customerRepository.findAll() }
        verify(exactly = 1) { customerRepository.findByNameContaining(name) }

    }

    @Test
    fun `should create customer and encrypt password`() {

        val initialPassword = (Math.random() * 10).toString()
        val mockCustomer = buildCustomer(password = initialPassword)
        val mockPassword = UUID.randomUUID().toString()

        every { customerRepository.save(mockCustomer) } returns mockCustomer

        every { bCryptPasswordEncoder.encode(initialPassword) } returns mockPassword

        val idCustomer = customerService.createCustomer(mockCustomer)

        assertEquals(mockCustomer.id, idCustomer)

        verify(exactly = 1) { customerRepository.save(any()) }
        verify(exactly = 1) { bCryptPasswordEncoder.encode(initialPassword) }
    }

    @Test
    fun `should return customer by id`() {

        val id = (Math.random() * 10).toInt()

        val mockCustomer = buildCustomer(id = id)

        every { customerRepository.findById(id) } returns Optional.of(mockCustomer)

        val customer = customerService.findCustomerById(id)

        assertEquals(id, customer.id)

        verify(exactly = 1) { customerRepository.findById(id) }

    }

    @Test
    fun `should throw error when customer not found`() {

        val id = (Math.random() * 10).toInt()

        every { customerRepository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException> { customerService.findCustomerById(id) }

        assertEquals("ML_201", error.errorCode)
        assertEquals("Customer [${id}] not exists", error.message)

        verify(exactly = 1) { customerRepository.findById(id) }
    }

    @Test
    fun `should update customer`() {

        val id = (Math.random() * 10).toInt()

        val customerMock = buildCustomer(id = id)

        every { customerRepository.existsById(id) } returns true

        every { customerRepository.save(customerMock) } returns customerMock

        assertDoesNotThrow { customerService.updateCustomer(customerMock) }

        verify(exactly = 1) { customerRepository.existsById(id) }
        verify(exactly = 1) { customerRepository.save(customerMock) }
    }

    @Test
    fun `should update not found customer`() {

        val id = (Math.random() * 10).toInt()

        val customerMock = buildCustomer(id = id)

        every { customerRepository.existsById(id) } returns false

        assertDoesNotThrow { customerService.updateCustomer(customerMock) }

        verify(exactly = 1) { customerRepository.existsById(id) }
        verify(exactly = 0) { customerRepository.save(customerMock) }
    }

    @Test
    fun `should deleted customer`() {

        val id = (Math.random() * 10).toInt()

        val customerMock = buildCustomer(id = id)

        every { customerService.findCustomerById(id) } returns customerMock

        every { bookService.deleteByCustomer(customerMock) } just runs

        every { customerRepository.save(customerMock) } returns customerMock

        assertDoesNotThrow { customerService.deleteCustomer(id) }

        verify(exactly = 1) { bookService.deleteByCustomer(customerMock) }
        verify(exactly = 1) { customerRepository.save(customerMock) }
        verify(exactly = 1) { customerService.findCustomerById(id) }

    }

    @Test
    fun `should email exists`() {

        val email = "${UUID.randomUUID()}@email.com"

        every { customerRepository.existsByEmail(email) } returns true

        assertFalse(customerService.emailAvailable(email))

        verify(exactly = 1) { customerRepository.existsByEmail(email) }
        verify(exactly = 1) { customerService.emailAvailable(email) }
    }

    @Test
    fun `should email not exists`() {

        val email = "${UUID.randomUUID()}@email.com"

        every { customerRepository.existsByEmail(email) } returns false

        assertTrue(customerService.emailAvailable(email))

        verify(exactly = 1) { customerRepository.existsByEmail(email) }
        verify(exactly = 1) { customerService.emailAvailable(email) }
    }

    @Test
    fun `should find by email`() {

        val mockCustomer = buildCustomer()

        val email = "${UUID.randomUUID()}@email.com"

        every { customerRepository.findByEmail(email) } returns mockCustomer

        val customer = customerService.findByEmail(email)

        assertEquals(mockCustomer, customer)

        verify(exactly = 1) { customerRepository.findByEmail(email) }
    }

    @Test
    fun `should not find by email`() {

        val email = "${UUID.randomUUID()}@email.com"

        every { customerRepository.findByEmail(email) } returns null

        val customer = customerService.findByEmail(email)

        assertEquals(null, customer)

        verify(exactly = 1) { customerRepository.findByEmail(email) }
    }

    @Test
    fun `should find by id`() {

        val mockCustomer = buildCustomer()

        val id = (Math.random() * 10).toInt()

        every { customerRepository.findById(id) } returns Optional.of(mockCustomer)

        val customer = customerService.findById(id)

        assertEquals(mockCustomer, customer.get())

        verify(exactly = 1) { customerRepository.findById(id) }
    }

    @Test
    fun `should not find by id`() {

        val id = (Math.random() * 10).toInt()

        every { customerRepository.findById(id) } returns Optional.empty()

        val customer = customerService.findById(id)

        assertTrue(customer.isEmpty)

        verify(exactly = 1) { customerRepository.findById(id) }
    }

    private fun buildCustomer(
        id: Int? = (Math.random() * 10).toInt(),
        name: String = "customer name",
        email: String = "${UUID.randomUUID()}@email.com",
        password: String = "password"
    ) = Customer(
        id = id,
        name = name,
        email = email,
        status = CustomerStatus.ATIVO,
        password = password,
        roles = setOf(UserRoles.CUSTOMER)
    )
}