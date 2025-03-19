/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package miumg.edu.gt.grupo1prograiii2025;

import java.util.Scanner;

/**
 *
 * @author Freddy Elias
 */
public class Grupo1PrograIII2025 {

    public static void main(String[] args) {
        hojaCalculo hoja = new hojaCalculo();
        Integer opcion = null;
        Scanner ent = new Scanner(System.in);
        
        hoja.insertar(1, 1, 4);
        hoja.insertar(1, 2, 8);
        hoja.insertar(2, 1, 10);
        
        hoja.imprimir();
        System.out.println(hoja.obtenerValor(1, 1));
        /**System.out.println("Hola. Bienvenido al entregable #1 del grupo NO.1");
        System.out.println("A continuacion te mostrare un grupo de opciones que puedes usar para");
        System.out.println("interactuar con nuestro algoritmo");
        System.out.println("");
        System.out.println("1. Crear una tabla con valores numericos");
        System.out.println("2. Ver la tabla que se creo anteriormente");
        System.out.println("3. Obtener un valor especifico de la tabla");
        System.out.println("4. Eliminar un dato de la tabla");
        System.out.println("5. Realizar una operacion aritmetica");
        do {
            try {
                opcion = ent.nextInt();
                
                switch(opcion){
                    case 1:
                        System.out.println("Por favor inrese los valores que desea ingresar");
                        Integer var1 = null;
                        do{
                            var1 = ent.nextInt();
                            System.out.println("");
                        } while(var1 == 2);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Ha ocurrido un error");
                opcion = -1;
                ent.nextInt();
            }
        } while (opcion == 6);*/
    }
}
