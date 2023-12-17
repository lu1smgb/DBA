/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dba_p2;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author ana
 */
public class BehaviourRudolphAcepta extends CyclicBehaviour {
    protected RudolphPrueba agente;
    
    public BehaviourRudolphAcepta(RudolphPrueba a){
        agente = a;
    }

    @Override
    public void action() {
        // Recibir la propuesta (PROPOSE)
        System.out.println("Esperando propuesta de agente");
        ACLMessage propuesta = agente.blockingReceive();
        System.out.println("Recibida propuesta del agente");

        if (propuesta != null) {
            // Verificar el código (aquí debes implementar tu lógica de verificación)
            boolean codigoCorrecto = verificarCodigo(propuesta.getContent());
            System.out.println("Codigo correcto: "+codigoCorrecto);
            // Responder con accept-proposal o reject-proposal
            System.out.println("Preparo respuesta para agente...");
            ACLMessage respuesta = propuesta.createReply();
            System.out.println("Respuesta creada");
            if (codigoCorrecto) {
                respuesta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                System.out.println("Rudolph acepta propuesta!! Te mando accept proposal");
            } else {
                respuesta.setPerformative(ACLMessage.REJECT_PROPOSAL);
                System.out.println("Rudolph no acepta propuesta... Te mando reject proposal");
            }

            // Enviar la respuesta al agente emisor
            agente.send(respuesta);
        } else {
            // Si no hay mensaje, bloquear el comportamiento hasta que llegue un mensaje
            block();
        }
    }

    private boolean verificarCodigo(String codigo) {
        // Aquí debes implementar la lógica de verificación del código
        // Devuelve true si es correcto, false si no lo es
        return codigo.equals("cacahuete");
    }
}