-- mercado_livro.customers definition

CREATE TABLE purchases(
    id INT AUTO_INCREMENT PRIMARY KEY,
    customers_id INT NOT NULL,
    nfe VARCHAR(255),
    created_at DATETIME NOT NULL,
    FOREIGN KEY(customers_id) REFERENCES customers(id)
);

CREATE TABLE purchases_books(
    purchases_id INT NOT NULL,
    books_id INT NOT NULL,
    FOREIGN KEY(purchases_id) REFERENCES purchases(id),
    FOREIGN KEY(books_id) REFERENCES books(id),
    PRIMARY KEY(purchases_id, books_id)
);