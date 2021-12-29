package br.com.academy.mercadolivro.events

import br.com.academy.mercadolivro.model.Purchase
import org.springframework.context.ApplicationEvent

class PurchaseEvent(source: Any, var purchase: Purchase) : ApplicationEvent(source)