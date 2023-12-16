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

public class Behaviour3 extends CyclicBehaviour {
    public String name = "Rayito";
    public String descripcion = "Intenta ir de casilla en casilla a la siguiente que esté más cerca aunque a veces se pierde sabe volver sobre sus pasos.";
    protected MyAgent agente;

    Behaviour3(MyAgent a) {
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

    // Nos desplazamos al nodo con menor distancia manhattan
    public void mover() {
        List<Nodo> adyacentes = agente.getEntorno().obtenerNodosAdyacentes();
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(Comparator
                .comparingInt((Nodo n) -> n.heu));
        colaPrioridad.addAll(adyacentes);
        colaPrioridad.removeAll(agente.getVisitedNodos());

        Nodo siguienteNodo = colaPrioridad.poll();

        if (siguienteNodo != null && siguienteNodo.val != -1) {
            moverAlNodo(siguienteNodo);
            // En caso de que no queden pasos disponibles (todos visitados o muros)
            // retrocedemos en pasos hasta llegar
            // a una casilla libre (siguienteNodo dejará de ser null)
        } else {
            regresarSobrePasos();
        }
    }

    private void regresarSobrePasos() {
        regresarSobrePasos(1);
    }

    // Regresamos un paso atrás utilizando VisitedNodos
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
    }

    // Función encargada de actualizar los valores del agente y el entorno
    private void moverAlNodo(Nodo siguienteNodo) {
        agente.setOrigen(siguienteNodo);
        agente.getEntorno().setAgenteActual(siguienteNodo);
        agente.addVisitedNodo(siguienteNodo);
        agente.plusIters();
    }
}
