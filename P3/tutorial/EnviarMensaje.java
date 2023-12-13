package P3.tutorial;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class EnviarMensaje extends SimpleBehaviour {

    private AgenteEmisor agente;
    
    EnviarMensaje(AgenteEmisor agente) {
        this.agente = agente;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(0);
        msg.addReceiver(new AID("agente-receptor", AID.ISLOCALNAME));
        msg.setContent("Hola receptor");
        agente.send(msg);
        this.agente.doDelete();
    }

    @Override
    public boolean done() {
        System.out.println("Mensaje enviado");
        return true;
    }

}
