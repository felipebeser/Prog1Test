CREATE DATABASE LOJA-INSTRUMENTOS

CREATE TABLE STATUS (
	status int,
	descricao varchar(50),
	constraint status_pk PRIMARY KEY (status)
)

CREATE TABLE product (
	id int NOT NULL AUTO_INCREMENT,
	titulo varchar (50) NOT NULL,
	categoria varchar (50) NOT NULL,
	preco float NOT NULL,
	status int NOT NULL,
	constraint product_pk PRIMARY KEY (id),
	constraint status_fk FOREIGN KEY (status)
		references STATUS(status)
)

INSERT INTO STATUS VALUES()


