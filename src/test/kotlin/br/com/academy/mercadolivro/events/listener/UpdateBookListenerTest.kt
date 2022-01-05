package br.com.academy.mercadolivro.events.listener

import br.com.academy.mercadolivro.events.PurchaseEvent
import br.com.academy.mercadolivro.helper.buildPurchase
import br.com.academy.mercadolivro.service.BookService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class UpdateBookListenerTest {

    @MockK
    private lateinit var bookService: BookService

    @InjectMockKs
    private lateinit var updateBookListener: UpdateBookListener

    @Test
    fun `should purchase books`() {

        val purchase = buildPurchase()

        every { bookService.purchase(purchase.books) } just runs

        updateBookListener.updateBookListener(PurchaseEvent(this, purchase))

        verify(exactly = 1) { bookService.purchase(purchase.books) }
    }
}