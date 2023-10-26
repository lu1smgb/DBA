package dba_p2;

import java.util.ArrayList;
import java.lang.String;

public class Mapa {
    
    private String nombre;
    private int num_filas;
    private int num_columnas;
    private ArrayList<ArrayList<Integer>> mapa;

    public Mapa(String nombre, int num_filas, int num_columnas) {

        this.nombre = nombre;
        this.num_filas = num_filas;
        this.num_columnas = num_columnas;
        this.mapa = new ArrayList<ArrayList<Integer>>();

        // Generamos el mapa completamente vacio
        for (int i=0; i < num_filas; i++) {
            mapa.add(new ArrayList<Integer>());
            ArrayList<Integer> fila = mapa.get(i);
            for (int j=0; j < num_columnas; j++) {
                fila.add(j, 0);
            }
        }

    }

    public void imprimir() {
        
        for (int i=0; i < num_filas; i++) {
            for (int j=0; j < num_columnas; j++) {
                System.out.print(mapa.get(i).get(j) + (j < num_columnas - 1 ? " " : "\n"));
            }
        }

    }

}
