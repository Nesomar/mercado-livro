package br.com.academy.mercadolivro.controller

import br.com.academy.mercadolivro.controller.request.CustomerRequest
import br.com.academy.mercadolivro.enums.CustomerStatus
import br.com.academy.mercadolivro.helper.buildCustomer
import br.com.academy.mercadolivro.repository.CustomerRepository
import br.com.academy.mercadolivro.security.UserCustomDetails
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
@WithMockUser
internal class CustomerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun cleanCustomerDatabase() = customerRepository.deleteAll()

    @AfterEach
    fun tearDown() = customerRepository.deleteAll()

    @Test
    fun `should return all customers`() {

        val customer1 = customerRepository.save(buildCustomer())
        val customer2 = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/customers"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(customer1.id))
            .andExpect(jsonPath("$[0].name").value(customer1.name))
            .andExpect(jsonPath("$[0].email").value(customer1.email))
            .andExpect(jsonPath("$[0].status").value(customer1.status.name))
            .andExpect(jsonPath("$[1].id").value(customer2.id))
            .andExpect(jsonPath("$[1].name").value(customer2.name))
            .andExpect(jsonPath("$[1].email").value(customer2.email))
            .andExpect(jsonPath("$[1].status").value(customer2.status.name))
    }

    @Test
    fun `should filter all customers by name when get all`() {

        val customer1 = customerRepository.save(buildCustomer(name = "Carlos"))
        customerRepository.save(buildCustomer(name = "Pedro"))

        mockMvc.perform(get("/customers?name=Car"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].name").value(customer1.name))
            .andExpect(jsonPath("$[0].email").value(customer1.email))
            .andExpect(jsonPath("$[0].status").value(customer1.status.name))
    }

    @Test
    fun `should create customer`() {

        val request = CustomerRequest("Fake Name", "emailfake@email.com", "123456789")

        mockMvc.perform(
            post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated)

        val customers = customerRepository.findAll().toList()

        assertEquals(1, customers.size)
        assertEquals(request.nome, customers[0].name)
        assertEquals(request.email, customers[0].email)
    }

    @Test
    fun `should throw error when create customer has invalid information`() {

        val request = CustomerRequest("", "emailfake@email.com", "123456789")

        mockMvc.perform(
            post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.code").value(HttpStatus.UNPROCESSABLE_ENTITY.value()))
            .andExpect(jsonPath("$.message").value("Invalid payload."))
            .andExpect(jsonPath("$.internalCode").value("ML_005"))
    }

    @Test
    fun `should get user by id when user has the same id`() {

        val customer = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/customers/${customer.id}")
            .with(user(UserCustomDetails(customer))))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(customer.id))
            .andExpect(jsonPath("$.name").value(customer.name))
            .andExpect(jsonPath("$.email").value(customer.email))
            .andExpect(jsonPath("$.status").value(customer.status.name))
    }

    @Test
    fun `should return forbidden when user has differ id`() {

        val customer = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/customers/0")
            .with(user(UserCustomDetails(customer))))
            .andExpect(status().isForbidden)
            .andExpect(jsonPath("$.code").value(HttpStatus.FORBIDDEN.value()))
            .andExpect(jsonPath("$.message").value("Unauthorized"))
            .andExpect(jsonPath("$.internalCode").value("ML_000"))
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `should get user by id when user is admin`() {

        val customer = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/customers/${customer.id}")
            .with(user(UserCustomDetails(customer))))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(customer.id))
            .andExpect(jsonPath("$.name").value(customer.name))
            .andExpect(jsonPath("$.email").value(customer.email))
            .andExpect(jsonPath("$.status").value(customer.status.name))
    }

    @Test
    fun `should update customer`() {

        val customer = customerRepository.save(buildCustomer())
        val request = CustomerRequest("Fake Name Update", "emailfakeupdate@email.com", "122333")

        mockMvc.perform(
            put("/customers/${customer.id}").with(user(UserCustomDetails(customer)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNoContent)

        val customerSaved = customerRepository.findById(customer.id!!).get()

        assertEquals("Fake Name Update", customerSaved.name)
        assertEquals("emailfakeupdate@email.com", customerSaved.email)
    }

    @Test
    fun `should throw error when update customer has invalid information`() {

        val customer = customerRepository.save(buildCustomer())
        val request = CustomerRequest("Fake Name Update", " ", "122333")

        mockMvc.perform(
            put("/customers/${customer.id}").with(user(UserCustomDetails(customer)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.code").value(HttpStatus.UNPROCESSABLE_ENTITY.value()))
            .andExpect(jsonPath("$.message").value("Invalid payload."))
            .andExpect(jsonPath("$.internalCode").value("ML_005"))
    }

    @Test
    fun `should delete customer by id`() {

        val customer = customerRepository.save(buildCustomer())

        mockMvc.perform(
            delete("/customers/${customer.id}").with(user(UserCustomDetails(customer)))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent)

        val customerDeleted = customerRepository.findById(customer.id!!).get()

        assertEquals(CustomerStatus.INATIVO, customerDeleted.status)
    }
}