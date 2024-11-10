DROP DATABASE IF EXISTS Concesionario;
CREATE DATABASE  Concesionario;
USE Concesionario;
CREATE TABLE Coches (
  matricula VARCHAR(10) PRIMARY KEY,
  marca VARCHAR(50) NOT NULL,
  modelo VARCHAR(50) NOT NULL,
  tipo VARCHAR(30) NOT NULL
);

INSERT INTO Coches (matricula, marca, modelo, tipo)
VALUES
    ('6666HHH', 'Renault', 'Clio', 'Deportivo'),
    ('5555BCD', 'Ford', 'SMax', 'Familiar');