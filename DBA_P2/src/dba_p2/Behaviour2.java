/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dba_p2;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


public class Behaviour2 extends CyclicBehaviour {
    public String name = "Rayo";
    public String descripcion = "El padre de rayo dicen que se fue a por tabaco usando la distancia euclídea en lugar de la Manhattan y nunca volvió a casa";
    protected MyAgent agente;

    Behaviour2(MyAgent a) {
        this.myAgent = agente = a;
    }

    @Override
    public void action() {
        agente.getEntorno().mostrarEstado();
        System.out.println("CONTADOR: " + agente.getContadorHeuristicaMayor() + " \n");
        mover();
        if (agente.getEntorno().agente_actual.equals(agente.getEntorno().destino)) {
            System.out.println("¡El agente ha alcanzado el objetivo!");
            agente.doDelete();
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private double calcularDistanciaEuclidiana(Nodo nodo1, Nodo nodo2) {
        int deltaX = nodo1.x - nodo2.x;
        int deltaY = nodo1.y - nodo2.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    // Funciona igual que Behaviour3 pero en caso de que haya 2 casillas con menor
    // distancia igual
    // se obtendrá la casilla que tenga menor distancia euclidea
    public void mover() {
        List<Nodo> adyacentes = agente.getEntorno().obtenerNodosAdyacentes();
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(Comparator
                .comparingInt((Nodo n) -> n.heu)
                .thenComparingDouble(n -> calcularDistanciaEuclidiana(n, agente.getEntorno().destino)));
        colaPrioridad.addAll(adyacentes);
        colaPrioridad.removeAll(agente.getVisitedNodos());

        Nodo siguienteNodo = colaPrioridad.poll();

        if (siguienteNodo != null && siguienteNodo.val != -1) {
            moverAlNodo(siguienteNodo);
        } else {
            regresarSobrePasos();
        }
    }

    private void regresarSobrePasos() {
        regresarSobrePasos(1);
    }

    private void regresarSobrePasos(int pasos) {
        System.out.println("REGRESAMOS sobre " + pasos + " pasos\n");
        for (int i = 0; i < pasos; i++) {
            Nodo ultimoNodoVisitado = agente.getVisitedNodos().get(agente.getVisitedNodos().size() - 2);
            agente.addVisitedNodo(0, agente.getVisitedNodos().get(agente.getVisitedNodos().size() - 1));
            agente.removeVisitedNodo(agente.getVisitedNodos().size() - 1);
            agente.setOrigen(ultimoNodoVisitado);
            agente.getEntorno().setAgenteActual(ultimoNodoVisitado);
            agente.plusIters();
            agente.getEntorno().mostrarEstado();
        }
        // agente.setVisitedNodos((ArrayList<Nodo>) visitedaux);
    }

    private void moverAlNodo(Nodo siguienteNodo) {
        agente.setOrigen(siguienteNodo);
        agente.getEntorno().setAgenteActual(siguienteNodo);
        agente.addVisitedNodo(siguienteNodo);
        agente.plusIters();
    }
}
