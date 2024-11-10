package org.example;

import org.example.Model.*;
import org.example.util.comprobaciones;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Banco banco = new Banco(); //creo la instancia del banco para manejar las cuentas
        Scanner sc = new Scanner(System.in);
        int opcion;

        try {
            do {
                System.out.println("-------------- \n " + "Elige opción:\n " + "1. Abrir una nueva cuenta \n " + "2. Ver un listado de las cuentas abiertas \n " + "3. Obtener los datos de una cuenta concreta \n " + "4. Realizar un ingreso en una cuenta \n " + "5. Retirar efectivo de una cuenta \n " + "6. Consultar el saldo actual de una cuenta \n " + "7. Salir de la aplicación ");
                opcion = sc.nextInt();

                sc.nextLine(); //evito que me da error tras haber introducido un valor numérico y posteriormente un String

                switch (opcion) {
                    case 1: //abrir cuenta
                        abrirNuevaCuenta(banco, sc);
                        break;
                    case 2: //listado cuentas abiertas
                        banco.listarCuentas();
                        break;
                    case 3: //datos de una cuenta
                        obtenerDatosCuenta(banco, sc);
                        break;
                    case 4: //realizar ingreso
                        realizarIngreso(banco, sc);
                        break;
                    case 5: //retirar efectivo
                        retirarEfectivo(banco, sc);
                        break;
                    case 6: //consultar saldo
                        consultarSaldo(banco, sc);
                        break;
                    case 7: //salir
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida, intente de nuevo.");
                        break;
                }//switch
            } while (opcion != 7);
        } catch (Exception e) {
            sc.nextLine(); //reinicio el scanner para que no se me salga del bucle del principio
            System.out.println("Error. Se han introducido datos erróneos. Inténtelo de nuevo."); //se sale del programa porque no está dentro de un bucle este try-catch como el de abajo (el de abrirCuenta)
        }//try-catch
    }//main


    //MÉTODOS
    //método para abrir una nueva cuenta bancaria
    private static void abrirNuevaCuenta(Banco banco, Scanner sc) {
        System.out.println("- Abrir nueva cuenta -");
        System.out.print("Ingrese el nombre del titular: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese los apellidos del titular: ");
        String apellidos = sc.nextLine();
        System.out.print("Ingrese el DNI del titular: ");
        String dni = sc.nextLine();
        if(!comprobaciones.validarDni(dni)){
            System.out.println("El DNI no es valido.");
            return;
        }
        Persona titular = new Persona(nombre, apellidos, dni);

        try {
            System.out.print("Ingrese el IBAN (debe comenzar con 'ES' y tener 20 caracteres): ");
            String iban = sc.nextLine();
            if(!comprobaciones.validarIban(iban)){
                System.out.println("IBAN no válido.");
                return;
            }

            System.out.println("Seleccione el tipo de cuenta que quiere abrir:");
            System.out.println("1. Cuenta de ahorro \n" + "2. Cuenta corriente personal \n" + "3. Cuenta corriente empresa");
            int tipoCuenta = sc.nextInt();
            sc.nextLine();

            System.out.print("Ingrese el saldo inicial que tendrá la cuenta: ");
            double saldoInicial = sc.nextDouble();
            sc.nextLine();
            comprobaciones.comprobacionSaldoPositivo(sc, saldoInicial); //compruebo que sea positivo

            CuentaBancaria nuevaCuenta; //creo un objeto de tipo CuentaBancaria para posteriormente rellenarlo con los datos del titular

            switch (tipoCuenta) { //dependiendo del tipo de cuenta que quiera abrir el usuario, pedirá unos datos u otros
                case 1: //cuenta de ahorro
                    System.out.print("Ingrese el tipo de interés anual: ");
                    double interes = sc.nextDouble();
                    comprobaciones.comprobacionInteresPositivo(sc, interes); //compruebo que sea positivo
                    nuevaCuenta = new CuentaAhorro(titular, iban, saldoInicial, interes); //creo la nueva cuenta con los datos introducidos
                    break;
                case 2: //CC personal
                    System.out.print("Ingrese las entidades autorizadas (separadas por comas): ");
                    String entidades = sc.nextLine();
                    System.out.print("Ingrese la comisión de mantenimiento: ");
                    double comision = sc.nextDouble();
                    comprobaciones.comprobacionComisionPositiva(sc, comision); //compruebo que sea positivo
                    nuevaCuenta = new CuentaCorrientePersonal(titular, iban, saldoInicial, entidades, comision); //creo la nueva cuenta con los datos introducidos
                    break;
                case 3: //CC empresa
                    System.out.print("Ingrese las entidades autorizadas (separadas por comas): ");
                    String entidadesEmpresa = sc.nextLine();
                    System.out.print("Ingrese el máximo descubierto permitido: "); //crédito que ofrece el banco como saldo adicional en una cuenta
                    double maxDescubierto = sc.nextDouble();
                    comprobaciones.comprobacionComisionPositiva(sc, maxDescubierto); //compruebo que sea positiva
                    System.out.print("Ingrese el tipo de interés por descubierto: "); //cantidad que se cobra al deudor por no pagar una deuda a tiempo
                    double interesDescubierto = sc.nextDouble();
                    comprobaciones.comprobacionInteresPositivo(sc, interesDescubierto); //compruebo que sea positivo
                    System.out.print("Ingrese la comisión fija por descubierto: "); //cantidad que se paga a la entidad financiera por estar en números rojos
                    double comisionDescubierto = sc.nextDouble();
                    comprobaciones.comprobacionComisionPositiva(sc, comisionDescubierto); //compruebo que sea positiva
                    nuevaCuenta = new CuentaCorrienteEmpresa(titular, iban, saldoInicial, entidadesEmpresa, maxDescubierto, interesDescubierto, comisionDescubierto); //creo la nueva cuenta con los datos introducidos
                    break;
                default:
                    System.out.println("Tipo de cuenta no válido. Inténtelo de nuevo.");
                    return;
            }//switch

            banco.abrirCuenta(nuevaCuenta); //llamo al método para que compruebe todos los datos introducidos y abra la cuenta o saque error de apertura
        } catch (Exception e) {
            sc.nextLine(); //reinicio el scanner para que no se me salga del bucle del principio
            System.out.println("Error. Se han introducido datos erróneos. Inténtelo de nuevo."); //me vuelve a reiniciar el bucle porque estoy llamando al método desde el menú principal
        }//try-catch
    }//abrirNuevaCuenta


    //método para obtener los datos de una cuenta específica
    private static void obtenerDatosCuenta(Banco banco, Scanner sc) {
        System.out.println("- Obtener datos de una cuenta -");
        System.out.print("Ingrese el IBAN de la cuenta: ");
        String iban = sc.nextLine();
        banco.obtenerDatosCuenta(iban); //llamo al método que imprime los datos
    }//obtenerDatosCuenta


    //método para realizar un ingreso en una cuenta
    private static void realizarIngreso(Banco banco, Scanner sc) {
        System.out.println("- Realizar ingreso -");
        System.out.print("Ingrese el IBAN de la cuenta: ");
        String iban = sc.nextLine();
        System.out.print("Introduzca la cantidad que quiere ingresar: ");
        double cantidad = sc.nextDouble();

        banco.realizarIngreso(iban, cantidad); //llamo al método que maneja el ingreso
    }//realizarIngreso


    //método para retirar efectivo de una cuenta
    private static void retirarEfectivo(Banco banco, Scanner sc) {
        System.out.println("- Retirar efectivo -");
        System.out.print("Ingrese el IBAN de la cuenta: ");
        String iban = sc.nextLine();
        System.out.print("Ingrese la cantidad que desea retirar: ");
        double cantidad = sc.nextDouble();

        banco.retirarEfectivo(iban, cantidad); //llamo al método que maneja la retirada
    }//retirarEfectivo


    //método para consultar el saldo de una cuenta
    private static void consultarSaldo(Banco banco, Scanner sc) {
        System.out.println("- Consultar saldo -");
        System.out.print("Ingrese el IBAN de la cuenta: ");
        String iban = sc.nextLine();
        double saldo = banco.obtenerSaldo(iban); //obtengo el saldo de la cuenta que tiene el IBAN introducido

        //mensaje según si el saldo es válido o cuenta no encontrada
        System.out.println(saldo >= 0 ? "El saldo actual de la cuenta es: " + saldo + " euros." : "Cuenta no encontrada.");
    }//consultarSaldo
}//class