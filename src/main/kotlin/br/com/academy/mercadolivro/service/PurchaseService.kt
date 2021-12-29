package br.com.academy.mercadolivro.service

import br.com.academy.mercadolivro.events.PurchaseEvent
import br.com.academy.mercadolivro.model.Purchase
import br.com.academy.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun create(purchase: Purchase): Int? {
        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchase))
        return purchaseRepository.save(purchase).id
    }

    fun update(purchase: Purchase) {
        purchaseRepository.save(purchase)
    }
}