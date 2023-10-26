/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dba_p2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

import dba_p2.Mapa;

/**
 *
 * @author ana
 */
public class mapReader {
    
    ArrayList<ArrayList<Integer>> matriz_mapa;
    
    int contador = 0;
    int num_filas = 0;
    int num_columnas = 0;
    
    Mapa mapa;
    
    public void read(String nombreArchivo){
        
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) { // lee la linea
                switch (contador) {
                    case 0:
                        {
                            // lee filas
                            char c = linea.charAt(0);
                            num_filas = Character.getNumericValue(c);
                            contador++;
                            break;
                        }
                    case 1:
                        {
                            // lee columnas
                            char c = linea.charAt(0);
                            num_columnas = Character.getNumericValue(c);
                            contador++;
                            break;
                        }
                    default:
                        for (int i = 0; i < num_columnas; i++) { // cada elemento de la linea
                            ArrayList<Integer> fila = new ArrayList<>();
                            
                            char c = linea.charAt(i);
                            int valor = Character.getNumericValue(c);
                            fila.add(valor); // lo añade a fila
                            
                            matriz_mapa.add(fila); // añade fila a la matriz
                        }   break;
                }
                
            } // fin while
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // construir mapa con lo leído
        mapa = new Mapa(num_filas, num_columnas, matriz_mapa);
    }
    
    Mapa getMapa(){
        return mapa;
    }
}
