/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dba_p2;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

/**
 *
 * @author ana
 */
public class BehaviourAceptarCodigo {
    
}




public class ComportamientoEmisor extends OneShotBehaviour {

    @Override
    public void action() {
        // Configurar el agente emisor

        // Crear un mensaje de propuesta (PROPOSE)
        ACLMessage propuesta = new ACLMessage(ACLMessage.PROPOSE);
        propuesta.addReceiver(new AID("AgenteReceptor", AID.ISLOCALNAME));
        propuesta.setContent("Código a verificar: ..."); // Puedes enviar el código aquí

        // Enviar la propuesta al agente receptor
        myAgent.send(propuesta);
    }
}

public class ComportamientoReceptor extends CyclicBehaviour {

    @Override
    public void action() {
        // Recibir la propuesta (PROPOSE)
        ACLMessage propuesta = myAgent.receive();

        if (propuesta != null) {
            // Verificar el código (aquí debes implementar tu lógica de verificación)
            boolean codigoCorrecto = verificarCodigo(propuesta.getContent());

            // Responder con accept-proposal o reject-proposal
            ACLMessage respuesta = propuesta.createReply();
            if (codigoCorrecto) {
                respuesta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
            } else {
                respuesta.setPerformative(ACLMessage.REJECT_PROPOSAL);
            }

            // Enviar la respuesta al agente emisor
            myAgent.send(respuesta);
        } else {
            // Si no hay mensaje, bloquear el comportamiento hasta que llegue un mensaje
            block();
        }
    }

    private boolean verificarCodigo(String codigo) {
        // Aquí debes implementar la lógica de verificación del código
        // Devuelve true si es correcto, false si no lo es
        return codigo.equals("código correcto");
    }
}
