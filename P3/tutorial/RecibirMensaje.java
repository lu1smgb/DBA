package P3.tutorial;

import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class RecibirMensaje extends SimpleBehaviour {
    
    private AgenteReceptor agente;
    private int paso;
    private boolean finalizado;

    RecibirMensaje(AgenteReceptor agente) {
        this.agente = agente;
        this.paso = 0;
        this.finalizado = false;
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

    private void recepcionPerformativa() {
        ACLMessage msg, reply;
        switch (this.paso) {
            case 0:
                System.out.println("[Receptor] Espero mensaje del emisor");
                msg = this.agente.blockingReceive();
                System.out.println(msg);
                if (msg.getPerformative() == ACLMessage.REQUEST) {
                    System.out.println("[Receptor] REQUEST del emisor recibido");
                    reply = msg.createReply(ACLMessage.AGREE);
                    this.agente.send(reply);
                    System.out.println("[Receptor] Enviamos AGREE al emisor");
                    this.paso = 1;
                }
                else {
                    System.err.println("[Receptor] Error en el protocolo");
                    this.agente.doDelete();
                }
                break;
                
            case 1:
                System.out.println("[Receptor] Espero mensaje del emisor");
                msg = this.agente.blockingReceive();
                System.out.println(msg);
                if (msg.getPerformative() == ACLMessage.INFORM) {
                    System.out.println("[Receptor] INFORM del emisor recibido: " + msg.getContent());
                    reply = msg.createReply(ACLMessage.INFORM);
                    reply.setContent("Perfecto pues");
                    this.agente.send(reply);
                    this.finalizado = true;
                }
                else {
                    System.err.println("[Receptor] Error en el protocolo");
                    this.agente.doDelete();
                }
                break;
            default:
                System.err.println("[Receptor] Error en el protocolo");
                this.agente.doDelete();
                break;
        }
    }

    @Override
    public void action() {
        recepcionPerformativa();
    }

    @Override
    public boolean done() {
        if (finalizado) {
            System.out.println("RecibirMensaje finaliza");
        }
        return finalizado;
    }

}
