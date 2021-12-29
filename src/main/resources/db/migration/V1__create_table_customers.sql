-- mercado_livro.customers definition

CREATE TABLE customers (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email varchar(255) UNIQUE NOT NULL,
  name varchar(255) NOT NULL
);