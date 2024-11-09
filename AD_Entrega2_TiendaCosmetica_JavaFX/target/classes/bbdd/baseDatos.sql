DROP DATABASE IF EXISTS tienda_cosmetica;
CREATE DATABASE tienda_cosmetica;
USE tienda_cosmetica;

CREATE TABLE Productos (
                           id_producto INT PRIMARY KEY AUTO_INCREMENT,
                           nombre_producto VARCHAR(100) NOT NULL UNIQUE,
                           precio DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Clientes (
                          id_cliente INT PRIMARY KEY AUTO_INCREMENT,
                          nombre_usuario VARCHAR(100) NOT NULL UNIQUE,
                          email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Ventas (
                        id_venta INT PRIMARY KEY AUTO_INCREMENT,
                        nombreUsuario VARCHAR(100),
                        nombreProducto VARCHAR(100),
                        fecha_venta DATE NOT NULL,
                        cantidad INT NOT NULL,
                        total DECIMAL(10, 2) NOT NULL
);



INSERT INTO Productos (nombre_producto, precio)
VALUES
    ('Crema hidratante', 20.67),
    ('Sérum antiarrugas', 17.99),
    ('Labial mate', 9.49);


INSERT INTO Clientes (nombre_usuario, email)
VALUES
    ('Ana', 'ana.perez@mail.com'),
    ('Carlos', 'carlos.garcia@mail.com');


INSERT INTO Ventas (nombreUsuario, nombreProducto, fecha_venta, cantidad, total)
VALUES
    ('Ana', 'Crema hidratante', '2024-09-30', 2, 39.98),
    ('Carlos', 'Sérum antiarrugas', '2024-09-30', 1, 29.99);