package P3.practica;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class ProponerRescate extends Behaviour {
    
    private AgenteVoluntario agente;
    private int paso;
    private boolean finalizado;

    ProponerRescate(AgenteVoluntario agente) {
        this.agente = agente;
        this.paso = 0;
        this.finalizado = false;
    }

    @Override
    public void action() {
        ACLMessage msg, reply;
        switch (this.paso) {
            case 0:
                msg = new ACLMessage(ACLMessage.PROPOSE);
                msg.addReceiver(new AID("santa", AID.ISLOCALNAME));
                msg.setContent("Quiero salvar la Navidad");
                msg.setConversationId("IdConversacionSanta");
                System.out.println(msg);
                System.out.println("[Agente] Presenta propuesta");
                this.agente.send(msg);
                this.paso = 1;
                break;
            case 1:
                System.out.println("[Agente] Espera respuesta de Santa");
                reply = this.agente.blockingReceive();
                System.out.println(reply);
                switch (reply.getPerformative()) {
                    case ACLMessage.ACCEPT_PROPOSAL:
                        // Contacta con rudolph
                        System.out.println("[Agente] Recibe aceptacion");
                        this.agente.setIdConversacionRudolph(reply.getContent());
                        this.finalizado = true;
                        break;

                    case ACLMessage.REJECT_PROPOSAL:
                        System.out.println("[Agente] Recibe rechazo, termina ejecucion");
                        this.finalizado = true;
                        this.agente.doDelete();
                        break;
                
                    default:
                        System.err.println("[Agente] ERROR en el protocolo");
                        this.agente.doDelete();
                        break;
                }
            default:
                break;
        }
    }

    @Override
    public boolean done() {
        if (finalizado)
            System.out.println("[Agente] TERMINA COMPORTAMIENTO ProponerRescate");
        return finalizado;
    }

}
