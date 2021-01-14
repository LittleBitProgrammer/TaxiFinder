package com.robertovecchio.model.graph;

import com.robertovecchio.model.graph.observer.Edge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WeightedGraph<T, W> {
    int vertices;  // numero vertici
    List<LinkedList<Edge<T, W>>> adjacencylist; // array di linkedList

    public WeightedGraph(int vertices){
        this.vertices = vertices;
        adjacencylist = new ArrayList<>();

        // Inizializziamo una linked list per ogni vertice
        for (int i = 0; i <vertices ; i++) {
            adjacencylist.add(new LinkedList<>());
        }
    }

    public void addEdge(T source, T destination, W weight, double lengthWeight) {
        Edge<T, W> edge = new Edge<>(source, destination, weight, lengthWeight);
        adjacencylist.get(adjacencylist.indexOf(source)).addFirst(edge); //for directed graph
    }

    public void printGraph(){
        for (LinkedList<Edge<T,W>> linkedList : this.adjacencylist){
            for (Edge<T,W> edge: linkedList){
                System.out.println("Vertex " + edge.getSource() + "is connected to " + edge.getDestination() +
                        " With weight " + edge.getWeight());
            }
        }
    }
}
