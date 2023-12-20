/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PRACTICA3_DBA;

/**
 *
 * @author Usuario
 */
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.Runtime;
import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import javax.swing.*;

public class MyAgent extends Agent {
    private String codigo;
    private ArrayList<Nodo> visitedNodos; // Nodos visitados
    private Nodo origen; // Nodo origen
    private Entorno entorno; // Entorno
    private int contadorHeuristicaMayor; // Si pasamos a una casilla donde la distancia era mayor a la original
                                         // comenzamos a contar
    private int iters; // Número de pasos
    private int menores; // Si estamos contando desde que pasamos a una mayor cuantas veces pasamos a
                         // casillas con menor distancia
    private boolean cuenta; // Bool que controla si se cuentan los pasos desde que nos movimos a una casilla
                            // con distancia mayot
    private BehaviourBusqueda b = new BehaviourBusqueda(this);

    // Inicializamos los atributos del agente y le pasamos el entorno y su nodo
    // origen
    @Override
    protected void setup() {
        this.entorno = (Entorno) this.getArguments()[0];
        this.origen = entorno.agente_actual;
        this.visitedNodos = new ArrayList<>();
        this.visitedNodos.add(origen);
        this.contadorHeuristicaMayor = 0;
        this.cuenta = false;
        this.iters = 0;
        this.menores = 0;
        addBehaviour(b);
    }

    // Métodos set y get
    public void addVisitedNodo(Nodo n) {
        visitedNodos.add(n);
    }

    public void addVisitedNodo(int i, Nodo n) {
        visitedNodos.add(i, n);
    }

    public void removeVisitedNodo(int i) {
        visitedNodos.remove(i);
    }

    public ArrayList<Nodo> getVisitedNodos() {
        return visitedNodos;
    }

    public void setVisitedNodos(ArrayList<Nodo> l) {
        visitedNodos = l;
    }

    public Nodo getOrigen() {
        return origen;
    }
    
    public void reset(){
        this.origen = entorno.agente_actual;
        this.visitedNodos = new ArrayList<>();
        this.visitedNodos.add(origen);
        this.contadorHeuristicaMayor = 0;
        this.cuenta = false;
        this.iters = 0;
        this.menores = 0;
    }

    public Entorno getEntorno() {
        return entorno;
    }

    public int getContadorHeuristicaMayor() {
        return contadorHeuristicaMayor;
    }

    public int getIters() {
        return iters;
    }

    public void plusIters() {
        iters++;
    }

    public void setOrigen(Nodo n) {
        origen = n;
    }

    public int getMenores() {
        return menores;
    }

    public boolean isCuenta() {
        return cuenta;
    }

    public void setCuenta(boolean c) {
        cuenta = c;
    }

    public void setMayores(int my) {
        contadorHeuristicaMayor = my;
    }

    public void setMenores(int mn) {
        menores = mn;
    }

    public void setIdConversacionRudolph(String content) {
        codigo = content;
    }
    
    public String getCodigo(){
        return codigo;
    }
    
    @Override
    protected void takeDown(){
        System.exit(0);
    }
}
