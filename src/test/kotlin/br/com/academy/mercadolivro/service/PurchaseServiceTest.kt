package br.com.academy.mercadolivro.service

import br.com.academy.mercadolivro.events.PurchaseEvent
import br.com.academy.mercadolivro.helper.buildPurchase
import br.com.academy.mercadolivro.repository.PurchaseRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher

@ExtendWith(MockKExtension::class)
internal class PurchaseServiceTest {

    @MockK
    private lateinit var purchaseRepository: PurchaseRepository

    @MockK
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @InjectMockKs
    private lateinit var purchaseService: PurchaseService

    private val purchaseEventSlot = slot<PurchaseEvent>()

    @Test
    fun `should create purchase and publish event`() {

        val purchaseMock = buildPurchase()

        every { purchaseRepository.save(purchaseMock) } returns purchaseMock

        every { applicationEventPublisher.publishEvent(any()) } just  runs

        purchaseService.create(purchaseMock)

        verify(exactly = 1) { purchaseRepository.save(purchaseMock) }
        verify(exactly = 1) { applicationEventPublisher.publishEvent(capture(purchaseEventSlot)) }

        assertEquals(purchaseMock, purchaseEventSlot.captured.purchase)
    }

    @Test
    fun `should update purchase`() {

        val purchaseMock = buildPurchase()

        every { purchaseRepository.save(purchaseMock) } returns purchaseMock

        assertDoesNotThrow{ purchaseService.update(purchaseMock) }

        verify(exactly = 1) { purchaseRepository.save(purchaseMock) }
    }
}

