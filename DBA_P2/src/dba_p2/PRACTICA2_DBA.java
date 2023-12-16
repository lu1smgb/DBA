package dba_p2;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

// Clase encargada de leer los mapas disponibles
public class PRACTICA2_DBA extends JFrame {
    private JPanel sidePanel;
    private MapPreviewPanel previewPanel;
    private JList<String> mapList;

    public PRACTICA2_DBA() {
        initComponents();
        loadMaps();
    }

    private void loadMaps() {
        File mapsFolder = new File("maps");

        if (!mapsFolder.exists() || !mapsFolder.isDirectory()) {
            System.err.println("Error: La carpeta de mapas no existe o no es un directorio.");
            return;
        }

        File[] mapFiles = mapsFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (mapFiles != null) {
            String[] mapNames = new String[mapFiles.length];

            for (int i = 0; i < mapFiles.length; i++) {
                mapNames[i] = mapFiles[i].getName();
            }

            mapList.setListData(mapNames);
        } else {
            System.err.println("Error al obtener la lista de archivos de mapas.");
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Map Viewer");
        setSize(800, 600);

        sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());

        mapList = new JList<>();
        mapList.setForeground(Color.GREEN);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        mapList.setCellRenderer(renderer);

        sidePanel.add(new JScrollPane(mapList), BorderLayout.CENTER);
        sidePanel.setPreferredSize(new Dimension(250, getHeight()));
        sidePanel.setBackground(Color.BLUE);

        previewPanel = new MapPreviewPanel();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sidePanel, BorderLayout.WEST);
        getContentPane().add(previewPanel, BorderLayout.CENTER);

        mapList.addListSelectionListener(e -> {
            String selectedMap = "maps/" + mapList.getSelectedValue();
            if (selectedMap != null) {
                try (BufferedReader br = new BufferedReader(new FileReader(selectedMap))) {
                    int[][] mapMatrix = readMapMatrix(br);
                    previewPanel.setMapMatrix(mapMatrix);
                    previewPanel.repaint();
                    previewPanel.showSelectMapButton(true);
                } catch (IOException | NumberFormatException ex) {
                    ex.printStackTrace();
                }
            } else {
                previewPanel.showSelectMapButton(false);
            }
        });
    }

    private int[][] readMapMatrix(BufferedReader br) throws IOException {
        int rows = Integer.parseInt(br.readLine());
        int cols = Integer.parseInt(br.readLine());

        int[][] mapMatrix = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            String[] rowValues = br.readLine().split("\t");
            for (int j = 0; j < cols; j++) {
                mapMatrix[i][j] = Integer.parseInt(rowValues[j]);
            }
        }

        return mapMatrix;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear la instancia de DynamicBehaviorAgent

            // Crear la instancia de MapViewer2 pasando la instancia de DynamicBehaviorAgent
            PRACTICA2_DBA mapViewer = new PRACTICA2_DBA();

            // Hacer visible el MapViewer2
            mapViewer.setVisible(true);
        });
    }
    // Configurar la plataforma Jade

}
