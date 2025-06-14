package com.example;

import java.util.*;

public class TarjanSCC {
    private Map<Integer, List<Integer>> graph;
    private int index = 0;
    private Stack<Integer> stack = new Stack<>();
    private Map<Integer, Integer> indices = new HashMap<>();
    private Map<Integer, Integer> lowlink = new HashMap<>();
    private Set<Integer> onStack = new HashSet<>();
    private List<List<Integer>> result = new ArrayList<>();

    private boolean captureSteps;

    public TarjanSCC(Map<Integer, List<Integer>> graph, boolean captureSteps) {
        this.graph = graph;
        this.captureSteps = captureSteps;
    }

    public List<List<Integer>> run() {
        if (captureSteps) {
            GraphVisualizer.addSnapshot(graph, new ArrayList<>(), "Initial Graph");
        }
        for (Integer node : graph.keySet()) {
            if (!indices.containsKey(node)) {
                strongConnect(node);
            }
        }
        if (captureSteps) {
            GraphVisualizer.addSnapshot(graph, result, "After Algorithm");
        }
        return result;
    }

    private void strongConnect(int v) {
        indices.put(v, index);
        lowlink.put(v, index);
        index++;
        stack.push(v);
        onStack.add(v);

        for (int w : graph.getOrDefault(v, new ArrayList<>())) {
            if (!indices.containsKey(w)) {
                strongConnect(w);
                lowlink.put(v, Math.min(lowlink.get(v), lowlink.get(w)));
            } else if (onStack.contains(w)) {
                lowlink.put(v, Math.min(lowlink.get(v), indices.get(w)));
            }
        }

        if (lowlink.get(v).equals(indices.get(v))) {
            List<Integer> component = new ArrayList<>();
            int w = stack.pop();
            onStack.remove(w);
            component.add(w);

            while (w != v) {
                w = stack.pop();
                onStack.remove(w);
                component.add(w);
            }

            result.add(component);
            if (captureSteps) {
                GraphVisualizer.addSnapshot(graph, new ArrayList<>(result), "Found SCC: " + component);
            }
        }
    }
}
