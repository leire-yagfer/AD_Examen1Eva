package org.example.Model;

//clase abstracta que heredan los tipos de cuenta (ahorro y corriente). Esta hereda de la interface
public abstract class CuentaBancaria implements Imprimible {

    //ATRIBUTOS
    private Persona titular; //nombre de la clase Persona
    private String iban;
    private double saldo; //se va actualizando a medida que se hacen ingresos/retirada


    //CONSTRUCTOR
    public CuentaBancaria(Persona titular, String iban, double saldoInicial) {
        this.titular = titular;
        this.iban = iban;
        this.saldo = saldoInicial;
    }


    //GET Y SET
    public Persona getTitular() {
        return titular;
    }

    public String getIban() {
        return iban;
    }

    public double getSaldo() {
        return saldo;
    }


    //MÉTODOS
    //método para depositar dinero en la cuenta
    public void depositar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
        }
        else System.out.println("La cantidad debe ser mayor que 0.");
    }//depositar


    //método para retirar dinero de la cuenta --> es de tipo boolean para verificar si la retirada ha sido exitosa o no; actúa como un controlador de la operación.
    public boolean retirar(double cantidad) {
        if (cantidad > 0 && saldo >= cantidad) {
            saldo -= cantidad;
            return true; //se ha podido realizar la retirada
        }
        else{
            System.out.println("La cantidad que se quiere retirar debe ser inferior a la que se encuentra disponible.");
            return false; //no se ha podido retirar
        }
    }//retirar


    //método para obtener el saldo actual de la cuenta --> de tipo double porque saldo es de ese tipo de variable
    public double obtenerSaldo() {
        return saldo;
    }//obtenerSaldo


    //método abstracto que será implementado por las clases hijas
    public abstract String devolverInfoString();
}//class