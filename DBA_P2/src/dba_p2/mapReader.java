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
    
    public void read(String nombreArchivo){
        
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
             while ((linea = br.readLine()) != null) { // lee la linea
                ArrayList<Integer> fila = new ArrayList<>();
                for (int i = 0; i < linea.length(); i++) { // cada elemento de la linea
                    char c = linea.charAt(i);
                    int valor = Character.getNumericValue(c);
                    fila.add(valor); // lo añade a fila
                }
                matriz_mapa.add(fila); // añade fila a la matriz
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // construir mapa con lo leído
        Mapa mapa = new Mapa(matriz_mapa);
        
        return mapa;
    }
}
