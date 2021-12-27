package br.com.academy.mercadolivro.model

import br.com.academy.mercadolivro.enums.BookStatus
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "books")
class Book(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null
) {

}