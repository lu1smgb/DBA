/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PRACTICA3_DBA;

public class Nodo {
    int x;
    int y;
    int val; //valor en el mapa 0 o -1
    int heu; //distancia manhattan a destino del nodo

    //El valor correspondiente a la casilla (muro o libre) las coordenadas y si es la casilla destino o no
    public Nodo(int a, int b, int v, boolean destino){
        x = a;
        y = b;
        val = v;
        if(destino){
            heu = 0;
        }
        else{
            heu = 9999999; 
        }

    }

    //Establece la distancia manhattan al destino
    public void setHeu(Nodo dest){ //si la casilla es un muro
        heu = Math.abs(x - dest.x) + Math.abs(y - dest.y);
        if(val == -1){
            heu = 999999; // la heu infinita osea no pasa
        }
    }
    
    public boolean equals(Nodo n){ //comparador de nodos
        if(x == n.x && y == n.y){
            return true;
        }
        return false;
    }
}
