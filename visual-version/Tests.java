package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tests {
    public static void main(String[] args) {
        testCase1WithViz();
    }

    public static void testCase1(){
        Map<Integer, List<Integer>> graph = new HashMap<>();

        graph.put(0, List.of(1));
        graph.put(1, List.of(0));
        graph.put(2, List.of(0, 1, 3));
        graph.put(3, List.of(2));
        graph.put(4, List.of(1, 3, 5));
        graph.put(5, List.of(3, 6));
        graph.put(6, List.of(4));

        TarjanSCC tarjan = new TarjanSCC(graph, false);
        List<List<Integer>> sccs = tarjan.run();

        System.out.println("Компоненты сильной связности для теста 1:");
        for (List<Integer> component : sccs) {
            System.out.println(component);
        }
    }

    public static void testCase2(){
        Map<Integer, List<Integer>> graph = new HashMap<>();

        graph.put(1, List.of(2));
        graph.put(2, List.of(4));
        graph.put(3, List.of(1));
        graph.put(4, List.of(3));
        graph.put(5, List.of(3, 4, 6));
        graph.put(6, List.of(7));
        graph.put(7, List.of(5));
        graph.put(8, List.of(10));
        graph.put(9, List.of(8));
        graph.put(10, List.of(9, 11));
        graph.put(11, List.of(10, 13));
        graph.put(12, List.of(11));


        TarjanSCC tarjan = new TarjanSCC(graph, false);
        List<List<Integer>> sccs = tarjan.run();

        System.out.println("Компоненты сильной связности для теста 2:");
        for (List<Integer> component : sccs) {
            System.out.println(component);
        }
    }

    public static void testCase3(){
        Map<Integer, List<Integer>> graph = new HashMap<>();

        graph.put(1, List.of(2));
        graph.put(2, List.of(3, 4));
        graph.put(3, List.of(1));
        graph.put(4, List.of(5));
        graph.put(5, List.of(6));
        graph.put(6, List.of(5));
        graph.put(7, List.of(5, 8));
        graph.put(8, List.of(6, 9));
        graph.put(9, List.of(10));
        graph.put(10, List.of(7, 8));

        TarjanSCC tarjan = new TarjanSCC(graph, false);
        List<List<Integer>> sccs = tarjan.run();

        System.out.println("Компоненты сильной связности для теста 3:");
        for (List<Integer> component : sccs) {
            System.out.println(component);
        }
    }

    public static void testCase1WithViz() {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        graph.put(0, List.of(1));
        graph.put(1, List.of(0));
        graph.put(2, List.of(0, 1, 3));
        graph.put(3, List.of(2));
        graph.put(4, List.of(1, 3, 5));
        graph.put(5, List.of(3, 6));
        graph.put(6, List.of(4));

        GraphVisualizer.clearSnapshots();

        TarjanSCC tarjan = new TarjanSCC(graph, true); // включаем запись шагов
        List<List<Integer>> sccs = tarjan.run();

        System.out.println("Компоненты сильной связности для теста 1:");
        for (List<Integer> component : sccs) {
            System.out.println(component);
        }

        GraphVisualizer.showSnapshots();
    }

}
