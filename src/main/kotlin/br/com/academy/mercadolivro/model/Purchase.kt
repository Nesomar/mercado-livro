package br.com.academy.mercadolivro.model

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "purchases")
data class Purchase(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "customers_id")
    val customer: Customer,

    @ManyToMany
    @JoinTable(
        name = "purchases_books",
        joinColumns = [JoinColumn(name = "purchases_id")],
        inverseJoinColumns = [JoinColumn(name = "books_id")]
    )
    val books: MutableList<Book>,

    @Column
    var nfe: String? = null,

    @Column
    val price: BigDecimal,

    @Column
    val createdAt: LocalDateTime = LocalDateTime.now()
)
