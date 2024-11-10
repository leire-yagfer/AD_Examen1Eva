package org.example.Model;

//clase que hereda de CuentaCorriente
public class CuentaCorrienteEmpresa extends CuentaCorriente {

    //ATRIBUTOS
    private double maximoDescubierto;
    private double tipoInteresDescubierto;
    private double comisionFijaDescubierto;


    //CONSTRUCTOR
    public CuentaCorrienteEmpresa(Persona titular, String iban, double saldoInicial, String entidadesAutorizadas, double maximoDescubierto, double tipoInteresDescubierto, double comisionFijaDescubierto) {
        super(titular, iban, saldoInicial, entidadesAutorizadas);
        this.maximoDescubierto = maximoDescubierto;
        this.tipoInteresDescubierto = tipoInteresDescubierto;
        this.comisionFijaDescubierto = comisionFijaDescubierto;
    }


    //MÉTODOS
    //método que añado al haber heredado la clase CuentaCorriente
    @Override
    public String devolverInfoString() {
        return "  -Cuenta Corriente Empresa. Titular: " + getTitular().devolverInfoString() + ", IBAN: " + getIban() + ", Saldo: " + getSaldo() + ", Entidades autorizadas: " + getEntidadesAutorizadas() + ", Máximo descubierto: " + maximoDescubierto + ", Interés por descubierto: " + tipoInteresDescubierto + ", Comisión fija por descubierto: " + comisionFijaDescubierto;
    }//devolverInfoString
}//class