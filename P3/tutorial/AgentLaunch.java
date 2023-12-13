package P3.tutorial;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class AgentLaunch {
    public static void main(String[] args) {
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "172.19.0.1");
        p.setParameter(Profile.CONTAINER_NAME, "Mi contenedor");
        ContainerController cc = rt.createAgentContainer(p);
        String agentClassNameEmisor = AgenteEmisor.class.getCanonicalName();
        String agentClassNameReceptor = AgenteReceptor.class.getCanonicalName();
        try {
            AgentController acReceptor = cc.createNewAgent("agente-receptor", agentClassNameReceptor, null);
            AgentController acEmisor = cc.createNewAgent("agente-emisor", agentClassNameEmisor, null);
            acReceptor.start();
            acEmisor.start();
        }
        catch (StaleProxyException e) {
            System.err.println("Error al crear el agente: StaleProxyException");
        }

    }
}
