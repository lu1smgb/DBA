/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dba_p2;

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
    private Behaviour0 b0 = new Behaviour0(this);
    private Behaviour1 b1 = new Behaviour1(this);
    private Behaviour2 b2 = new Behaviour2(this);
    private Behaviour3 b3 = new Behaviour3(this);
    private Behaviour4 b4 = new Behaviour4(this);
    private int selectedBehaviorIndex = -1; // Comportamiento elegido en la IU

    // Interfaz selectora de behaviour a usar
    private void mostrarSelector() {
        JFrame frame = new JFrame("Behaviour Selector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(820, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));

        JLabel descriptionLabel = new JLabel();
        JButton[] buttons = new JButton[5];

        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i] = new JButton(obtenerNombreBehaviour(i));

            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedBehaviorIndex = index;
                    String description = obtenerDescripcionBehaviour(index);
                    descriptionLabel.setText(description);
                }
            });

            panel.add(buttons[i]);
        }

        panel.add(descriptionLabel);

        JButton seleccionarButton = new JButton("Seleccionar");
        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedBehaviorIndex != -1) {
                    switch (selectedBehaviorIndex) {
                        case 0:
                            addBehaviour(b0);
                            break;
                        case 1:
                            addBehaviour(b1);
                            break;
                        case 2:
                            addBehaviour(b2);
                            break;
                        case 3:
                            addBehaviour(b3);
                            break;
                        case 4:
                            addBehaviour(b4);
                            break;
                        default:
                            break;
                    }
                    frame.dispose();
                }
            }
        });

        panel.add(seleccionarButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private String obtenerNombreBehaviour(int index) {
        switch (index) {
            case 0:
                return b0.name;
            case 1:
                return b1.name;
            case 2:
                return b2.name;
            case 3:
                return b3.name;
            case 4:
                return b4.name;
            default:
                return "Nombre no disponible";
        }
    }

    private String obtenerDescripcionBehaviour(int index) {
        switch (index) {
            case 0:
                return b0.descripcion;
            case 1:
                return b1.descripcion;
            case 2:
                return b2.descripcion;
            case 3:
                return b3.descripcion;
            case 4:
                return b4.descripcion;
            default:
                return "Descripción no disponible";
        }
    }

    // Inicializamos los atributos del agente y le pasamos el entorno y su nodo
    // origen
    @Override
    protected void setup() {
        this.entorno = (Entorno) this.getArguments()[0];
        this.origen = (Nodo) this.getArguments()[1];
        this.visitedNodos = new ArrayList<>();
        this.visitedNodos.add(origen);
        this.contadorHeuristicaMayor = 0;
        this.cuenta = false;
        this.iters = 0;
        this.menores = 0;
        mostrarSelector();
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
}
