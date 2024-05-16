//package com.hossam.gui;
//
//import java.awt.*;
//import javax.swing.*;
//import javax.swing.border.*;
//import org.graphstream.graph.*;
//import org.graphstream.graph.implementations.*;
//import org.graphstream.ui.swing_viewer.SwingViewer;
//import org.graphstream.ui.swing_viewer.util.MouseOverMouseManager;
//import org.graphstream.ui.view.*;
//
//public class MainGui {
//
//    public static void main(String[] args) {
//        Graph graph = new SingleGraph("Tutorial");
//
//        graph.addNode("A");
//        graph.addNode("B");
//        graph.addNode("C");
//        graph.addEdge("AB", "A", "B");
//        graph.addEdge("BC", "B", "C");
//        graph.addEdge("CA", "C", "A");
//        graph.setAttribute("ui.stylesheet", styleSheet);
//        graph.setAttribute("ui.quality");
//        graph.setAttribute("ui.antialias");
//
//        // Enable auto layout
//        // Enable auto layout
//        Viewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
//        viewer.enableAutoLayout();
//
//        // Create a view and set the MouseOverMouseManager
//        View view = viewer.addDefaultView(false); // false indicates the view is not in a JFrame by itself
//        view.setMouseManager(new MouseOverMouseManager());
//
//
//        // Create a Swing JFrame to display the graph
//        JFrame frame = new JFrame("GraphStream Swing Viewer");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1000, 1000);
//
//        // Create a panel to hold the text, image, and graph
//        JPanel panel = new JPanel(new BorderLayout());
//
//        // Create a sub-panel for the text and image
//        JPanel topPanel = new JPanel();
//        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
//
//        // Add text to the top panel
//        JLabel label = new JLabel("Graph Visualization");
//        label.setAlignmentX(Component.CENTER_ALIGNMENT);
//        topPanel.add(label);
//
//        // Add an image to the top panel
//        ImageIcon imageIcon = new ImageIcon("src/main/images/haw_logo.png");
//        JLabel imageLabel = new JLabel(imageIcon);
//        imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        topPanel.add(imageLabel);
//
//        // Add the top panel to the main panel
//        panel.add(topPanel, BorderLayout.NORTH);
//
//        // Add the graph view to the main panel
//        panel.add((Component) view, BorderLayout.CENTER);
//
//        // Add the main panel to the frame
//        frame.add(panel);
//
//        frame.setVisible(true);
//    }
//    protected static String styleSheet =
//            "node {" +
//                    "   shape: circle;" +
//                    "   size: 20px;" +
//                    "   fill-color: rgba(103,143,212, 150);" + // Solid inner circle
//                    "   stroke-mode: plain;" +
//                    "   stroke-color: rgba(1,68,183, 150);" +
//                    "   stroke-width: 3px;" +
//                    "}" +
//                    "node.shadow {" +
//                    "   size: 28px;" + // Larger size to create the shadow effect
//                    "   fill-color: rgba(1,68,183, 50);" + // Semi-transparent shadow color
//                    "}" +
//                    "edge {" +
//                    "   fill-color: rgba(153,180,226, 150);" +
//                    "   fill-mode: dyn-plain;" +
//                    "   size: 4px;" +
//                    "}";
//
//}