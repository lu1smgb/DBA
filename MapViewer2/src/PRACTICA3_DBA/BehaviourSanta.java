package PRACTICA3_DBA;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import java.security.SecureRandom;

/**
 *
 * @author Usuario
 */
public class BehaviourSanta extends Behaviour{
    private double probabilidad = 0.8;
    private Santa agente;
    private int step;
    
    BehaviourSanta(Santa s){
        this.agente = s;
        this.step = 0;
    }
    
    @Override
    public void action(){
        ACLMessage msg = new ACLMessage(), reply;
        switch(this.step){
            case 0:
                msg.addReceiver(new AID("Rudolph", AID.ISLOCALNAME));
                msg.setContent(this.agente.getCodigo());
                msg.setConversationId("coords-conv");
                System.out.println("CODIGO SANTA: " + this.agente.getCodigo());
                this.agente.send(msg);
                msg = this.agente.blockingReceive();
                if(msg.getPerformative() == ACLMessage.PROPOSE){
                    SecureRandom random = new SecureRandom();
                    double resultado = random.nextDouble();
                    //if(resultado < probabilidad){
                    reply = msg.createReply(ACLMessage.ACCEPT_PROPOSAL);
                    reply.setContent(this.agente.getCodigo());
                    //}
                    //else{
                    //    reply = msg.createReply(ACLMessage.REJECT_PROPOSAL);
                    //}
                    
                    this.agente.send(reply);
                    
                }
                this.step = 1;
            
            case 1:
                msg = this.agente.blockingReceive();
                System.out.println("RECIBIDO SANTA");
                reply = msg.createReply();
                reply.setContent(this.agente.getex() + " " + this.agente.getey());
                this.agente.send(reply);
                
                msg = this.agente.blockingReceive();
                reply = msg.createReply();
                reply.setContent("HoHoHo!");
                this.agente.send(reply);
                
        }
    }

    @Override
    public boolean done() {
        this.agente.takeDown(); 
        return true;
    }
}
