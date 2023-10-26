package ev3_prg_hundir_la_flota;

import java.util.Scanner;
/**
 *
 * @author Angie Melissa Candela Alvarez
 */
public class EV3_PRG_HUNDIR_LA_FLOTA {
    
    //=======================================
    // FUNCION MAIN PRINCIPAL
    //=======================================
    /*Aqui se da la bienvenida al juego, segun el modo de juegose crean los tableros 
     * y con la funcion juega_partida se inicia el juego*/

    public static void main(String[] args) {
         System.out.println("========== ¡HUNDIR LA FLOTA! ==========");
         
         int opcion = modo_juego();

        switch (opcion) {
            case 1:
                System.out.println("\n  Facil:");
                String tablero_oculto[][] = tablero_oculto(10,10,1);
                juega_partida(tablero_visible(10,10),tablero_oculto, 1, 50);
                break;
            case 2:
                System.out.println("\n  Medio:");
                String tablero_oculto2[][] = tablero_oculto(10,10,2);
                juega_partida(tablero_visible(10,10),tablero_oculto2, 2, 30);
                break;
            case 3:
                System.out.println("\n  Dificil:");
                String tablero_oculto3[][] = tablero_oculto(10,10,3);
                juega_partida(tablero_visible(10,10),tablero_oculto3, 3, 10);
                break;
            case 4:
                System.out.println("\n  Personalizado:\n");
                int logitud = tamaño_personalizado();
                String tablero_oculto4[][] = tablero_oculto(logitud, logitud,4);
                int intentos = disparos_personalizado();
                juega_partida(tablero_visible(logitud,logitud),tablero_oculto4, 4, intentos);
                break;
            default:
                System.err.println("Opcion incorrecta");
        }
    }
    
    
    public static int modo_juego() {
     // Muestra el menu y devuelve la opcion elegida por el usuario (1, 2, 3 o 4)
        int opcion;

        System.out.println("Niveles:");
        System.out.println("1. Facil: 10 barcos (5 lanchas, 3 buques, 1 acorazado y 1 portaaviones) (50 disparos).");
        System.out.println("2. Medio: 5 barcos (2 lanchas, 1 buque, 1 acorazado y 1 portaaviones) (30 disparos).");
        System.out.println("3. Dificil: 2 barcos (1 lancha y 1 buque) (10 disparos).");
        System.out.println("4. Personalizado.");
        
        Scanner in = new Scanner(System.in);
        opcion = in.nextInt();

        return opcion;
    }
    
    
    //=======================================
    // FUNCIONES DE TABLERO
    //=======================================
    public static String[][] tablero_visible(int filas, int columnas){
        /*crea un tablero de tamaño 10x10 con numeros(0 al 9) horizontalmente 
         * y letras (A a la J) verticalmente, pero solo en las posiciones laterales (0,x.. y ..x,0).
        las demas posiciones contendran: "- " */
        String letra_fila[] = {"A", "B", "C", "D", "E", "F", "G","H", "I", "J", "K", "L", "M", "N","O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String[][] tablero = new String[filas+1][columnas+1];
        int contador = 0;
        int contador2 = 0;
        
        for(int i=0;i<tablero.length;i++){
            for(int j=0;j<tablero[i].length;j++){
                if(i == 0){
                    if(j == 0){tablero[i][j] = "  ";
                    }else{
                        if(contador <= 9){tablero[i][j] = Integer.toString(contador) + " ";
                        contador++;
                        
                        }else{tablero[i][j] = Integer.toString(contador);
                        contador++;}
}
                }else if( j == 0){
                    tablero[i][j] = letra_fila[contador2] + " ";
                    contador2++;
                
            }else{tablero[i][j]="- ";}
        }
        
    }
         
        return tablero;
    }
    
    
    
    public static String[][] tablero_oculto(int filas, int columnas, int modo){
        /*llama a la funcion tablero_visible, así crea otro tablero igual
        pero en este se insertan los barcos y
        se mantiene oculto*/
        String[][] tablero_oculto = tablero_visible(filas, columnas);
        insertar_barcos(tablero_oculto, modo);
         
        return tablero_oculto;
    }
    
    
    //=======================================
    // FUNCIONES DE BARCOS
    //=======================================
    public static void insertar_barcos(String tablero_oculto[][], int modo){
        /*Controla que barcos y cuantos barcos se insertan 
        segun el modo de juego elegido*/
        switch (modo) {
            case 1:
                insertar_lancha(tablero_oculto, 5);
                insertar_buque(tablero_oculto, 3);
                insertar_acorazado(tablero_oculto);
                insertar_portaaviones(tablero_oculto);
                break;
            case 2:
                insertar_lancha(tablero_oculto, 2);
                insertar_buque(tablero_oculto, 1);
                insertar_acorazado(tablero_oculto);
                insertar_portaaviones(tablero_oculto);
                break;
            case 3:
                insertar_lancha(tablero_oculto, 1);
                insertar_buque(tablero_oculto, 1);
                break;
            case 4:
                /*guardala lista de barcos para controlar cuantos barcos se insertan
                segun lo que haya elegido el usuario
                */
                int[] barcos = barcos_personalizado(tablero_oculto);
                insertar_lancha(tablero_oculto, barcos[0]);
                insertar_buque(tablero_oculto, barcos[1]);
                for(int i=0;i<barcos[2];i++){insertar_acorazado(tablero_oculto);}
                for(int i=0;i<barcos[3];i++){insertar_portaaviones(tablero_oculto);}
                break;
        }
    }
    
    
     public static void insertar_lancha(String[][] tablero, int veces){
         // Inserta una lancha en una posicion aleatoria del tablero oculto
            int contador = 0;
            while(contador != veces){
                int posicion_h = (int)(Math.random()* tablero.length);
                int posicion_v = (int)(Math.random()* tablero.length);
                while(posicion_h == 0 || posicion_v == 0){
                posicion_h = (int)(Math.random()* tablero.length);
                posicion_v = (int)(Math.random()* tablero.length);
            }
            if(tablero[posicion_v][posicion_h].equalsIgnoreCase("- ")){
                tablero[posicion_v][posicion_h]= "L ";
                contador++;
            }

    }
    }
    
    
    public static void insertar_buque(String[][] tablero, int veces){
        /* Inserta x veces buques en posiciones horizontales aleatorias del tablero oculto.
        Su longitud es de 3 casillas.
        el numero de veces dependen del modo de juego*/
        for(int b =1 ; b <= veces; b++){
            int contador = 0;
            int posicion_h = (int)(Math.random()* tablero.length);
            int posicion_v = (int)(Math.random()* tablero.length);
            int h = posicion_h;
        
            while(contador != 3){
                while(posicion_h == 0 || posicion_v == 0 || posicion_h+3 > tablero.length){
                    posicion_h = (int)(Math.random()* tablero.length);
                    posicion_v = (int)(Math.random()* tablero.length);
                    h = posicion_h; 

            }
                if(tablero[posicion_v][h].equalsIgnoreCase("- ")){
                    h++;
                    contador++;
                }else{
                    contador = 0;
                    posicion_h = (int)(Math.random()* tablero.length);
                    posicion_v = (int)(Math.random()* tablero.length);
                    h = posicion_h;

                }
            }
                for(int a = 1; a <= 3;a++){
                    tablero[posicion_v][posicion_h]= "B ";
                    posicion_h++;
                }
            }
    }
    
    public static void insertar_acorazado(String[][] tablero){
        /* Inserta x veces acorazado en posiciones horizontales aleatorias del tablero oculto.
        Su longitud es de 4 casillas.
        el numero de veces dependen del modo de juego*/
            int contador = 0;
            int posicion_h = (int)(Math.random()* tablero.length);
            int posicion_v = (int)(Math.random()* tablero.length);
            int h = posicion_h;
        
            while(contador != 4){
                while(posicion_h == 0 || posicion_v == 0 || posicion_h+4 > tablero.length){
                    posicion_h = (int)(Math.random()* tablero.length);
                    posicion_v = (int)(Math.random()* tablero.length);
                    h = posicion_h; 

            }
                if(tablero[posicion_v][h].equalsIgnoreCase("- ")){
                    h++;
                    contador++;
                }else{
                    contador = 0;
                    posicion_h = (int)(Math.random()* tablero.length);
                    posicion_v = (int)(Math.random()* tablero.length);
                    h = posicion_h;

                }
            }
                for(int a = 1; a <= 4;a++){
                    tablero[posicion_v][posicion_h]= "Z ";
                    posicion_h++;
                }
            
    }
    
    public static void insertar_portaaviones(String[][] tablero){
        /* Inserta x veces portaaviones en posiciones verticalmente aleatorias del tablero oculto.
        Su longitud es de 5 casillas.
        el numero de veces dependen del modo de juego*/
            int contador = 0;
            int posicion_h = (int)(Math.random()* tablero.length);
            int posicion_v = (int)(Math.random()* tablero.length);
            int v = posicion_v;
        
            while(contador != 5){
                while(posicion_h == 0 || posicion_v == 0 || posicion_v+5 > tablero.length){
                    posicion_h = (int)(Math.random()* tablero.length);
                    posicion_v = (int)(Math.random()* tablero.length);
                    v = posicion_v; 

            }
                if(tablero[v][posicion_h].equalsIgnoreCase("- ")){
                    v++;
                    contador++;
                }else{
                    contador = 0;
                    posicion_h = (int)(Math.random()* tablero.length);
                    posicion_v = (int)(Math.random()* tablero.length);
                    v = posicion_v;

                }
            }
                for(int a = 1; a <= 5;a++){
                    tablero[posicion_v][posicion_h]= "P ";
                    posicion_v++;
                }
            
    }
    
    
    
    //=======================================
    // FUNCIONES DE MODO PERSONALIZADO
    //=======================================
    public static int tamaño_personalizado(){
        //Permite al usuario escribir por pantalla un tamaño entre 5 y 26.
        System.out.println("Elige el tamaño del tablero, debe ser mayor que 5 y menor que 27.\n");
        System.out.println("EJEMPLO: Si lo quieres de 10x10, escribe solo '10' :  ");
        
        Scanner in = new Scanner(System.in);
        int tamaño = in.nextInt();
        while(tamaño>26 || tamaño<5){
        System.out.println(" Valor incorrecto, vuelve a intentarlo: ");
        tamaño = in.nextInt();}
        return tamaño;
    }
    
    
    
    public static int[] barcos_personalizado(String[][] tablero){
        /*Permite al usuario escribir por pantalla el numero de barcos 
        que desea insertar y controla que no ocupen mayos longitud que la 
        del tablero. Devuelve una lista con cada tipo de barco.
        */
        System.out.println(" \nCuantos barcos quieres insertar?.\n");
        System.out.println(" Lanchas: \n");
        Scanner in = new Scanner(System.in);
        int lanchas = in.nextInt();
        System.out.println(" Buques: \n");
        int buques = in.nextInt();
        System.out.println(" Acorazados: \n");
        int acorazados = in.nextInt();
        System.out.println(" Portaaviones: \n");
        int portaaviones = in.nextInt();
        
 
        while(lanchas+(buques*3)+(acorazados*4)+(portaaviones*5) > (tablero.length*tablero.length)-2){
            System.out.println("Son demasiados barcos, vuelve a intentarlo! ");
            System.out.println("\nLanchas: \n");
            lanchas = in.nextInt();
            System.out.println("\nBuques: \n");
            buques = in.nextInt();
            System.out.println("\nAcorazados: \n");
            acorazados = in.nextInt();
            System.out.println("\nPortaaviones: \n");
            portaaviones = in.nextInt(); }
        
        int barcos[] = {lanchas, buques, acorazados, portaaviones};
        return barcos;
    }
    
    
    
    public static int disparos_personalizado(){
        //Permite al usuario escribir por pantalla un numero de tiros o intentos.
        System.out.println(" \nNumero de intentos: \n");
        Scanner in = new Scanner(System.in);
        int intentos = in.nextInt();
        return intentos;
    }
    
    
    
    //=======================================
    // FUNCIONES DE JUEGO
    //=======================================
    public static void juega_partida(String tablero[][], String tablero_oculto[][], int modo, int intentos){
        mostrar_tablero(tablero);
    //Llama a realiza tiro con un numero de intentos diferente segun el 'modo' de juego que reciba por el parametro.
        switch (modo) {
            case 1:
                realiza_tiro(tablero, tablero_oculto, intentos);
                break;
            case 2:
                realiza_tiro(tablero, tablero_oculto, intentos);
                break;
            case 3:
                realiza_tiro(tablero, tablero_oculto, intentos);
                break;
            case 4:
                realiza_tiro(tablero, tablero_oculto, intentos);
                break;
        }
        
    }
    
    public static void realiza_tiro(String tablero[][], String tablero_oculto[][], int intentos){
        /*llama a todas las funciones de coordenadas, imprimiendo el tablero en cada tiro
        que haga el usuario mostrando el numero de la ronda y los cambios segun si se encontro A o X.*/
        int coordenadas[];
        int ronda = 1;
        boolean victoria;
        //intera el numero de intentos que reciba por parametro
        for(int i=0;i<intentos;i++){
            coordenadas = busca_coordenadas(tablero);
            System.out.println("\n\n== RONDA " + ronda +" ==");
            comprobar_tiro(tablero, tablero_oculto, coordenadas[0], coordenadas[1]);
            mostrar_tablero(tablero);
            ronda++;
            victoria = comprobar_victoria(tablero, tablero_oculto);
            //comprueba que devuelve la funcion comprobar_victoria, si es true gana y finaliza el bucle (y el juego), sino continua.
            if(victoria == true){
                System.out.println("\n\n== GANASTE!! ==");
                break;}}
        //al finalizar los intentos finaliza la partida y muestra el tablero oculto, asi el usuario verifica que le falto por hundir.
                System.out.println("\n\n== PARTIDA FINALIZADA ==");
                System.out.println("\nRESULTADO:");
                mostrar_tablero(tablero_oculto);
    }
    
    public static String[] pedir_coordenadas(String[][] tablero){
        //Permite al usuario escribir por pantalla la posicion donde desea disparar. Recibe la letra y luego el numero.
        System.out.println("\n\nEscribe una coordenada: (ejemplo -> letra: A, numero: 0 | letra: B, numero: 1): ");
        Scanner in = new Scanner(System.in);
        System.out.println("\nLetra: ");
        String letra = in.next().toUpperCase();       
        System.out.println("\nNumero: ");
        String numero = in.next();
        boolean letra_encontrada = comprueba_string(tablero, letra);
        
            //comprueba que el numero no sea mayor que la longitud horizontal o que la letra no exita, en caso de ser así pregunta de nuevo.
            while(isNumeric(numero) == false || isNumeric(letra) == true){
                System.out.println("\n\nCoordenada incorrecta, intentalo de nuevo: (ejemplo -> letra: A, numero: 0 | letra: B, numero: 1): ");
                System.out.println("\nLetra: ");
                letra = in.next().toUpperCase();
                System.out.println("\nNumero: ");
                numero = in.next();
                letra_encontrada = comprueba_string(tablero, letra);
        }
            while(Integer.parseInt(numero)>tablero.length-2 || letra_encontrada == false){
                    System.out.println("\n\nCoordenada incorrecta, intentalo de nuevo: (ejemplo -> letra: A, numero: 0 | letra: B, numero: 1): ");
                    System.out.println("\nLetra: ");
                    letra = in.next().toUpperCase();
                    System.out.println("\nNumero: ");
                    numero = in.next();
                    letra_encontrada = comprueba_string(tablero, letra);
                    }
        //devuelve una lista de Strings
        String coordenadas[] = {letra, numero};
        return coordenadas;
    }
    
    
    public static boolean comprueba_string(String tablero[][], String letra){
         //recorre el tablero en busca de la letra recibida
        String letra_espacio = letra + " ";
        boolean letra_encontrada = false;
        for(int i=0;i<tablero.length;i++){
                if(letra_espacio.equals(tablero[i][0])){
                   letra_encontrada = true;
            }}
        return letra_encontrada;
        } 
    
    
     public static boolean isNumeric(String str) { 
         //comprueba que el string que reciba sea digito, deuelve true si lo es, false si no lo es.
         return str.matches("-?\\d+(\\.\\d+)?");
     } 
    
    
    public static int[] busca_coordenadas(String tablero[][]){
        /*guarda en la variable posiciones la lista con las coordenadas
        convierte el numero el int.
        */
        String[] posiciones = pedir_coordenadas(tablero);
        String letra = posiciones[0];
        String letra_espacio = letra + " ";
        int numero = Integer.parseInt(posiciones[1]);
        int index_letra = 0;
        //busca la posicion vertical donde esta la letra y lo almacena en index_letra
        for(int i=0;i<tablero.length;i++){
                if(letra_espacio.equals(tablero[i][0])){
                    index_letra = i;
            }}
        
        //devuelve una lista con la posicion horizontal y vertical (x,y).
        int posicion[] = {index_letra,numero+1};
        return posicion;
    }
    
    public static void comprobar_tiro( String[][] tablero, String[][] tablero_oculto, int posicion_v, int posicion_h){
        //comprueba que en el tablero oculto haya algun barco, si es asi imprime X en la posicion del tablero visible, sino A.
        if(tablero_oculto[posicion_v][posicion_h].equals("- ")){
            System.out.print("\n-- AGUA --\n");
            tablero[posicion_v][posicion_h] = "A ";
        }else{
            System.out.print("\n-- ¡TOCADO! --\n");
            tablero[posicion_v][posicion_h] = "X ";  
        }

    }
    

     public static boolean comprobar_victoria(String[][] tablero, String[][] tablero_oculto){
        /*compara el tablero oculto con el tablero visible, verificando si en todas las coordenadas donde estan los barcos
        en el oculto, tambien estan puestas las X en el visible.*/
        int contador1 = 1;
        int contador2 = 1;
        boolean victoria = false;
        for(int i=1;i<tablero.length;i++){
            for(int j=1;j<tablero[i].length;j++){
                if(!(tablero_oculto[i][j].equals("- "))){
                    contador1++;
                    if(tablero[i][j].equals("X ")){
                        contador2++;
                    }
                }
            }
        }
        
        if(contador1 == contador2 ){
            victoria = true;
        }
        
        //devuelve true si se cumple y false si no.
        return victoria;
    }
     
    public static void mostrar_tablero(String[][] tablero){
        //recorre el tablero pasador por parametro y lo muestra.
        for(int i=0;i<tablero.length;i++){
            System.out.print("\n");
            for(int j=0;j<tablero[i].length;j++){
                System.out.print(tablero[i][j] + " ");
            }
        }
    }

    
  }
    
