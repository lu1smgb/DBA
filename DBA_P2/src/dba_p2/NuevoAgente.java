package dba_p2;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import jade.core.Runtime;
import jade.core.behaviours.OneShotBehaviour;

public class NuevoAgente extends Agent {
    
    private Entorno entorno;
    private ArrayList<Coordinates> alrededores;
    private Deque<Coordinates> followedPath;
    private ArrayList<Coordinates> visitedCoordinates;

    @Override
    protected void setup() {
        this.entorno = (Entorno) this.getArguments()[0];
        percibir();
        this.followedPath = new LinkedList<Coordinates>();
        this.visitedCoordinates = new ArrayList<Coordinates>();

        // Comportamiento de prueba
        // usado para comprobar el estado del entorno
        this.addBehaviour(new OneShotBehaviour(this) {
            public void action() {
                System.out.println(entorno);
                ////doDelete();
            }
        });

        this.addBehaviour(new OneShotBehaviour(this) {
            public void action() {
                moverse(Movimiento.ABAJO);
                moverse(Movimiento.ABAJO);
                moverse(Movimiento.DERECHA);
                moverse(Movimiento.DERECHA);
                moverse(Movimiento.DERECHA);
                moverse(Movimiento.DERECHA);
                moverse(Movimiento.ARRIBA);
                moverse(Movimiento.ARRIBA);
                moverse(Movimiento.DERECHA);
                moverse(Movimiento.DERECHA);
                moverse(Movimiento.ABAJO);
                moverse(Movimiento.ABAJO);
                moverse(Movimiento.ABAJO);
                moverse(Movimiento.ABAJO);
                moverse(Movimiento.ABAJO);
                moverse(Movimiento.ABAJO);
                moverse(Movimiento.ABAJO);

            }
        });
        
        this.addBehaviour(new OneShotBehaviour(this) {
            public void action() {
                System.out.println(entorno);
                System.out.println("Agente ha llegado a destino: " + entorno.objetivoCumplido());
                doDelete();
            }
        });
        
    }

    @Override
    protected void takeDown() {
        System.out.println("Terminando agente...");
        System.exit(0);
    }
    
    public Coordinates percibirDireccion(Movimiento direccion) throws IndexOutOfBoundsException {
        Coordinates posicionAgente = this.entorno.getPosicionAgente();
        Coordinates coordenadas = new Coordinates(posicionAgente);
        switch (direccion) {
            case ARRIBA:
                coordenadas.y -= 1;
                break;
            case ABAJO:
                coordenadas.y += 1;
                break;
            case IZQUIERDA:
                coordenadas.x -= 1;
                break;
            case DERECHA:
                coordenadas.x += 1;
                break;
            default:
                break;
        }
        if (!this.entorno.coordenadasValidas(coordenadas)) {
            throw new IndexOutOfBoundsException("Percibe " + direccion + " desde " + posicionAgente + " -> FUERA DEL MAPA");
        }
        System.out.println("Percibe " + direccion + " desde " + posicionAgente + " -> " + coordenadas);
        return coordenadas;
    }

    /**
     * Funcion usada para actualizar las percepciones/sensores del agente
     * Arriba, abajo, izquierda, derecha
     */
    public void percibir() {
        System.out.println("--- INICIA PERCEPCION ---");
        int numeroSensores = Movimiento.values().length;
        ArrayList<Coordinates> alrededores = new ArrayList<>(numeroSensores);
        for (Movimiento m : Movimiento.values()) {
            try {
                alrededores.add(this.percibirDireccion(m));
            }
            catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
                alrededores.add(null);
            }
        }
        this.alrededores = alrededores;
        System.out.println(this.alrededores);
        System.out.println("--- TERMINA PERCEPCION ---");
    }

    public boolean moverse(Movimiento movimiento) {

        Coordinates posicionAnterior = this.entorno.getPosicionAgente();
        Coordinates nuevaPosicion = this.alrededores.get(movimiento.value());

        boolean pudoMoverse = this.entorno.moverAgente(nuevaPosicion);

        if (pudoMoverse && !this.followedPath.contains(posicionAnterior)) {
            followedPath.add(posicionAnterior);
        }

        // Una vez se mueve tiene que actualizar su percepcion/sensores
        percibir();
        return pudoMoverse;

    }

    public static void main(String[] args) {

        Runtime rt = Runtime.instance();

        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "172.19.0.1");
        p.setParameter(Profile.CONTAINER_NAME, "Practica 2 DBA - Grupo 304");

        ContainerController cc = rt.createAgentContainer(p);
        String agentClassName = NuevoAgente.class.getCanonicalName();
        System.out.println(agentClassName);
        
        try {
            int mapaArray[][] = {
                { 0,  0,  0, -1,  0,  0,  0 },
                { 0,  0, -1,  0,  0, -1,  0 },
                { 0,  0,  0,  0,  0, -1,  0 },
                { 0,  0,  0,  0,  0, -1,  0 },
                { 0,  0,  0, -1,  0, -1,  0 },
                { 0,  0, -1,  0,  0, -1,  0 },
                { 0,  0,  0,  0,  0, -1,  0 },
                { 0,  0,  0,  0,  0, -1,  0 }
            };
            Mapa mapa = new Mapa(mapaArray);
            mapa.setName("Mapa Prototipo");
            Entorno entorno = new Entorno(mapa);
            AgentController ac = cc.createNewAgent("NuevoAgente", agentClassName, new Object[] {entorno});
            ac.start();
        }
        catch (StaleProxyException e) {
            System.err.println("Error al crear el agente: StaleProxyException");
        }

    }

}
