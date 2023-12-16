package dba_p2;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public class ComportamientoManhattan extends ComportamientoAgente {
    
    protected Deque<Movimiento> followedPathMovimientos;
    protected Deque<Coordinates> followedPath;
    protected ArrayList<Coordinates> visitedCoordinates;

    ComportamientoManhattan(NuevoAgente agente) {
        super(agente);
        this.followedPathMovimientos = new LinkedList<>();
        this.followedPath = new LinkedList<>();
        this.visitedCoordinates = new ArrayList<>();
    }

    private ArrayList<Coordinates> obtenerNoVisitados() {
        ArrayList<Coordinates> noVisitados = new ArrayList<>();
        // Itera en las direcciones especificadas en Movimiento
        for (Coordinates c : this.agente.getPercepcion()) {

            if (c != null) {
                Celda tipoCelda = this.agente.getEntorno().getElement(c);
                if (!visitedCoordinates.contains(c) && tipoCelda != Celda.WALL) {
                    noVisitados.add(c);
                }
                else {
                    noVisitados.add(null);
                }
            }
            else {
                noVisitados.add(null);
            }
        }
        return noVisitados;
    }

    // moverNoVisitados
    private Movimiento moverNoVisitados(ArrayList<Coordinates> noVisitados) {
        ////Random random = new Random();
        Movimiento proxima = null;
        ////int indiceMovimiento = 0;
        if (!noVisitados.isEmpty()) {
            double minimo = Double.MAX_VALUE;
            double aux = 0;
            Movimiento movimiento_minimo = null;
            for (Movimiento m : Movimiento.values()) {
                Coordinates coordenada = noVisitados.get(m.value());
                if (coordenada != null) {
                    aux = calculaHeuristica(coordenada);
                    if (aux < minimo) {
                        minimo = aux;
                        movimiento_minimo = m;
                    }
                }
            }
            proxima = movimiento_minimo;
        } 
        else if (!visitedCoordinates.isEmpty()) {
            switch (followedPathMovimientos.getLast()) {
                case IZQUIERDA:
                    proxima = Movimiento.DERECHA;
                    break;
                case DERECHA:
                    proxima = Movimiento.IZQUIERDA;
                    break;
                case ARRIBA:
                    proxima = Movimiento.ABAJO;
                    break;
                case ABAJO:
                    proxima = Movimiento.ARRIBA;
                    break;
                default:
                    break;
            }
        }
        return proxima;
    }

    private Movimiento mover(Movimiento movimiento) {
        Coordinates localCoordinate = this.agente.getEntorno().getPosicionAgente();
        if (!visitedCoordinates.contains(localCoordinate)) {
            visitedCoordinates.add(localCoordinate);
        }

        if (obtenerNoVisitados().isEmpty()) {
            movimiento = followedPathMovimientos.element();
            followedPathMovimientos.pop();
            followedPath.pop();
        }
        else {
            if (!followedPath.contains(localCoordinate)) {
                followedPathMovimientos.add(movimiento);
                followedPath.add(localCoordinate);
            }
        }
        return movimiento;
    }

    private double calculaHeuristica(Coordinates proxima) {
        Coordinates objectiveCoordinate = this.agente.getEntorno().getPosicionObjetivo();
        double distancia = Math.abs(objectiveCoordinate.x - proxima.x) + Math.abs(objectiveCoordinate.y - proxima.y);
        return distancia;
    }

    @Override
    protected Movimiento decidirMovimiento() {

        return mover(moverNoVisitados(obtenerNoVisitados()));

    }

}
