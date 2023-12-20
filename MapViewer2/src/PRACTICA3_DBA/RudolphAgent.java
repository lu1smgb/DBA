/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PRACTICA3_DBA;

import jade.core.Agent;

/**
 *
 * @author Usuario
 */
public class RudolphAgent extends Agent{
    
    private String codigo;
    private BehaviourRudolph b = new BehaviourRudolph(this);
    
    @Override 
    protected void setup(){
        addBehaviour(b);
    }
    
    public void setCodigo(String s){
        codigo = s;
    }
    
    public String getCodigo(){
        return codigo;
    }
    
}
