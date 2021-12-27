package br.com.academy.mercadolivro.model

import br.com.academy.mercadolivro.enums.CustomerStatus
import javax.persistence.*

@Entity(name = "customers")
data class Customer(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email: String,

    @Column
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus
)
