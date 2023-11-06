
package dba_p2;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPANames;
import java.util.Random;
import dba_p2.TableroSwing;
import jade.core.AID;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import dba_p2.Coordinates;

public class MyJadeAgent extends Agent {
    private int x; // Posición X actual del agente en la matriz
    private int y; // Posición Y actual del agente en la matriz
    private Deque<Coordinates> followedPath = new LinkedList<>();
    
    // nuevo
    private ArrayList<Coordinates> visitedCoordinates; // lista de visitados
    private Coordinates localCoordinate; // donde esta el agente
    

    // Referencia a la interfaz gráfica Swing
    private TableroSwing tablero;

    protected void setup() {
        // Inicializa la posición del agente (ajusta según tus necesidades)
        //x = 0;
        //y = 0;
        
        // nuevo
        visitedCoordinates = new ArrayList<>();
        localCoordinate = new Coordinates();
        
        //visitedCoordinates.add(localCoordinate);
        //visitedCoordinates.add(localCoordinate);

        // Inicializa la referencia a la interfaz gráfica
        tablero = new TableroSwing(new int[][]{
            {0, 0, 0, -1, 0, -1, 0},
            {0, 0, -1, 0, 0, -1, 0},
            {0, 0, 0, 0, 0, -1, 0},
            {0, 0, 0, 0, 0, -1, 0},
            {0, 0, 0, -1, 0, -1, 0},
            {0, 0, -1, 0, 0, -1, 0},
            {0, 0, 0, 0, 0, -1, 0},
            {0, 0, 0, 0, 0, -1, 0}
        });

        // Comportamiento para manejar las actualizaciones de posición
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                // Simula el movimiento del agente
                //moverAgenteAleatoriamente();
                mover(moverNoVisitados(obtenerNoVisitados()));

                // Actualiza la posición del agente en la interfaz gráfica
                //tablero.setAgentePosition(x, y);
                
                for(int i = 0; i<visitedCoordinates.size(); i++){
                    System.out.println("(i="+i+") --> x=" + visitedCoordinates.get(i).x + ", y=" + visitedCoordinates.get(i).y);
                }

                // Envia un mensaje a la interfaz gráfica con la nueva posición (opcional)
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.setContent("Nueva posición del agente: " + localCoordinate.x + ", " + localCoordinate.y);
                message.addReceiver(new AID("InterfazGrafica", AID.ISLOCALNAME));
                send(message);

                // Pausa para simular un intervalo de tiempo entre movimientos
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Simula el movimiento del agente de forma aleatoria
    private void moverAgenteAleatoriamente() {
        Random random = new Random();
        int[] posiblesMovimientosX = {1, -1, 0, 0};
        int[] posiblesMovimientosY = {0, 0, 1, -1};
        int indiceMovimiento = random.nextInt(4);

        int nuevoX = x + posiblesMovimientosX[indiceMovimiento];
        int nuevoY = y + posiblesMovimientosY[indiceMovimiento];

        // Asegúrate de que el agente no se salga de los límites de la matriz (ajusta según el tamaño de tu matriz)
        if (nuevoX >= 0 && nuevoX < tablero.getMatriz()[0].length && nuevoY >= 0 && nuevoY < tablero.getMatriz().length) {
            x = nuevoX;
            y = nuevoY;
        }
         System.out.println(x);
         System.out.println(y);
    }
    
    private ArrayList<Coordinates> obtenerNoVisitados(){
        ArrayList<Coordinates> noVisitados = new ArrayList<>();
        Coordinates arriba = new Coordinates(localCoordinate.x, localCoordinate.y-1);
        Coordinates abajo = new Coordinates(localCoordinate.x, localCoordinate.y+1);
        Coordinates izquierda = new Coordinates(localCoordinate.x-1, localCoordinate.y);
        Coordinates derecha = new Coordinates(localCoordinate.x+1, localCoordinate.y);
        
        /* Debería funcionar, descomentar este y comentar el otro
        if(!visitedCoordinates.contains(arriba) && arriba.y >= 0){
            noVisitados.add(arriba);
        }
        if(!visitedCoordinates.contains(abajo)){
            noVisitados.add(abajo);
        }
        if(!visitedCoordinates.contains(izquierda) && izquierda.x >= 0){
            noVisitados.add(izquierda);
        }
        if(!visitedCoordinates.contains(derecha)){
            noVisitados.add(derecha);
        }*/
        
        
        //Para saber si es un obstaculo o no, si no funciona comentar y descomentar el de abajo
        if(!visitedCoordinates.contains(arriba) && arriba.y >= 0 && tablero.getMatriz()[arriba.y][arriba.x] != -1){
            noVisitados.add(arriba);
        }
        if(!visitedCoordinates.contains(abajo) && abajo.y < tablero.getMatriz().length && tablero.getMatriz()[abajo.y][abajo.x] != -1){
            noVisitados.add(abajo);
        }
        if(!visitedCoordinates.contains(izquierda) && izquierda.x >= 0 && tablero.getMatriz()[izquierda.y][izquierda.x] != -1){
            noVisitados.add(izquierda);
        }
        if(!visitedCoordinates.contains(derecha) && derecha.x < tablero.getMatriz()[0].length && tablero.getMatriz()[derecha.y][derecha.x] != -1){
            noVisitados.add(derecha);
        }
        
        /*
        if(!contieneCoordenada(arriba) && arriba.y >= 0){
            noVisitados.add(arriba);
        }
        if(!contieneCoordenada(abajo)){
            noVisitados.add(abajo);
        }
        if(!contieneCoordenada(izquierda) && izquierda.x >= 0){
            noVisitados.add(izquierda);
        }
        if(!contieneCoordenada(derecha)){
            noVisitados.add(derecha);
        }
        */
        
        return noVisitados;
    }
    
    private Coordinates moverNoVisitados(ArrayList<Coordinates> noVisitados){
        Random random = new Random();
        
        int indiceMovimiento = random.nextInt(noVisitados.size());
        
        return noVisitados.get(indiceMovimiento);
    }
    
    private void mover(Coordinates coordenada){
        visitedCoordinates.add(localCoordinate);
        //Si no podemos movernos a ningun sitio retrocedemos
        
        if (obtenerNoVisitados().size() == 0){
            followedPath.pop();
            coordenada = followedPath.element();
        }
        else{
            if (!followedPath.contains(localCoordinate){
                followedPath.push(localCoordinate);
            }
        }
        
        tablero.setAgentePosition(coordenada.x, coordenada.y);
        localCoordinate = coordenada;
        
    }
    
    private boolean contieneCoordenada(Coordinates coordenada){
        for(int i=0; i<visitedCoordinates.size(); i++){
            if(coordenada.x == visitedCoordinates.get(i).x && coordenada.y == visitedCoordinates.get(i).y){
                return true;
            }
        }
            // mejor implementar equal
        return false;
    }
}
