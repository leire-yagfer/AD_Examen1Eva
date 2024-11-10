package com.example.clientesempresarepasogeneral.Clases;

public class Sesion {
    private static Clientes cliente1;

    public static Clientes getCliente() {
        return cliente1;
    }

    public static void setCliente(Clientes cliente) {
        cliente1 = cliente;
    }
}
