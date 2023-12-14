package P3.practica;

import java.security.SecureRandom;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class EsperaPropuesta extends Behaviour {

    private static double probabilidadAceptacion = 0.8;
    private Santa santa;
    private int paso;
    private boolean finalizado;

    EsperaPropuesta(Santa santa) {
        this.santa = santa;
        this.paso = 0;
        this.finalizado = false;
    }

    @Override
    public void action() {
        ACLMessage msg, reply;
        switch (this.paso) {
            case 0:
                System.out.println("[Santa] Espera nueva propuesta");
                msg = this.santa.blockingReceive();
                if (msg.getPerformative() == ACLMessage.PROPOSE) {
                    System.out.println("[Santa] Recibe propuesta: " + msg.getContent());
                    System.out.println(msg);
                    SecureRandom random = new SecureRandom();
                    double resultadoAleatorio = random.nextDouble();
                    if (resultadoAleatorio < probabilidadAceptacion) {
                        // Aceptacion
                        System.out.println("[Santa] Acepta propuesta");
                        reply = msg.createReply(ACLMessage.ACCEPT_PROPOSAL);
                        reply.setContent(this.santa.getCodigoSecreto());
                    }
                    else {
                        // Rechazo
                        System.out.println("[Santa] Rechaza propuesta");
                        reply = msg.createReply(ACLMessage.REJECT_PROPOSAL);
                    }
                    this.santa.send(reply);
                    this.paso = 1;
                }
                else {
                    System.err.println("[Santa] ERROR en el protocolo de comunicacion");
                    this.santa.doDelete();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean done() {
        if (finalizado)
            System.out.println("[Santa] TERMINA COMPORTAMIENTO EsperaPropuesta");
        return finalizado;
    }

}
