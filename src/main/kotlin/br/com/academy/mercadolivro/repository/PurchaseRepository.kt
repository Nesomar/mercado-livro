package br.com.academy.mercadolivro.repository

import br.com.academy.mercadolivro.model.Purchase
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PurchaseRepository: CrudRepository<Purchase, Int>, JpaRepository<Purchase, Int>