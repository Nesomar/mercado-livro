package br.com.academy.mercadolivro.events.listener

import br.com.academy.mercadolivro.events.PurchaseEvent
import br.com.academy.mercadolivro.service.BookService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UpdateBookListener(private val bookService: BookService) {

    @Async
    @EventListener
    fun updateBookListener(purchaseEvent: PurchaseEvent) {

        bookService.purchase(purchaseEvent.purchase.books)
    }
}