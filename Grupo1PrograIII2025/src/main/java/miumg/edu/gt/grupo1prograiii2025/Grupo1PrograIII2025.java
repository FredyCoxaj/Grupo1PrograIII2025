/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package miumg.edu.gt.grupo1prograiii2025;

import java.util.Scanner;
import static miumg.edu.gt.grupo1prograiii2025.menuSecundario.eliminarDatos;
import static miumg.edu.gt.grupo1prograiii2025.menuSecundario.insertarDatos;
import static miumg.edu.gt.grupo1prograiii2025.menuSecundario.realizarOperaciones;
import static miumg.edu.gt.grupo1prograiii2025.menuSecundario.verDatos;
import static miumg.edu.gt.grupo1prograiii2025.menuSecundario.verValorEspecifico;
/**
 *
 * @author Freddy Elias
 */
public class Grupo1PrograIII2025 {

    public static void main(String[] args) {
        MainMenu.main(args); 
        int opcion = 0;
        Scanner ent = new Scanner(System.in);
        
        //menu
        System.out.println("**************************************************************************");
        System.out.println("* Hola. Bienvenido al entregable #1 del grupo NO.1                       *");
        System.out.println("* A continuacion te mostrare un grupo de opciones que puedes usar para   *");
        System.out.println("* interactuar con nuestro algoritmo                                      *");
        System.out.println("**************************************************************************");
        System.out.println("");
        do {
            System.out.println("------------------------------------------------");
            System.out.println("- 1. Crear una tabla con valores numericos     -");
            System.out.println("- 2. Ver la tabla que se creo anteriormente    -");
            System.out.println("- 3. Obtener un valor especifico de la tabla   -");
            System.out.println("- 4. Eliminar un dato de la tabla              -");
            System.out.println("- 5. Realizar una operacion aritmetica         -");
            System.out.println("- 6. Salir del programa                        -");
            System.out.println("------------------------------------------------");

            try {
                opcion = ent.nextInt();
                
                if (opcion < 1 || opcion > 6) {
                    System.out.println("-----------------------------------------");
                    System.out.println("- El valor ingresado no esta en el menu -");
                    System.out.println("-----------------------------------------");
                    System.out.println("");
                }
            } catch (Exception e) {
                System.out.println("---------------------------------");
                System.out.println("- Debe ingresar un valor valido -");
                System.out.println("---------------------------------");
                System.out.println("");
                ent.nextLine();
            }

            switch (opcion) {
                case 1: insertarDatos();
                    break;
                case 2: verDatos();
                    break;
                case 3: verValorEspecifico();
                    break;
                case 4: eliminarDatos();
                    break;
                case 5: realizarOperaciones();
                    break;
            }

        } while (opcion != 6);
        System.out.println("****************************");
        System.out.println("* Gracias por participar   *");
        System.out.println("****************************");
    }
}
