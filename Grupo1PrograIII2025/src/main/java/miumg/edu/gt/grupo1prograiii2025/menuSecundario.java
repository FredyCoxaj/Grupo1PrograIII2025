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
    public static void insertarDatos() {
        Scanner ent = new Scanner(System.in);
        hojaCalculo hoja = new hojaCalculo();
        int opcion = 0; int fila = 0; int columna = 0; int valor = 0;
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
                    valor = ent.nextInt();
                    System.out.println("- Por favor ingrese el numero de la fila         -");
                    System.out.println("--------------------------------------------------");
                    fila = ent.nextInt();
                    System.out.println("- Por favor ingrese el numero de la columna      -");
                    System.out.println("--------------------------------------------------");
                    columna = ent.nextInt();
                    System.out.println("");
                    break;
                } catch (Exception e) {
                    System.out.println("Debe ingresar una opcion valida");
                    ent.nextLine();
                }
            }
            hoja.insertar(fila, columna, valor);
            
            try {
                System.out.println("-----------------------------");
                System.out.println("- Desea ingresar otro valor -");
                System.out.println("- 1. SI                     -");
                System.out.println("- 2. NO                     -");
                System.out.println("-----------------------------");
                opcion = ent.nextInt();
            } catch (Exception e) {
                System.out.println("Debe ingresar una opcion valida");
                ent.nextLine();
            }
        } while (opcion != 2);
    }

    public static void verDatos() {
        System.out.println("");
    }

    public static void verValorEspecifico() {
        System.out.println("");
    }

    public static void eliminarDatos() {
        System.out.println("");
    }

    public static void realizarOperaciones() {
        System.out.println("");
    }
}
