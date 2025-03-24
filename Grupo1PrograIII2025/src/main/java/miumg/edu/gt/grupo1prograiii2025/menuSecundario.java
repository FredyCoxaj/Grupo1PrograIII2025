/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.grupo1prograiii2025;

import java.util.Scanner;

/**
 *
 * @author Freddy Elias
 */
public class menuSecundario {
    static Scanner ent = new Scanner(System.in);
    static hojaCalculo hoja = new hojaCalculo();

    public static void insertarDatos() {
        int opcion = 0;
        int fila = 0;
        int columna = 0;
        Double valor;
        System.out.println("++++++++++++++++++++++++++++++++++++");
        System.out.println("+ Bienvenido a insercion de datos  +");
        System.out.println("++++++++++++++++++++++++++++++++++++");
        System.out.println("");
        System.out.println("");
        System.out.println("Mapa representarivo de las filas y columnas");
        System.out.println("   | c1 | c2 | c3 | c4 | c5 | c6 | c7 | c8 | c9 | c10|");
        System.out.println("______________________________________________________");
        System.out.println("f1 |____|____|____|____|____|____|____|____|____|____|");
        System.out.println("f3 |____|____|____|____|____|____|____|____|____|____|");
        System.out.println("f4 |____|____|____|____|____|____|____|____|____|____|");
        System.out.println("f5 |____|____|____|____|____|____|____|____|____|____|");
        System.out.println("f6 |____|____|____|____|____|____|____|____|____|____|");
        System.out.println("f7 |____|____|____|____|____|____|____|____|____|____|");
        System.out.println("f8 |____|____|____|____|____|____|____|____|____|____|");
        System.out.println("");
        do {
            while (true) {
                try {
                    System.out.println("- Por favor ingrese el valor que desea ingresar  -");
                    System.out.println("--------------------------------------------------");
                    valor = ent.nextDouble();
                    System.out.println("- Por favor ingrese el numero de la fila         -");
                    System.out.println("--------------------------------------------------");
                    fila = ent.nextInt();
                    System.out.println("- Por favor ingrese el numero de la columna      -");
                    System.out.println("--------------------------------------------------");
                    columna = ent.nextInt();
                    System.out.println("");
                    break;
                } catch (Exception e) {
                    System.out.println("-----------------------------------");
                    System.out.println("- Debe ingresar una opcion valida -");
                    System.out.println("-----------------------------------");
                    ent.nextLine();
                }
            }
            hoja.insertar(fila, columna, valor);

            System.out.println("-----------------------------");
            System.out.println("- Desea ingresar otro valor -");
            System.out.println("- 1. SI                     -");
            System.out.println("- 2. NO                     -");
            System.out.println("-----------------------------");
            try {
                opcion = ent.nextInt();
            } catch (Exception e) {
                System.out.println("");
                System.out.println("------------------------------------");
                System.out.println("- Debe ingresar una opcion valida  -");
                System.out.println("------------------------------------");
                ent.nextLine();
            }
        } while (opcion != 2);
    }

    public static void verDatos() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+ Bienvenido a Visualizacion de datos  +");
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("");
        System.out.println("La tabla esta de la siguiente manera: ");
        System.out.println("");
        System.out.println("|  c1  |  c2   |  c3   |  c4   |  c5   |  c6   |  c7   |  c8   |  c9   |  c10  |");
        hoja.imprimir();
        System.out.println("");

    }

    public static void verValorEspecifico() {
        Integer fila = 0;
        Integer columna = 0;
        int opcion = 0;
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+ Bienvenido a Obtencion de datos      +");
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("");
        //if()
        do {
            while (true) {
                try {
                    System.out.println("Por favor ingese el numero de la fila del valor que desea obtener");
                    fila = ent.nextInt();
                    System.out.println("Por favor ingese el numero de la columna del valor que desea obtener");
                    columna = ent.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("");
                    System.out.println("------------------------------------");
                    System.out.println("- Debe ingresar una opcion valida  -");
                    System.out.println("------------------------------------");
                    ent.nextLine();
                }
            }
            System.out.println("El valor buscado es: " + hoja.obtenerValor(fila, columna));
            System.out.println("-----------------------------");
            System.out.println("- Desea ingresar otro valor -");
            System.out.println("- 1. SI                     -");
            System.out.println("- 2. NO                     -");
            System.out.println("-----------------------------");
            try {
                opcion = ent.nextInt();
                if (opcion < 1 || opcion > 2) {
                    System.out.println("-----------------------------------------");
                    System.out.println("- El valor ingresado no esta en el menu -");
                    System.out.println("-----------------------------------------");
                    System.out.println("");
                }
            } catch (Exception e) {
                System.out.println("");
                System.out.println("------------------------------------");
                System.out.println("- Debe ingresar una opcion valida  -");
                System.out.println("------------------------------------");
                ent.nextLine();
            }
        } while (opcion < 2 || opcion > 2);
    }

    public static void eliminarDatos() {
        System.out.println("");
    }

    public static void realizarOperaciones() {
        System.out.println("");
    }
}
