package br.com.academy.mercadolivro.events.listener

import br.com.academy.mercadolivro.events.PurchaseEvent
import br.com.academy.mercadolivro.helper.buildPurchase
import br.com.academy.mercadolivro.service.PurchaseService
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
internal class GeneratedNfeListenerTest {

    @MockK
    private lateinit var purchaseService: PurchaseService

    @InjectMockKs
    private lateinit var generatedNfeListener: GeneratedNfeListener

    @Test
    fun `should generated nfe`() {

        val purchase = buildPurchase(nfe = null)

        val mockNfe = UUID.randomUUID()

        val purchaseExpected = purchase.copy(nfe = mockNfe.toString())

        mockkStatic(UUID::class)

        every { UUID.randomUUID() } returns mockNfe

        every { purchaseService.update(purchaseExpected) } just runs

        generatedNfeListener.generatedNFEListener(PurchaseEvent(this, purchase))

        verify(exactly = 1) { purchaseService.update(purchaseExpected) }
    }
}