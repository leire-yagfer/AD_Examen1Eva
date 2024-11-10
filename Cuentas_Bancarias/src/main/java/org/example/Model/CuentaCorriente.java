package org.example.Model;

//clase abstracta que hereda de CuentaBancaria
public abstract class CuentaCorriente extends CuentaBancaria {

    //ATRIBUTOS
    private String entidadesAutorizadas;

    //CONSTRUCTOR
    public CuentaCorriente(Persona titular, String iban, double saldoInicial, String entidadesAutorizadas) {
        super(titular, iban, saldoInicial);
        this.entidadesAutorizadas = entidadesAutorizadas;
    }


    //GET Y SET
    public String getEntidadesAutorizadas() {
        return entidadesAutorizadas;
    }


    //MÉTODOS
    //método que añado al haber heredado la clase CuentaBancaria
    @Override
    public abstract String devolverInfoString();
}
