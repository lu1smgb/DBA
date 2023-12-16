/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dba_p2;

public class Nodo {
    int x;
    int y;
    int val;
    int heu;

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
    public void setHeu(Nodo dest){
        heu = Math.abs(x - dest.x) + Math.abs(y - dest.y);
        if(val == -1){
            heu = 999999;
        }
    }
    
    public boolean equals(Nodo n){
        if(x == n.x && y == n.y){
            return true;
        }
        return false;
    }
}
