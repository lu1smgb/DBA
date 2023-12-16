/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dba_p2;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

// Agente de prueba para el selector de comportamientos
public class Behaviour4 extends OneShotBehaviour {
    public String name = "Stuart";
    public String descripcion = "Le da miedo andar como lo dejes solo chilla de miedo y se auto elimina.";
    protected MyAgent agente;

    Behaviour4(MyAgent a) {
        this.myAgent = agente = a;
    }

    @Override
    public void action() {
        System.out.println("ME DA MIEDO IR SOLO NO PUEDO MÁS (SE TIRA POR UN BARRANCO)\n");
        agente.doDelete();
    }

}
