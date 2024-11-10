package org.example.Model;

//clase que hereda de CuentaCorriente
public class CuentaCorrientePersonal extends CuentaCorriente {
    private double comisionMantenimiento;

    //CONSTRUCTOR
    public CuentaCorrientePersonal(Persona titular, String iban, double saldoInicial, String entidadesAutorizadas, double comisionMantenimiento) {
        super(titular, iban, saldoInicial, entidadesAutorizadas);
        this.comisionMantenimiento = comisionMantenimiento;
    }


    //MÉTODOS
    //método que añado al haber heredado la clase CuentaCorriente --> contiene los datos de la cuenta
    @Override
    public String devolverInfoString() {
        return "  -Cuenta Corriente Personal. Titular: " + getTitular().devolverInfoString() + ", IBAN: " + getIban() + ", saldo: " + getSaldo() + ", entidades autorizadas: " + getEntidadesAutorizadas() + ", comisión de mantenimiento: " + comisionMantenimiento;
    }//devolverInfoString
}//class