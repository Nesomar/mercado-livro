-- mercado_livro.customers definition

CREATE TABLE books (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) NOT NULL,
  price decimal(10,2) NOT NULL,
  status varchar(255) NOT NULL,
  customer_id int NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES customers(id)
);