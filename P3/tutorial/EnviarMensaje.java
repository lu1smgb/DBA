package P3.tutorial;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class EnviarMensaje extends SimpleBehaviour {

    private AgenteEmisor agente;
    private int paso;
    private String idConversacion;
    private boolean finalizado;
    
    EnviarMensaje(AgenteEmisor agente) {
        this.agente = agente;
        this.paso = 0;
        this.idConversacion = "identificador-conversacion";
        this.finalizado = false;
    }

    private void envioSimple() {
        ACLMessage msg = new ACLMessage(0);
        msg.addReceiver(new AID("agente-receptor", AID.ISLOCALNAME));
        msg.setContent("Hola receptor");
        agente.send(msg);
        this.agente.doDelete();
    }

    private void envioPorPasos() {
        switch (this.paso) {
            case 0: // Envia mensaje al receptor por primera vez
                System.out.println("[Emisor] Paso 1 - Envio mensaje a receptor");
                ACLMessage msg = new ACLMessage(0);
                msg.addReceiver(new AID("agente-receptor", AID.ISLOCALNAME));
                msg.setContent("Hola receptor");
                msg.setConversationId(this.idConversacion);
                this.agente.send(msg);
                this.paso = 1;
                break;
            case 1: // El emisor espera respuesta del receptor
                System.out.println("[Emisor] Paso 2 - Espero respuesta de emisor");
                ACLMessage reply = this.agente.blockingReceive();
                if (reply.getConversationId().equals(this.idConversacion)) {
                    System.out.println("[Emisor] Mensaje recibido: " + reply.getContent());
                }
                else {
                    System.err.println("Error: ids no coinciden");
                    this.agente.doDelete();
                }
                this.finalizado = true;
                break;
            default:
                System.err.println("Error en el protocolo");
                this.agente.doDelete();
                break;
        }
    }

    private void envioPerformativa() {
        ACLMessage msg, reply;
        switch (this.paso) {
            case 0:
                msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(new AID("agente-receptor", AID.ISLOCALNAME)); // !!!
                msg.setConversationId(this.idConversacion);
                this.agente.send(msg);
                System.out.println("[Emisor] Envio REQUEST al receptor");
                this.paso = 1;
                break;
            case 1:
                msg = this.agente.blockingReceive();
                if (msg.getConversationId().equals(this.idConversacion) && msg.getPerformative() == ACLMessage.AGREE) {
                    System.out.println("[Emisor] AGREE del receptor recibido");
                    reply = msg.createReply(ACLMessage.INFORM);
                    reply.setContent("He recibido tu agree, receptor!");
                    this.agente.send(reply);
                    System.out.println("[Emisor] Envio INFORM a receptor");
                    this.paso = 2;
                }
                else {
                    System.err.println("[Emisor] Error en el protocolo");
                    this.agente.doDelete();
                }
                break;
            case 2:
                msg = this.agente.blockingReceive();
                if (msg.getConversationId().equals(this.idConversacion) && msg.getPerformative() == ACLMessage.INFORM) {
                    System.out.println("[Emisor] INFORM del receptor recibido: " + msg.getContent());
                    this.finalizado = true;
                }
                else {
                    System.err.println("[Emisor] Error en el protocolo");
                    this.agente.doDelete();
                }
            default:
                System.err.println("[Emisor] Error en el protocolo");
                this.agente.doDelete();
                break;
        }
    }

    @Override
    public void action() {
        envioPerformativa();
    }

    @Override
    public boolean done() {
        if (finalizado) {
            System.out.println("EnviarMensaje finaliza");
        }
        return finalizado;
    }

}
