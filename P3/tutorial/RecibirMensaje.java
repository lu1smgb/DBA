package P3.tutorial;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class RecibirMensaje extends SimpleBehaviour {
    
    private AgenteReceptor agente;

    RecibirMensaje(AgenteReceptor agente) {
        this.agente = agente;
    }

    private void recepcionSimple() {
        System.out.println("Espera mensaje");
        ACLMessage msg = this.agente.blockingReceive();
        System.out.println("Mensaje recibido: " + msg.getContent());
        this.agente.doDelete();
    }

    private void recepcionPorPasos() {
        System.out.println("[Receptor] Espero mensaje del emisor");
        ACLMessage msg = this.agente.blockingReceive();
        System.out.println("[Receptor] Mensaje de " + msg.getSender().getLocalName() + ": " + msg.getContent());
        ACLMessage reply = msg.createReply();
        reply.setContent("Hola emisor");
        System.out.println("[Receptor] Envio respuesta a emisor");
        this.agente.send(reply);
    }

    @Override
    public void action() {
        recepcionPorPasos();
    }

    @Override
    public boolean done() {
        return true;
    }

}
