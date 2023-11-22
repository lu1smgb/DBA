package dba_p2;

import java.util.ArrayList;
import javax.management.InvalidAttributeValueException;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import jade.core.Runtime;

public class NuevoAgente extends Agent {
    
    private Entorno entorno;
    private ArrayList<Coordinates> alrededores;

    /**
     * <h2>Setup</h2>
     * 
     * El agente inicializa el entorno, una vez inicializado, 
     * realiza una percepcion inicial de este
     * <p>
     * Despues se inicializan las estructuras de datos que sean necesarias
     * para la implementacion de los comportamientos que vaya a seguir
     * 
     * Finalmente se agregan los comportamientos
     */
    @Override
    protected void setup() {
        this.entorno = (Entorno) this.getArguments()[0];
        this.alrededores = percibir();

        addBehaviour(new ComportamientoAleatorio(this));
        
    }

    /**
     * <h2>Getter entorno</h2>
     * Necesario para que los comportamientos puedan acceder al entorno del agente
     * @return El entorno del agente
     */
    public Entorno getEntorno() {
        return this.entorno;
    }

    /**
     * <h2>takeDown</h2>
     * Funcion a ejecutar por el agente una vez se haya indicado su finalizacion
     */
    @Override
    protected void takeDown() {
        System.out.println("Terminando agente...");
        System.exit(0);
    }
    
    /**
     * <h2>Metodo percibirDireccion</h2>
     * 
     * El agente percibe unas coordenadas segun la direccion especificada
     * <p>
     * Lanzara una excepcion si las coordenadas no se encuentran dentro de los limites
     * del entorno
     * 
     * @param direccion Direccion en la que el agente va a percibir
     * @return Coordenadas percibidas, si la direccion apunta dentro del entorno
     * @throws IndexOutOfBoundsException Si la direccion apunta fuera del entorno
     * @see {@link #percibir()}
     */
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
            throw new IndexOutOfBoundsException(
                "Percibe " + direccion + " desde " + posicionAgente + " -> FUERA DEL MAPA");
        }
        System.out.println(
            "Percibe " + direccion + " desde " + posicionAgente + " -> " + coordenadas + " " + this.entorno.getElement(coordenadas));
        return coordenadas;
    }

    /**
     * <h2>Metodo percibir</h2>
     * 
     * Metodo de percepcion usado por el agente
     * <p>
     * Percibe en las 4 direcciones: arriba, abajo, izquierda y derecha
     * <p>
     * Para cada direccion:
     * <p>
     * Obtiene una coordenada si la misma se encuentra dentro de los limites del mapa.
     * En caso contrario, obtiene un valor nulo
     * <p>
     * Las percepciones del agente se guardan en {@link #alrededores} y se van sobreescribiendo con futuras llamadas
     * @return Coordenadas percibidas por el agente
     */
    public ArrayList<Coordinates> percibir() {
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
        System.out.println(alrededores);
        System.out.println("--- TERMINA PERCEPCION ---");
        return alrededores;
    }

    /**
     * <h2>Metodo moverse</h2>
     * Metodo usado por el agente para moverse en una direccion
     * Para ello usa la coordenada de la percepcion correspondiente a la direccion especificada
     * @param movimiento Direccion en la que desea moverse el agente
     * @return Si el agente ha podido moverse
     */
    public boolean moverse(Movimiento movimiento) {

        Coordinates nuevaPosicion = this.alrededores.get(movimiento.value());

        boolean pudoMoverse = this.entorno.moverAgente(nuevaPosicion);

        // Una vez se mueve tiene que actualizar su percepcion/sensores
        this.alrededores = percibir();
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
            Coordinates posicionAgente = new Coordinates(6, 6);
            Coordinates posicionObjetivo = new Coordinates(mapa.getNumberOfCols() - 1, mapa.getNumberOfRows() - 1);
            Entorno entorno = new Entorno(mapa, posicionAgente, posicionObjetivo);
            AgentController ac = cc.createNewAgent("NuevoAgente", agentClassName, new Object[] {entorno});
            ac.start();
        }
        catch (StaleProxyException e) {
            System.err.println("Error al crear el agente: StaleProxyException");
        }
        catch (InvalidAttributeValueException e) {
            System.err.println("Error al crear el agente: parametros del entorno no validos");
        }

    }

}
