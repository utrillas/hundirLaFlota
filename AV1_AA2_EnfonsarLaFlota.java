/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.util.Scanner;

/**
 *
 * @author CARMEN_UTRILLAS_GINER_1_DAM
 */
public class AV1_AA2_EnfonsarLaFlota{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner nivel = new Scanner(System.in);
        
        int opcio;
        int min = 1, max = 3;
        int files = 11, columnes = 11, llanxes = 0, vaixells = 0, cuirassats = 0, portaavions = 0, max_intents = 0;
        
        
        // TODO code application logic here

        /*Menu incial de pantalla, , son textos que solamente los imprimimos en pantalla */
        
        System.out.println("==== BENVINGUTS A AFONAR LA FLOTA ====");

        do {
            System.out.println("Nivells de dificultat:");
            System.out.println("1.Fàcil: 5 llanxes, 3 vaixells, 1 cuirassat i 1 portaavions (50 tiros)");
            System.out.println("2.Mitjà: 2 llanxes, 1 vaixell, 1 cuirassat y 1 portaavions (30 tiros)");
            System.out.println("3.Difícil: 1 llanxa y 1 vaixel (10 tiros)");
            System.out.println("Quín nivell tries ?");
            
            opcio= nivel.nextInt();
        /*Realitzarem un while amb un switch amb diferents opcions que ens portaran a cada nivell de dificultad del joc*/ 

        } while (demana_dades_entre_max_i_min(opcio, min, max)== false);
    
        switch(opcio){
        
            case 1:
                llanxes = 5;
                vaixells = 3;
                cuirassats = 1;
                portaavions = 1;
                max_intents = 50;
                break;    
            case 2:
                llanxes = 2;
                vaixells = 1;
                cuirassats = 1;
                portaavions = 1;
                max_intents = 30;
                break;    
            case 3:
                llanxes = 1;
                vaixells = 1;
                max_intents = 10;
                break;    
        }
        jugar_partida (files, columnes, llanxes, vaixells, cuirassats, portaavions, max_intents);

        /* Amb aquesta funció farem que comence el joc i inicializarem casi totes les funcions del joc*/    
    }   
    public static void jugar_partida(int files, int columnes, int llanxes, int vaixells, int cuirassats, int portaavions, int max_intents) {
    
        System.out.println("==== COMENÇA EL JOC ====");
        char ocean [][];
        ocean = crear_tauler ( files, columnes);
        inserir_barcos (ocean,  llanxes,  vaixells,  cuirassats,  portaavions, columnes, files);
        boolean veureTot=false;
        int intent[] = new int [2];

        /*Açí le diem al programa que si hi ha trets o barque de quasevol tipo, tindra que seguir demanat estes funciones */
        
        while(max_intents>0 && queden_barcos(ocean) == true){
            
            mostra_tauler (ocean, veureTot);
            System.out.println(" Et queden "+ max_intents+" intents");
            intent=demana_coordenades_tret(ocean);
            processa_tret(ocean, intent[0], intent[1]);
            max_intents--;
        }
        /*En cas contrarí, significará que el joc a finalitzat, y ens donorá una resposta segun siga.*/  
        veureTot=true;
        System.out.println("EL JOC HA FINALITZAT");
        if (queden_barcos (ocean) == true) {
            System.out.println("HAS PERDUT!!!"); 
        }else{
            System.out.println("HAS GUANYAT!!!");
        }
        mostra_tauler(ocean, veureTot);
        System.out.println("GRACIES PER JUGAR!!!");
    }
    
    
// FUNCIÓ: Crear tauler buit amb "-" en totes les poscions 
    public static char[][] crear_tauler(int files, int columnes) {
    
        char ocean[][]=new char[files][columnes];
        
        int abecedari_ini = 65;
        char abecedari = (char) abecedari_ini;
    /*Açí le direm que deu de crear en el tauler según les coordenades de i y j */  

        for (int i = 0; i < ocean.length; i++) {
            for (int j = 0; j < ocean[i].length; j++) {
                if (i==0 && j==0) {
                    ocean[i][j]=' ';
                }
                if (i==0 && j!=0) {
                    ocean[i][j]= String.valueOf(j-1).charAt(0);
                }
                if (i!=0 && j==0) {
                    ocean[i][j] = abecedari;
                    abecedari++;
                }
                if (i!=0 && j!=0) {
                    ocean[i][j]='-';
                }
            }     
        }
        return ocean;
    }
    // FUNCIÓ: Rep el tauler i dimensions de l'objecte i ens retorna una posició aleatòria 
    public static int[] coordenada_aleatoria(char[][] ocean, int mida, int columnes, int files) {
        int minim = 1, columna, fila;
        int coord []= new int [2];
     /*Açí ens intentará posar el barcos donam-nos coordenades aleatories, en el cas que no conseguixca posar-ho, te un contador de 100 vegades*/

        for (int i = 0; i < 100; i++) {
            int llarg = mida-1;
            columna =(int)(1 + Math.random() * (columnes-mida));
            fila =(int)(1 + Math.random() * (files-minim));
            for (int j = 0; j < mida - 1; j++) {
                if(ocean[fila][columna+j]=='-'){
                   llarg--; 
                }
                
            }
            if(llarg==0){
                coord [0] = fila;
                coord [1] = columna;
                break;
            }
        }
        return coord;
     }
// PROCEDIMENT: Mostra per pantalla el tauler
    public static void mostra_tauler(char[][] ocean, boolean veureTot) {
        /*Açí le estem dien al programa que deu de ensenyar-nos en comptes dels vaixells*/
        if (veureTot == false){
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                    if (i==0 || j == 0){
                        System.out.print(" " +ocean [i][j]);
                    } else{
                        if (ocean [i][j] == 'L' || ocean [i][j]== 'B' || ocean [i][j] == 'Z'|| ocean [i][j] == 'P'){
                            System.out.print(" -");
                        }else{
                            System.out.print(" " +ocean[i][j]);
                        }

                        }
                }
                System.out.println("");
            }
        }
        else {
             for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                    System.out.print(" "+ocean [i][j]);
                }
                System.out.println("");
            }
        } 
    }

// PROCEDIMENT: Inserir els barcos en posicions aleatòries 
    public static void inserir_barcos (char[][] ocean, int llanxes, int vaixells, int cuirassats, int portaavions, int columnes, int files) {
    /*Açí le diem al programa com te que posar el barcos, començan per el de mayor mida*/ 

        int coord []= new int [2];    
        int mida; 
        
        for (int i = 1; i <= portaavions; i++) {
            mida = 5;
            coord = coordenada_aleatoria ( ocean, mida, columnes, files);
            if (coord [0]==0 && coord [1]==0 ) {
                System.out.println("ERROR: No s'ha pogut inserir el barco");
            } else{
            
                for (int j = 0; j < mida; j++) {
                    ocean[ coord [0]][(coord[1]+j)]='P';   
                }   
            }
        }
        for (int i = 1; i <= cuirassats; i++) {
            mida = 4;
            coord = coordenada_aleatoria ( ocean, mida, columnes, files);
            if (coord [0]==0 && coord [1]==0 ) {
                System.out.println("ERROR: No s'ha pogut inserir el barco");
            } else{
            
                for (int j = 0; j < mida; j++) {
                ocean[ coord [0]][(coord[1]+j)]='Z';  
                }   
            }
        }
              
        for (int i = 1; i <= vaixells; i++) {
            mida = 3;
            coord = coordenada_aleatoria ( ocean, mida, columnes, files);
            if (coord [0]==0 && coord [1]==0 ) {
                System.out.println("ERROR: No s'ha pogut inserir el barco");
            } else{
            
               for (int j = 0; j < mida; j++) {
                ocean[ coord [0]][(coord[1]+j)]='B';  
                }

            }
        }

        for (int i = 1; i <= llanxes; i++) {
            mida = 1;
            coord = coordenada_aleatoria ( ocean, mida, columnes, files);
            if (coord [0]==0 && coord [1]==0 ) {
                System.out.println("ERROR: No s'ha pogut inserir el barco");
            } else{
            
              for (int j = 0; j < mida; j++) {
                ocean[ coord [0]][coord[1]+j]='L';  
                }

            }
        }
    
    }
 
 // FUNCIÓ: Demana coordenades de tret
    public static int[] demana_coordenades_tret(char[][] ocean) {
       
        Scanner entrada = new Scanner (System.in);
        int tiro[] = new int [2];
        int fila = -1;
        int columnes = -1;
        int files = 0;
    /*Açí demanarem les coordenades de cada tret, ademés, reconeix si se introduix una coordenada no vàlida*/

        do{
            System.out.println("Introduix una lletra entre la A y la J:");
            fila= String.valueOf(entrada.nextLine()).toUpperCase().charAt(0);
            files= fila-64;
            if (files <0 || files >10) {
                System.out.println("Lletra no válida, introdueix una lletra entre la A y la J");
            }
        } while(files<1 || files>10);
        tiro[0]=files;
        do{
            System.out.println(" Introduix un nombre entre 0 y 9");
            if(entrada.hasNextInt()){
                columnes= entrada.nextInt()+1;
            }else{
                System.out.println("Nombre no válid, introdueix un nombre entre 0 y 9");
                entrada.nextLine();
            }
        }while(columnes<0 || columnes>10);
        tiro[1]=columnes;
        System.out.println("");
        
        return tiro; 
    }
// PROCEDIMENT: Processem el tret amb les coordenades indicades 
    public static void processa_tret(char[][] ocean, int f, int c) {
/*Açí le diem que ens te que retornar despres de proposar el tret*/

        switch (ocean [f][c]){
            case '-':
                System.out.println("AGUA");
                ocean[f][c]='A';
                break;
            case 'A':
            case 'X':
                System.out.println("Error! ja hi havies fet un tret ací");
                break;
            case 'L':
            case 'B':
            case 'Z':
            case 'P':
                System.out.println("Tocat!");
                ocean[f][c]='X';
                break;
        }
    }            
// FUNCIÓ: Comprovar si quede barcos en el tauler 
    public static boolean queden_barcos(char[][] ocean) {
/* Açí le diem al programa que recorra el array i verifique si hi ha barcos*/

        boolean barcos = false;
            
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11;j++) {
             if (ocean[i][j]== 'L' || ocean[i][j]== 'B' || ocean[i][j]== 'Z' || ocean[i][j]== 'P'){
                 barcos = true;
             }   
                
            }
            
        }
        return barcos;
    }
// FUNCIÓ: Demana el valor indicat del menú a l'usuari i valida que siga correcte.
    public static boolean demana_dades_entre_max_i_min (int opcio, int min, int max) {
    
        boolean verificacio = true;
        
        if (opcio<min || opcio>max) {
            verificacio = false;
            System.out.println("Opció no válida, introduir un nombre, 1 , 2 o 3 ");
        }
        return verificacio;
    }
    

     
}
        

       
        


