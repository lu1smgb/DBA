/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P2DBA;

import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.Agent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableroSwing extends JFrame {
    private int[][] matriz;
    private int agenteX;
    private int agenteY;
    private int circleRadius = 20;

    public TableroSwing(int[][] matriz) {
        this.matriz = matriz;
        agenteX = 0;
        agenteY = 0;

        setTitle("Tablero de Celdas");
        setSize(800, 820);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setAgentePosition(int x, int y) {
        agenteX = x;
        agenteY = y;
        repaint();  // Repintar el tablero cuando se actualiza la posición del agente
    }

    public int[][] getMatriz() {
        return matriz;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int cellWidth = getWidth() / matriz[0].length;
        int cellHeight = getHeight() / matriz.length;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if (matriz[i][j] == 0) {
                    g.setColor(Color.GREEN);
                } else if (matriz[i][j] == -1) {
                    g.setColor(Color.RED);
                }

                g.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
            }
        }

        g.setColor(Color.YELLOW);
        g.fillOval(agenteX * cellWidth + cellWidth / 2 - circleRadius / 2, agenteY * cellHeight + cellHeight / 2 - circleRadius / 2, circleRadius, circleRadius);
    }

    public static void main(String[] args) {
        Runtime rt = Runtime.instance();

        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.CONTAINER_NAME, "Javier_Linde_Container");

        ContainerController cc = rt.createAgentContainer(p);

        try {
            String agentName = "Practica2_Javier_Linde";
            AgentController ac = cc.createNewAgent(agentName, MyJadeAgent.class.getName(), null);

            ac.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
