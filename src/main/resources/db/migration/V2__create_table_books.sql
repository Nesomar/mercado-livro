-- mercado_livro.customers definition

CREATE TABLE books (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  status VARCHAR(255) NOT NULL,
  customers_id INT,
  FOREIGN KEY (customers_id) REFERENCES customers(id)
);