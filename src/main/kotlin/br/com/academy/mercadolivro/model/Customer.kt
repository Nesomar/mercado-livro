package br.com.academy.mercadolivro.model

import javax.persistence.*

@Entity(name = "customers")
data class Customer(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email: String
)
