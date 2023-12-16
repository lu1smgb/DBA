/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dba_p2;

import java.util.ArrayList;
import java.util.List;

public class Entorno {
    private Nodo[][] mapa_heu;
    Nodo destino;
    Nodo agente_actual;
    private int cont;

    public Entorno(int[][] mapa, int ox, int oy, int dx, int dy) {
        destino = new Nodo(dx, dy, 0, true);
        mapa_heu = new Nodo[mapa.length][mapa[0].length];
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                mapa_heu[i][j] = new Nodo(i, j, mapa[i][j], false);
                mapa_heu[i][j].setHeu(destino);
            }
        }
        agente_actual = new Nodo(ox, oy, 0, false);
        agente_actual.setHeu(destino);
    }

    public List<Nodo> obtenerNodosAdyacentes() {
        List<Nodo> adyacentes = new ArrayList<>();

        // Nodo arriba
        Nodo nodoArriba = obtenerNodo(agente_actual.x - 1, agente_actual.y);
        if (nodoArriba != null) {
            adyacentes.add(nodoArriba);
        }

        // Nodo abajo
        Nodo nodoAbajo = obtenerNodo(agente_actual.x + 1, agente_actual.y);
        if (nodoAbajo != null) {
            adyacentes.add(nodoAbajo);
        }

        // Nodo izquierda
        Nodo nodoIzquierda = obtenerNodo(agente_actual.x, agente_actual.y - 1);
        if (nodoIzquierda != null) {
            adyacentes.add(nodoIzquierda);
        }

        // Nodo derecha
        Nodo nodoDerecha = obtenerNodo(agente_actual.x, agente_actual.y + 1);
        if (nodoDerecha != null) {
            adyacentes.add(nodoDerecha);
        }

        return adyacentes;
    }

    private Nodo obtenerNodo(int x, int y) {
        // Verifica si las coordenadas están dentro de los límites del mapa
        if (x >= 0 && x < mapa_heu.length && y >= 0 && y < mapa_heu[0].length) {
            return mapa_heu[x][y];
        } else {
            // Si las coordenadas están fuera de los límites, devuelve null
            return null;
        }
    }

    public void setAgenteActual(Nodo nuevoAgente) {
        // Verifica si las nuevas coordenadas están dentro de los límites del mapa
        if (nuevoAgente.x >= 0 && nuevoAgente.x < mapa_heu.length
                && nuevoAgente.y >= 0 && nuevoAgente.y < mapa_heu[0].length) {
            agente_actual = nuevoAgente;
        } else {
            // Manejo de error: las coordenadas están fuera de los límites, podrías lanzar
            // una excepción o manejarlo de otra manera según tus necesidades
            System.out.println("Error: Las nuevas coordenadas del agente están fuera de los límites del mapa.");
        }
    }

    public int getTamEntorno() {
        if (mapa_heu.length >= mapa_heu[0].length) {
            return mapa_heu.length;
        } else {
            return mapa_heu[0].length;
        }
    }

    public void mostrarEstado() {
        // Muestra la posición del agente, la del objetivo y el mapa en la terminal
        System.out.println("Posición del agente: (" + agente_actual.x + ", " + agente_actual.y + ")");
        System.out.println("Posición del objetivo: (" + destino.x + ", " + destino.y + ")");
        System.out.println("Estado del mapa:");
        for (int i = 0; i < mapa_heu.length; i++) {
            for (int j = 0; j < mapa_heu[0].length; j++) {
                if (agente_actual.x == i && agente_actual.y == j) {
                    System.out.print("A "); // Marca la posición del agente
                } else if (destino.x == i && destino.y == j) {
                    System.out.print("D "); // Marca la posición del objetivo
                } else {
                    if (mapa_heu[i][j].val != -1) {
                        System.out.print(mapa_heu[i][j].val + " ");
                    } else {
                        System.out.print("X" + " ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println("-------------");
        cont++;
        System.out.println("PASOS: " + cont + "\n");
    }

}
