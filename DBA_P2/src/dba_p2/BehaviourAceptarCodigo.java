/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dba_p2;

import jade.lang.acl.ACLMessage;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;

/**
 *
 * @author ana
 */
public class BehaviourAceptarCodigo extends CyclicBehaviour {
    int step = 0;
    String codigo = "cacahuete";
    protected AgentePrueba agente;
    
    
    public BehaviourAceptarCodigo(AgentePrueba a) {
        agente = a;
    }
    
    @Override
    public void action(){
        switch(step){
            case 0:
                ACLMessage propuesta = new ACLMessage(ACLMessage.PROPOSE);
                propuesta.addReceiver(new AID("RudolphPrueba", AID.ISLOCALNAME));
                propuesta.setConversationId(codigo); // establecer el codigo como id de la conversación
                propuesta.setContent(codigo);
                agente.send(propuesta);
                System.out.println("Mandada proposición a rudolph con codigo:"+codigo);
                step = 1;
            case 1:
                System.out.println("Agente espera respuesta de Rudolph");
                propuesta = agente.blockingReceive(); // espera respuesta de rudolph
                System.out.println("Respuesta de Rudolph recibida");
                
                if (propuesta.getConversationId().equals(codigo) &&
                    propuesta.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                        System.out.println("Rudolph acepta propuesta");
                        ACLMessage solicitud = propuesta.createReply(ACLMessage.REQUEST);
                        solicitud.setContent("Solicitud de coordenadas"); 
                        System.out.println("Agente pide coordenadas");
                        agente.send(solicitud);
                        step = 2;
                    } else {
                        // me ha rechazado la propuesta
                        System.out.println("Rudolph me ha rechazado :(");
                        agente.doDelete();
                    }
            break;
            case 2:
                System.out.println("Esperando coordenadas de Rudolph");
                ACLMessage respuesta = agente.blockingReceive();
                System.out.println("Recibidas coordenadas de Rudolph");
                
                if (respuesta.getConversationId().equals(codigo) &&
                        respuesta.getPerformative() == ACLMessage.INFORM) {
                    String coordenadas = respuesta.getContent(); // cambiar al tipo que sean
                    System.out.println("Coordenadas recibidas: " + coordenadas);
                } else {
                    // Manejar otras respuestas o errores (opcional)
                    System.out.println("Error en el protocolo de conversación - paso 2");
                    agente.doDelete();
                }
        }
    }
}

