package PRACTICA3_DBA;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Usuario
 */
public class BehaviourRudolph extends OneShotBehaviour{
    
    protected RudolphAgent rudolph;
    private int step = 0;
    
    BehaviourRudolph(RudolphAgent a){
        rudolph = a;
    }
    
    @Override
    public void action(){
        ACLMessage msg = new ACLMessage(), reply;
        msg = this.rudolph.blockingReceive();
        this.rudolph.setCodigo(msg.getContent());
        //System.out.println("CODIGO RUDOLPH: " + this.rudolph.getCodigo());
                    
        msg = this.rudolph.blockingReceive();
        if(msg.getPerformative() == ACLMessage.PROPOSE){
            if(msg.getContent().equals(this.rudolph.getCodigo())){
                reply = msg.createReply(ACLMessage.ACCEPT_PROPOSAL);
                reply.setContent("Te enviaré las coordenadas");
            }
            else{
                reply = msg.createReply(ACLMessage.REJECT_PROPOSAL);
            }
                    
            this.rudolph.send(reply);
        }
        
        for(int i = 0; i < 8; i++){
            msg = this.rudolph.blockingReceive();
            
            switch(this.step){
                case 0:
                    int d = i + 2;
                    reply = msg.createReply(ACLMessage.INFORM);
                    reply.setContent(d + " " + d);
                    //System.out.println("ENVIO MENSAJE");
                    rudolph.send(reply);
                    //System.out.println("ENVIADO");
                    
            }
        }
        msg = this.rudolph.blockingReceive();
        reply = msg.createReply(ACLMessage.INFORM);
        reply.setContent("Ve por Santa héroe <3");
        //System.out.println("ENVIO MENSAJE");
        rudolph.send(reply);
        this.rudolph.doDelete();
        //System.out.println("ENVIADO");
        
    }
    
}
