
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
public class RudolphPrueba extends Agent{
    private BehaviourRudolphAcepta bRudolphAcepta = new BehaviourRudolphAcepta(this);
    
    String codigo = "cacahuete";
    
    @Override
    public void setup(){
        addBehaviour(bRudolphAcepta);
    }
    
    public static void main(String[] args) {
        jade.core.Runtime rt = jade.core.Runtime.instance();

        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.CONTAINER_NAME, "ServidorDBA");

        ContainerController cc = rt.createAgentContainer(p);

        try {
            String agentName = "RudolphPrueba";
            AgentController ac = cc.createNewAgent(agentName, RudolphPrueba.class.getName(), null);
            ac.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
