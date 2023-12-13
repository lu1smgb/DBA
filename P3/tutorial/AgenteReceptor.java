package P3.tutorial;

import jade.core.Agent;

public class AgenteReceptor extends Agent {
    
    @Override
    protected void setup() {
        this.addBehaviour(new RecibirMensaje(this));
    }

    @Override
    protected void takeDown() {
        System.out.println("Termina agente " + this.getLocalName());
        super.takeDown();
    }

}
