package org.example.Model;

import java.util.ArrayList;
import java.util.List;

//clase que administra las cuentas
public class Banco {
    //ATRIBUTOS
    private List<CuentaBancaria> cuentas; //lista de tipo CuentaBancaria que almacena las cuentas bancarias


    //CONSTRUCTOR
    public Banco() {
        this.cuentas = new ArrayList<>();
    }


    //MÉTODOS
    //método para abrir una nueva cuenta bancaria
    public boolean abrirCuenta(CuentaBancaria cuentaB) {
        //verifico si la cuenta ya existe
        for (CuentaBancaria c : cuentas) {
            if (c.getIban().equals(cuentaB.getIban())) { //igualo el IBAN que se introduce con los que hay en la lista ya
                System.out.println("Error al abrir la cuenta. Ese IBAN ya está en uso. Intenélo de nuevo.");
                return false; //si el IBAN ya existe, no se añade la cuenta
            }
        }//for
        //si no existe
        cuentas.add(cuentaB); //añade la nueva cuenta a la lista
        System.out.println("Cuenta abierta con exito.");
        return true;
    }//abrirCuenta


    //método para listar todas las cuentas
    public void listarCuentas() {
        System.out.println("-Listado de cuentas-");
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas disponibles.");
        } else {
            for (CuentaBancaria cuenta : cuentas) {
                System.out.println(cuenta.devolverInfoString());
            }//for
        }//else
    }//listadoCuentas


    //método para obtener la información de una cuenta específica por su IBAN
    public void obtenerDatosCuenta(String iban) {
        for (CuentaBancaria c : cuentas) {
            if (c.getIban().equals(iban)) {
                System.out.println(c.devolverInfoString());
                return; //salgo del método después de mostrar la información
            }//if
        }//for
        System.out.println("Cuenta no encontrada."); //si no se encuentra la cuenta
    }//obtenerDatosCuenta


    //método para realizar un ingreso en una cuenta
    public void realizarIngreso(String iban, double cantidad) {
        for (CuentaBancaria c : cuentas) {
            if (c.getIban().equals(iban)) {
                c.depositar(cantidad); //realizo el ingreso
                System.out.println("Ingreso realizado con éxito.");
                return; //salgo del método después de realizar el ingreso
            }//if
        }//for
        System.out.println("Error al realizar el ingreso. Verifique el IBAN."); //si no encuentra la cuenta
    }//ingresoCuenta


    //método para retirar dinero de una cuenta conociendo el IBAN
    public void retirarEfectivo(String iban, double cantidad) {
        for (CuentaBancaria c : cuentas) {
            if (c.getIban().equals(iban)) { //compruebo que existe y coincide el IBAN
                if (c.retirar(cantidad)) { //intenta retirar
                    System.out.println("Retirada realizada con éxito.");
                } else {
                    System.out.println("Error al realizar la retirada. Verifique el saldo.");
                }
                return; //salgo después de realizar la retirada
            }
        }//for
        System.out.println("Error al realizar la retirada. Verifique el IBAN."); //si no se encuentra la cuenta
    }//retiradaCuenta


    //método para obtener el saldo de una cuenta específica conociendo el IBAN
    public double obtenerSaldo(String iban) {
        for (CuentaBancaria c : cuentas) {
            if (c.getIban().equals(iban)) { //compruebo que existe y coincide el IBAN
                return c.obtenerSaldo(); //retorno el saldo si encuentra la cuenta
            }//if
        }//for
        return -1; //si no encuentra la cuenta, retorna -1
    }//obtenerSaldo
}//class