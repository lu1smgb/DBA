
package dba_p2;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 *
 * @author ana
 */
public class AgentePrueba extends Agent{
    private BehaviourAceptarCodigo bAceptarCodigo = new BehaviourAceptarCodigo(this);
    
    String codigo = "cacahuete";
    
    @Override
    public void setup(){
        addBehaviour(bAceptarCodigo);
    }
    
    public static void main(String[] args) {
        jade.core.Runtime rt = jade.core.Runtime.instance();

        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.CONTAINER_NAME, "ServidorDBA");

        ContainerController cc = rt.createAgentContainer(p);

        try {
            String agentName = "AgentePrueba";
            AgentController ac = cc.createNewAgent(agentName, AgentePrueba.class.getName(), null);
            ac.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
