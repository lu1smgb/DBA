package P3.tutorial;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class EnviarMensaje extends SimpleBehaviour {

    private AgenteEmisor agente;
    private int paso;
    
    EnviarMensaje(AgenteEmisor agente) {
        this.agente = agente;
        this.paso = 0;
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
                msg.setConversationId("identificador-conversacion");
                this.agente.send(msg);
                this.paso = 1;
                break;
            case 1: // El emisor espera respuesta del receptor
                System.out.println("[Emisor] Paso 2 - Espero respuesta de emisor");
                ACLMessage reply = this.agente.blockingReceive();
                if (reply.getConversationId().equals("identificador-conversacion")) {
                    System.out.println("[Emisor] Mensaje recibido: " + reply.getContent());
                }
                else {
                    System.err.println("Error: ids no coinciden");
                    this.agente.doDelete();
                }
                this.paso = 2;
                break;
            default:
                System.err.println("Error en el protocolo");
                this.agente.doDelete();
                break;
        }
    }

    @Override
    public void action() {
        envioPorPasos();
    }

    @Override
    public boolean done() {
        return paso == 2;
    }

}
