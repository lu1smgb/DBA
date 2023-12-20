package PRACTICA3_DBA;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import jade.lang.acl.ACLMessage;

public class BehaviourBusqueda extends CyclicBehaviour {
    protected MyAgent agente;
    private int umbral_mayor;
    private int umbral_menor;
    private boolean ini = true;
    private boolean solicitud = true;
    private boolean acabar = false;
    private ACLMessage msg = new ACLMessage(), reply;
    private ACLMessage msgs = new ACLMessage(), replys;

    BehaviourBusqueda(MyAgent a) {
        this.agente = a;

    }

    @Override
    public void action() {
        if(solicitud){
            permisoSanta();
            System.out.println("CODIGO MI AGENTE: " + this.agente.getCodigo());
            aceptacionRudolph();
            solicitud = false;
        }
        if(ini){
            System.out.println("WAITING");
            String c = "";
            msg = this.agente.blockingReceive();
            if(msg != null){ 
                if(msg.getContent().equals("Ve por Santa héroe <3")){
                    c = acabarSanta();
                    System.out.println("ASI QUEDA CCCCCCCCCCCCC");
                    System.out.println(c);
                }
                else{
                    c = msg.getContent();
                }
            }
            
            busquedaCoordenadas(c);
            ini = false;
        }
        agente.getEntorno().mostrarEstado(agente.getVisitedNodos());
        this.umbral_mayor = (agente.getEntorno().agente_actual.heu * 4);
        this.umbral_menor = (agente.getEntorno().agente_actual.heu * 2);
        System.out.println("CONTADOR: " + agente.getContadorHeuristicaMayor() + " \n");
        mover();
        if (agente.getEntorno().agente_actual.equals(agente.getEntorno().destino)) {
            if(!acabar){
                System.out.println("¡El agente ha alcanzado el objetivo!");
                reply = msg.createReply();
                reply.setContent("ENCONTRADO");
                this.agente.send(reply);
                ini = true;
            }
            else{
                cerrarSanta();
            }
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
    
    public void busquedaCoordenadas(String s){
        this.agente.reset();
        System.out.println("RECIBIDO");
        String[] destino = s.split(" ");
        int dx = Integer.parseInt(destino[0]);
        int dy = Integer.parseInt(destino[1]);

        agente.getEntorno().setDestino(dx, dy);
    }
    
    public void permisoSanta(){
        int step = 0;
        switch (step){
            case 0:
                msgs = new ACLMessage(ACLMessage.PROPOSE);
                msgs.addReceiver(new AID("Santa", AID.ISLOCALNAME));
                msgs.setContent("Quiero salvar la Navidad");
                msgs.setConversationId("IdConversacionSanta");
                System.out.println(msgs);
                System.out.println("[Agente] Presenta propuesta");
                this.agente.send(msgs);
                step = 1;
            case 1:
                System.out.println("[Agente] Espera respuesta de Santa");
                replys = this.agente.blockingReceive();
                System.out.println(replys);
                switch (replys.getPerformative()) {
                    case ACLMessage.ACCEPT_PROPOSAL:
                        // Contacta con rudolph
                        System.out.println("[Agente] Recibe aceptacion");
                        this.agente.setIdConversacionRudolph(replys.getContent());
                        break;

                    case ACLMessage.REJECT_PROPOSAL:
                        System.out.println("[Agente] Recibe rechazo, termina ejecucion");
                        this.agente.doDelete();
                        break;
                
                    default:
                        System.err.println("[Agente] ERROR en el protocolo");
                        this.agente.doDelete();
                        break;
                }
        }
                
    }
    
    public void aceptacionRudolph(){
        int step = 0;
        switch (step){
            case 0:
                msg = new ACLMessage(ACLMessage.PROPOSE);
                msg.addReceiver(new AID("Rudolph", AID.ISLOCALNAME));
                msg.setContent(this.agente.getCodigo());
                msg.setConversationId("AceptacionRudolph");
                System.out.println(msg);
                System.out.println("[Agente] Presenta propuesta");
                this.agente.send(msg);
                step = 1;
            case 1:
                System.out.println("[Agente] Espera respuesta de Rudolph");
                reply = this.agente.blockingReceive();
                System.out.println(reply);
                switch (reply.getPerformative()) {
                    case ACLMessage.ACCEPT_PROPOSAL:
                        // Contacta con rudolph
                        System.out.println("[Agente] Recibe aceptacion");
                        this.agente.setIdConversacionRudolph(reply.getContent());
                        break;

                    case ACLMessage.REJECT_PROPOSAL:
                        System.out.println("[Agente] Recibe rechazo, termina ejecucion");
                        this.agente.doDelete();
                        break;
                
                    default:
                        System.err.println("[Agente] ERROR en el protocolo");
                        this.agente.doDelete();
                        break;
                }
        }
        
    }
    
    public String acabarSanta(){
        //msgs = new ACLMessage();
        //msgs.addReceiver(new AID("Santa", AID.ISLOCALNAME));
        msgs.setContent("Acabemos con esto Santa");
        System.out.println(msgs);
        this.agente.send(msgs);
        acabar = true;
        msgs = this.agente.blockingReceive();
        return msgs.getContent();
    }
    
    public void cerrarSanta(){
        msgs.setContent("Ya he llegado bro");
        this.agente.send(msgs);
        msgs = this.agente.blockingReceive();
        System.out.println(msgs.getContent());
        this.agente.takeDown();
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

                    if (agente.isCuenta() && pasos >= umbral_mayor) { // Llegamos al
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
                if (agente.getMenores() < umbral_menor) { // Si llegamos al umbral de menores
                    agente.setMayores(agente.getContadorHeuristicaMayor() + 1); // Reiniciamos la cuenta de mayores
                    int pasos = agente.getContadorHeuristicaMayor();
                    moverAlNodo(siguienteNodo);

                    if (agente.isCuenta() && pasos >= umbral_mayor) { // Si llegamos al
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
            agente.getEntorno().mostrarEstado(agente.getVisitedNodos());
        }
    }

    private void moverAlNodo(Nodo siguienteNodo) {
        agente.setOrigen(siguienteNodo);
        agente.getEntorno().setAgenteActual(siguienteNodo);
        agente.addVisitedNodo(siguienteNodo);
        agente.plusIters();
    }
}
