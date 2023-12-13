package P3.tutorial;

import jade.core.Agent;

public class AgenteEmisor extends Agent {
    
    @Override
    protected void setup() {
        this.addBehaviour(new EnviarMensaje(this));
    }

    @Override
    protected void takeDown() {
        System.out.println("Termina agente " + this.getLocalName());
        super.takeDown();
    }

}
