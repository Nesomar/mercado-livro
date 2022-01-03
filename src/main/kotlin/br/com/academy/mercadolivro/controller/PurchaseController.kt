package br.com.academy.mercadolivro.controller

import br.com.academy.mercadolivro.controller.mapper.PurchaseMapper
import br.com.academy.mercadolivro.controller.request.PurchaseRequest
import br.com.academy.mercadolivro.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/purchases")
class PurchaseController(private val purchaseService: PurchaseService, private val purchaseMapper: PurchaseMapper) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun purchase(@RequestBody @Valid purchaseRequest: PurchaseRequest) {
        purchaseService.create(purchaseMapper.toModel(purchaseRequest))
    }
}