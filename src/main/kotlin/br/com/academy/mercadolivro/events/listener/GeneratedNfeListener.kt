package br.com.academy.mercadolivro.events.listener

import br.com.academy.mercadolivro.events.PurchaseEvent
import br.com.academy.mercadolivro.service.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.*

@Component
class GeneratedNfeListener(private val purchaseService: PurchaseService) {

    @Async
    @EventListener
    fun generatedNFEListener(purchaseEvent: PurchaseEvent) {
        purchaseEvent.purchase.nfe = UUID.randomUUID().toString()
        purchaseService.update(purchaseEvent.purchase)
    }
}