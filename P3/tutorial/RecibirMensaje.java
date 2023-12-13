package P3.tutorial;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class RecibirMensaje extends SimpleBehaviour {
    
    private AgenteReceptor agente;

    RecibirMensaje(AgenteReceptor agente) {
        this.agente = agente;
    }

    @Override
    public void action() {
        System.out.println("Espera mensaje");
        ACLMessage msg = this.agente.blockingReceive();
        System.out.println("Mensaje recibido: " + msg.getContent());
        this.agente.doDelete();
    }

    @Override
    public boolean done() {
        System.out.println("Termino recibir mensaje");
        return true;
    }

}
