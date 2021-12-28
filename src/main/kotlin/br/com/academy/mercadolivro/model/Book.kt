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

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null
) {

    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value) {
            if (BookStatus.DELETADO == field || BookStatus.CANCELADO == field)
                throw Exception("Não é possível alterar um livro com status $field")

            field = value
        }

    constructor(
        id: Int?,
        name: String,
        price: BigDecimal,
        status: BookStatus?,
        customer: Customer? = null
    ) :
            this(id, name, price, customer) {
        this.status = status
    }

}