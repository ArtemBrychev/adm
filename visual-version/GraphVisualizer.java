package com.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.ext.JGraphXAdapter;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class GraphVisualizer {
    private static final List<JComponent> frames = new ArrayList<>();

    public static void clearSnapshots() {
        frames.clear();
    }

    public static void addSnapshot(Map<Integer, List<Integer>> graphData, List<List<Integer>> sccs, String title) {
        // Построение графа
        Graph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
        for (Integer v : graphData.keySet()) g.addVertex(v.toString());
        for (Map.Entry<Integer, List<Integer>> e : graphData.entrySet()) {
            for (Integer w : e.getValue()) g.addEdge(e.getKey().toString(), w.toString());
        }

        JGraphXAdapter<String, DefaultEdge> graphAdapter = new JGraphXAdapter<>(g);
        mxGraphComponent component = new mxGraphComponent(graphAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        new mxCircleLayout(graphAdapter).execute(graphAdapter.getDefaultParent());

        // Раскраска SCC
        int colorIdx = 0;
        Color[] colors = {Color.PINK, Color.CYAN, Color.ORANGE, Color.LIGHT_GRAY, Color.YELLOW, Color.GREEN};

        for (List<Integer> scc : sccs) {
            Color c = colors[colorIdx++ % colors.length];
            for (Integer v : scc) {
                Object cell = graphAdapter.getVertexToCellMap().get(v.toString());
                if (cell != null) {
                    graphAdapter.setCellStyles(mxConstants.STYLE_FILLCOLOR, toHex(c), new Object[]{cell});
                }
            }
        }

        // Оборачиваем с заголовком
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(title, SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        frames.add(panel);
    }

    public static void showSnapshots() {
        if (frames.isEmpty()) return;

        JFrame frame = new JFrame("Graph Snapshots");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel container = new JPanel(new BorderLayout());
        JButton prev = new JButton("Previous");
        JButton next = new JButton("Next");
        JLabel indexLabel = new JLabel("1 / " + frames.size(), SwingConstants.CENTER);

        JPanel nav = new JPanel(new BorderLayout());
        nav.add(prev, BorderLayout.WEST);
        nav.add(indexLabel, BorderLayout.CENTER);
        nav.add(next, BorderLayout.EAST);

        container.add(nav, BorderLayout.NORTH);
        container.add(frames.get(0), BorderLayout.CENTER);

        final int[] index = {0};

        prev.addActionListener(e -> {
            if (index[0] > 0) {
                container.remove(1);
                container.add(frames.get(--index[0]), BorderLayout.CENTER);
                indexLabel.setText((index[0] + 1) + " / " + frames.size());
                frame.revalidate();
                frame.repaint();
            }
        });

        next.addActionListener(e -> {
            if (index[0] < frames.size() - 1) {
                container.remove(1);
                container.add(frames.get(++index[0]), BorderLayout.CENTER);
                indexLabel.setText((index[0] + 1) + " / " + frames.size());
                frame.revalidate();
                frame.repaint();
            }
        });

        frame.setContentPane(container);
        frame.setVisible(true);
    }

    private static String toHex(Color c) {
        return String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
    }
}
