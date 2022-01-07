package br.com.academy.mercadolivro.service

import br.com.academy.mercadolivro.exception.AuthenticationException
import br.com.academy.mercadolivro.helper.buildCustomer
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
internal class UserDetailCustomServiceTest {

    @InjectMockKs
    private lateinit var userDetailCustomService: UserDetailCustomService

    @MockK
    private lateinit var customService: CustomerService

    @Test
    fun `should return customer by identification`() {

        val id = (Math.random() * 50).toInt()

        val customer = buildCustomer(id = id)

        every { customService.findById(id) } returns Optional.of(customer)

        assertDoesNotThrow { userDetailCustomService.loadUserByUsername(id.toString()) }

        verify(exactly = 1) { customService.findById(id) }
    }

    @Test
    fun `should return exception not found customer by identification`() {

        val id = (Math.random() * 50).toInt()

        every { customService.findById(id) } returns Optional.empty()

        val error = assertThrows<AuthenticationException> { userDetailCustomService.loadUserByUsername(id.toString()) }

        assertEquals("Usuário não encontrado", error.message)
        assertEquals("999", error.errorCode)

        verify(exactly = 1) { customService.findById(id) }
    }
}