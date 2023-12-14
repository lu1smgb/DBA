package P3.practica;

import jade.core.Agent;

public class AgenteVoluntario extends Agent {
    
    private String idConversacionRudolph;

    public void setIdConversacionRudolph(String id) {
        this.idConversacionRudolph = id;
    }

    @Override
    protected void setup() {
        this.addBehaviour(new ProponerRescate(this));
    }

    @Override
    protected void takeDown() {
        System.out.println("Terminando agente");
        System.exit(0);
    }

}
