package org.example;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class QuickestRouteFinder {
    private Map<Character, Map<Character, Integer>> graph;

    public QuickestRouteFinder(String routeString) {
        buildGraph(routeString);
    }

    private void buildGraph(String routeString) {
        graph = new HashMap<>();
        String[] routes = routeString.split(" ");

        for (String route : routes) {
            char from = route.charAt(0);
            char to = route.charAt(1);
            int distance = Integer.parseInt(route.substring(2));

            graph.putIfAbsent(from, new HashMap<>());
            graph.get(from).put(to, distance);
        }
    }

    public int findQuickestRoute(char start, char end) {
        Map<Character, Integer> distances = new HashMap<>();
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(key -> distances.get(key.station)));

        distances.put(start, 0);
        minHeap.offer(new Node(start, 0));

        while (!minHeap.isEmpty()) {
            Node current = minHeap.poll();
            char currentStation = current.station;
            int currentDistance = current.distance;

            if (currentStation == end) {
                return currentDistance;
            }

            if (graph.containsKey(currentStation)) {
                for (Map.Entry<Character, Integer> neighbor : graph.get(currentStation).entrySet()) {
                    char nextStation = neighbor.getKey();
                    int newDistance = currentDistance + neighbor.getValue();

                    if (!distances.containsKey(nextStation) || newDistance < distances.get(nextStation)) {
                        distances.put(nextStation, newDistance);
                        minHeap.offer(new Node(nextStation, newDistance));
                    }
                }
            }
        }

        // If no route is found
        return -1;
    }

    private static class Node {
        char station;
        int distance;

        Node(char station, int distance) {
            this.station = station;
            this.distance = distance;
        }
    }

    public static void main(String[] args) {
        String routeString = "AB5 BC4 CD8 DC8 DE6 AD5 CE2 EB3 AE7";
        QuickestRouteFinder routeFinder = new QuickestRouteFinder(routeString);

        char startStation = 'A';
        char endStation = 'E';

        int quickestRoute = routeFinder.findQuickestRoute(startStation, endStation);

        if (quickestRoute != -1) {
            System.out.println("Quickest route from " + startStation + " to " + endStation + " is: " + quickestRoute + " KM");
        } else {
            System.out.println("No route found from " + startStation + " to " + endStation);
        }
    }
}