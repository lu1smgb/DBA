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

public class Behaviour0 extends CyclicBehaviour {
    public String name = "Reglitas americano";
    public String descripcion = "Como Reglitas pero americano (solo usa la Manhattan).";
    protected MyAgent agente;

    Behaviour0(MyAgent a) {
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

    // Exactamente igual que Behaviour 0 pero no tiene en cuenta la distancia
    // euclidea para elegir
    // el siguiente nodo
    public void mover() {
        List<Nodo> adyacentes = agente.getEntorno().obtenerNodosAdyacentes();
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(Comparator
                .comparingInt((Nodo n) -> n.heu));
        colaPrioridad.addAll(adyacentes);
        colaPrioridad.removeAll(agente.getVisitedNodos());

        Nodo siguienteNodo = colaPrioridad.poll();

        if (siguienteNodo != null && siguienteNodo.val != -1 && !agente.getVisitedNodos().contains(siguienteNodo)) {
            if (!agente.isCuenta()) {
                if (siguienteNodo.heu > agente.getOrigen().heu) {
                    agente.setCuenta(true);
                    agente.setMayores(agente.getContadorHeuristicaMayor() + 1);
                    int pasos = agente.getContadorHeuristicaMayor();
                    moverAlNodo(siguienteNodo);

                    if (agente.isCuenta() && pasos >= (agente.getEntorno().getTamEntorno()) * 2 - 2) {
                        System.out.println("Volver sobre " + pasos + " pasos");
                        regresarSobrePasos(pasos);
                        agente.setCuenta(false);
                        agente.setMayores(0);
                    }
                } else {
                    moverAlNodo(siguienteNodo);
                }
            } else {
                if (siguienteNodo.heu < agente.getOrigen().heu) {
                    agente.setMenores(agente.getMenores() + 1);
                }
                if (agente.getMenores() < 10) {
                    agente.setMayores(agente.getContadorHeuristicaMayor() + 1);
                    int pasos = agente.getContadorHeuristicaMayor();
                    moverAlNodo(siguienteNodo);

                    if (agente.isCuenta() && pasos >= (agente.getEntorno().getTamEntorno() * 2) - 2) {
                        System.out.println("Volver sobre " + pasos + " pasos");
                        regresarSobrePasos(pasos);
                        agente.setCuenta(false);
                        agente.setMayores(0);
                        agente.setMenores(0);
                    }
                } else {
                    agente.setCuenta(false);
                    agente.setMayores(0);
                    agente.setMenores(0);
                }
            }
        } else {
            regresarSobrePasos();
            agente.setMayores(0);
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
    }

    private void moverAlNodo(Nodo siguienteNodo) {
        agente.setOrigen(siguienteNodo);
        agente.getEntorno().setAgenteActual(siguienteNodo);
        agente.addVisitedNodo(siguienteNodo);
        agente.plusIters();
    }
}
