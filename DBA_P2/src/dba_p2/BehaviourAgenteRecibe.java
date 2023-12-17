package dba_p2;

import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Clase que define el comportamiento del agente receptor.
 */
public class BehaviourAgenteRecibe extends TickerBehaviour {
    protected AgentePrueba agente;
    
    public BehaviourAgenteRecibe(AgentePrueba a) {
        super(null, 1000); // Verificar cada 1000 milisegundos (1 segundo)
        agente = a;
    }

    @Override
    protected void onTick() {
        ACLMessage msg = agente.receive();
        if (msg != null) {
            System.out.println("Mensaje recibido: " + msg.getContent());
        } else {
            System.out.println("No hay mensaje con código de Santa");
        }
    }
}
