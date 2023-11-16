
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
import jade.core.behaviours.OneShotBehaviour;

public class MyJadeAgent extends Agent {
    private int x; // Posición X actual del agente en la matriz
    private int y; // Posición Y actual del agente en la matriz
    private Deque<Coordinates> followedPath = new LinkedList<>();
    
    // nuevo
    private ArrayList<Coordinates> visitedCoordinates; // lista de visitados
    private Coordinates localCoordinate; // donde esta el agente
    private Coordinates objectiveCoordinate;
    
    // variables comportamientos
    //ArrayList<Coordinates> noVisitados;
    //private Coordinates coordenada;
    

    // Referencia a la interfaz gráfica Swing
    private TableroSwing tablero;

    protected void setup() {
        // Inicializa la posición del agente (ajusta según tus necesidades)
        //x = 0;
        //y = 0;
        
        // nuevo
        visitedCoordinates = new ArrayList<>();
        localCoordinate = new Coordinates();
        objectiveCoordinate = new Coordinates(7, 6);
        //visitedCoordinates.add(localCoordinate);
        //visitedCoordinates.add(localCoordinate);
        
        // comportamientos
        //noVisitados = new ArrayList<>();

        // Inicializa la referencia a la interfaz gráfica
        tablero = new TableroSwing(new int[][]{
            {0, 0, 0, -1, 0, 0, 0},
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
                
                mover(moverNoVisitados(obtenerNoVisitados()));
                
                //addBehaviour(new ObtenerNoVisitados());  
                //addBehaviour(new MoverNoVisitados());  
                //addBehaviour(new Movimiento());

                // Actualiza la posición del agente en la interfaz gráfica
                //tablero.setAgentePosition(x, y);
                
                for(int i = 0; i<visitedCoordinates.size(); i++){
                    System.out.println("(i="+i+") --> x=" + visitedCoordinates.get(i).x + ", y=" + visitedCoordinates.get(i).y);
                }
                
                // Envia un mensaje a la interfaz gráfica con la nueva posición (opcional)
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.setContent("Nueva posición del agente: " + localCoordinate.x + ", " + localCoordinate.y);
                message.addReceiver(new AID("IcoordenadanterfazGrafica", AID.ISLOCALNAME));
                send(message);

                // Pausa para simular un intervalo de tiempo entre movimientos
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public class ObtenerNoVisitados extends OneShotBehaviour {
        ArrayList<Coordinates> noVisitados;
        
        public ObtenerNoVisitados(){
            this.noVisitados = new ArrayList<>();
        }
        
        @Override
        public void action() {
            //ArrayList<Coordinates> noVisitados = new ArrayList<>();
            Coordinates arriba = new Coordinates(localCoordinate.x, localCoordinate.y-1);
            Coordinates abajo = new Coordinates(localCoordinate.x, localCoordinate.y+1);
            Coordinates izquierda = new Coordinates(localCoordinate.x-1, localCoordinate.y);
            Coordinates derecha = new Coordinates(localCoordinate.x+1, localCoordinate.y);

            //Para saber si es un obstaculo o no, si no funciona comentar y descomentar el de abajo
            if(!visitedCoordinates.contains(arriba) && arriba.y >= 0 && tablero.getMatriz()[arriba.y][arriba.x] != -1){
                this.noVisitados.add(arriba);
            }
            if(!visitedCoordinates.contains(abajo) && abajo.y < tablero.getMatriz().length && tablero.getMatriz()[abajo.y][abajo.x] != -1){
                this.noVisitados.add(abajo);
            }
            if(!visitedCoordinates.contains(izquierda) && izquierda.x >= 0 && tablero.getMatriz()[izquierda.y][izquierda.x] != -1){
                this.noVisitados.add(izquierda);
            }
            if(!visitedCoordinates.contains(derecha) && derecha.x < tablero.getMatriz()[0].length && tablero.getMatriz()[derecha.y][derecha.x] != -1){
                this.noVisitados.add(derecha);
            }
        }
        
        public int onEnd() {
            addBehaviour(new MoverNoVisitados (this.noVisitados));
            
            return super.onEnd();
        }
        
       
    }
    
    public class MoverNoVisitados extends OneShotBehaviour {
        ArrayList<Coordinates> noVisitados;
        Coordinates coordenada;
        public MoverNoVisitados(ArrayList<Coordinates> noVisitados){
            this.noVisitados = noVisitados;
        }
        
        @Override
        public void action() {
            Random random = new Random();
            Coordinates proxima = new Coordinates();
            int indiceMovimiento = 0; 
            if (!this.noVisitados.isEmpty()){
                //movimiento random
                /*
                indiceMovimiento = random.nextInt(noVisitados.size());
                proxima = noVisitados.get(indiceMovimiento);
                */
                double minimo = Double.MAX_VALUE;
                double aux = 0;
                int indice_minimo = 0;
                for (int i = 0; i < this.noVisitados.size(); ++i){
                    aux = calculaHeuristica(this.noVisitados.get(i));

                    if (aux < minimo /*&& noVisitados.get(i).visitados <= vecesVisitadas*/){
                        minimo = aux;
                        indice_minimo = i;
                        //vecesVisitadas = noVisitados.get(i).visitados;
                    }
                }
                proxima = this.noVisitados.get(indice_minimo);
            }
            else if (!visitedCoordinates.isEmpty()){
                Coordinates aux = new Coordinates(visitedCoordinates.get(visitedCoordinates.size() - 1).x, visitedCoordinates.get(visitedCoordinates.size() - 1).y);
                proxima = aux;
            }

            //return proxima;
            
            this.coordenada = proxima;
        }
        
        public int onEnd() {
            addBehaviour(new Movimiento (this.coordenada, this.noVisitados));
            
            return super.onEnd();
        }
    }
    
    public class Movimiento extends OneShotBehaviour {
        Coordinates coordenada;
        ArrayList<Coordinates> noVisitados;
        
        public Movimiento(Coordinates coordenada, ArrayList<Coordinates> noVisitados){
            this.coordenada = coordenada;
            this.noVisitados = noVisitados;
        }
        @Override
        public void action() {
            // coordenada es el parametro de entrada
            if (!visitedCoordinates.contains(localCoordinate)){
                visitedCoordinates.add(localCoordinate);
            }
            //visitedCoordinates.get(visitedCoordinates.size() - 1).visitados += 1;
            //Si no podemos movernos a ningun sitio retrocedemos

            if (noVisitados.isEmpty()){
                this.coordenada = followedPath.element();
                followedPath.pop();
            }
            else{
                if (!followedPath.contains(localCoordinate)){
                    followedPath.push(localCoordinate);
                }
            }

            tablero.setAgentePosition(this.coordenada.x, this.coordenada.y);
            localCoordinate = this.coordenada;
        }
    }
    
    
    
    /// MÉTODOS

    private ArrayList<Coordinates> obtenerNoVisitados(){
        ArrayList<Coordinates> noVisitados = new ArrayList<>();
        Coordinates arriba = new Coordinates(localCoordinate.x, localCoordinate.y-1);
        Coordinates abajo = new Coordinates(localCoordinate.x, localCoordinate.y+1);
        Coordinates izquierda = new Coordinates(localCoordinate.x-1, localCoordinate.y);
        Coordinates derecha = new Coordinates(localCoordinate.x+1, localCoordinate.y);
        
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
        Coordinates proxima = new Coordinates();
        int indiceMovimiento = 0; 
        if (!noVisitados.isEmpty()){
            //movimiento random
            /*
            indiceMovimiento = random.nextInt(noVisitados.size());
            proxima = noVisitados.get(indiceMovimiento);
            */
            double minimo = Double.MAX_VALUE;
            double aux = 0;
            int indice_minimo = 0;
            for (int i = 0; i < noVisitados.size(); ++i){
                aux = calculaHeuristica(noVisitados.get(i));
                
                if (aux < minimo /*&& noVisitados.get(i).visitados <= vecesVisitadas*/){
                    minimo = aux;
                    indice_minimo = i;
                    //vecesVisitadas = noVisitados.get(i).visitados;
                }
            }
            proxima = noVisitados.get(indice_minimo);
        }
        else if (!visitedCoordinates.isEmpty()){
            Coordinates aux = new Coordinates(visitedCoordinates.get(visitedCoordinates.size() - 1).x, visitedCoordinates.get(visitedCoordinates.size() - 1).y);
            proxima = aux;
        }
        
        return proxima;
    }
    
    private void mover(Coordinates coordenada){
        if (!visitedCoordinates.contains(localCoordinate)){
            visitedCoordinates.add(localCoordinate);
        }
        //visitedCoordinates.get(visitedCoordinates.size() - 1).visitados += 1;
        //Si no podemos movernos a ningun sitio retrocedemos
        
        if (obtenerNoVisitados().isEmpty()){
            coordenada = followedPath.element();
            followedPath.pop();
        }
        else{
            if (!followedPath.contains(localCoordinate)){
                followedPath.push(localCoordinate);
            }
        }
        
        tablero.setAgentePosition(coordenada.x, coordenada.y);
        localCoordinate = coordenada;
        
    }
    
    private double calculaHeuristica(Coordinates proxima){
        return Math.sqrt(Math.pow((objectiveCoordinate.x - proxima.x), 2)+ Math.pow((objectiveCoordinate.y - proxima.y), 2));
    }
}
