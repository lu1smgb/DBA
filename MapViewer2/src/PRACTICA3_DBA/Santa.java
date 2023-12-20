/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PRACTICA3_DBA;

import jade.core.Agent;
import java.security.SecureRandom;

/**
 *
 * @author Usuario
 */
public class Santa extends Agent{
    private String codigo;
    private int establox = 0;
    private int establoy = 9;
    
    public String getCodigo(){
        return codigo;
    }
    
    public int getex(){
        return establox;
    }
    
    public int getey(){
        return establoy;
    }
    
    @Override
    protected void setup(){
        final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final int TAM_CODIGO = 16;
        StringBuilder builder = new StringBuilder(TAM_CODIGO);
        SecureRandom random = new SecureRandom();

        for (int i=0; i < TAM_CODIGO; i++) {
            int idxCaracter = random.nextInt(caracteres.length());
            char caracter = caracteres.charAt(idxCaracter);
            builder.append(caracter);
        }
        
        codigo = builder.toString();
        this.addBehaviour(new BehaviourSanta(this));
    }
    
    @Override
    protected void takeDown(){
        System.exit(0);
    }
}
