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

public class Behaviour1 extends CyclicBehaviour {
    public String name = "Reglitas";
    public String descripcion = "Siempre encontrará el camino, cuanto más compleja la situación menos tardará aunque es cierto que a veces le gusta complicarse la vida.";
    protected MyAgent agente;

    Behaviour1(MyAgent a) {
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

    // Funciona como behaviour2 pero tiene en cuenta cuando se pasa a una casilla
    // que tiene mayor distancia que en la que estabamos
    // Es decir, el agente ha encontrado un muro donde estaría la casilla con menor
    // distancia
    // En caso de que recorramos (n * 2) - 2 siendo n la mayor entre las filas y las
    // columnas del entorno
    // ese valor es el máximo de pasos en un mapa sin óbtaculos desde una esquina a
    // otra consideramos que el camino no es el correcto
    // y retrocedemos (n * 2) - 2 para coger la otra casilla que habría disponible
    // ya que tiene más posibilidades de ser el camino
    // correcto. También en el caso de que mientras contamos desde que llegamos a
    // una mayor pisamos n veces casillas con menor distancia
    // podría significar que sí que estamos en un camino correcto luego reiniciamos
    // el contador de pisar mayores a 0 para que siga por
    // ese camino
    public void mover() {
        List<Nodo> adyacentes = agente.getEntorno().obtenerNodosAdyacentes();
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(Comparator
                .comparingInt((Nodo n) -> n.heu)
                .thenComparingDouble(n -> calcularDistanciaEuclidiana(n, agente.getEntorno().destino)));
        colaPrioridad.addAll(adyacentes);
        colaPrioridad.removeAll(agente.getVisitedNodos());

        Nodo siguienteNodo = colaPrioridad.poll();

        if (siguienteNodo != null && siguienteNodo.val != -1 && !agente.getVisitedNodos().contains(siguienteNodo)) {
            if (!agente.isCuenta()) { // No estamos contando
                if (siguienteNodo.heu > agente.getOrigen().heu) { // Pasamos a una casilla con mayor heu
                    agente.setCuenta(true); // Comenzamos a contar
                    agente.setMayores(agente.getContadorHeuristicaMayor() + 1);
                    int pasos = agente.getContadorHeuristicaMayor();
                    moverAlNodo(siguienteNodo);

                    if (agente.isCuenta() && pasos >= (agente.getEntorno().getTamEntorno()) * 2 - 2) { // Llegamos al
                                                                                                       // umbral
                        System.out.println("Volver sobre " + pasos + " pasos"); // Volvemos sobre nuestros pasos
                        regresarSobrePasos(pasos);
                        agente.setCuenta(false);
                        agente.setMayores(0);
                    }
                } else {
                    moverAlNodo(siguienteNodo);
                }
            } else { // Si estamos contando
                if (siguienteNodo.heu < agente.getOrigen().heu) { // Contamos cuantas veces caemos en menores heu
                    agente.setMenores(agente.getMenores() + 1);
                }
                if (agente.getMenores() < agente.getEntorno().getTamEntorno()) { // Si llegamos al umbral de menores
                    agente.setMayores(agente.getContadorHeuristicaMayor() + 1); // Reiniciamos la cuenta de mayores
                    int pasos = agente.getContadorHeuristicaMayor();
                    moverAlNodo(siguienteNodo);

                    if (agente.isCuenta() && pasos >= (agente.getEntorno().getTamEntorno() * 2) - 2) { // Si llegamos al
                                                                                                       // umbral de
                                                                                                       // mayores
                        System.out.println("Volver sobre " + pasos + " pasos"); // Volvemos sobre nuestros pasos
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
        } else { // No hay movimientos disponibles
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
