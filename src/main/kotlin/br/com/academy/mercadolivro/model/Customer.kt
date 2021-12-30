package br.com.academy.mercadolivro.model

import br.com.academy.mercadolivro.enums.CustomerStatus
import br.com.academy.mercadolivro.enums.UserRoles
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
    var status: CustomerStatus,

    @Column
    var password: String,

    @CollectionTable(name = "customers_roles", joinColumns = [JoinColumn(name = "customers_id")])
    @ElementCollection(targetClass = UserRoles::class, fetch = FetchType.EAGER)
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var roles: Set<UserRoles> = setOf()

)
