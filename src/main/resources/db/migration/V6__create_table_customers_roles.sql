-- mercado_livro.customers definition

CREATE TABLE customers_roles (
  customers_id INT NOT NULL,
  role VARCHAR(255) NOT NULL,
  FOREIGN KEY(customers_id) REFERENCES customers(id)
);