package br.com.academy.mercadolivro.model

import br.com.academy.mercadolivro.enums.BookStatus
import br.com.academy.mercadolivro.exception.BadRequestException
import br.com.academy.mercadolivro.exception.enums.CommonErrorCode
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
                throw BadRequestException(CommonErrorCode.ML102.message.format(field), CommonErrorCode.ML102.code)
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