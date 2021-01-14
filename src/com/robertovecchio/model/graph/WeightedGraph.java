package com.robertovecchio.model.graph;

import com.robertovecchio.model.graph.observer.Edge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WeightedGraph<T> implements Serializable {

    // Tipo numerico Long utile alla serializzazione
    private final static long serialVersionUID = 6L;

    int vertices;  // numero vertici
    List<LinkedList<Edge<T>>> adjacencylist; // array di linkedList

    public WeightedGraph(int vertices){
        this.vertices = vertices;
        adjacencylist = new ArrayList<>();

        // Inizializziamo una linked list per ogni vertice
        for (int i = 0; i <vertices ; i++) {
            adjacencylist.add(new LinkedList<>());
        }
    }

    public WeightedGraph(){
        adjacencylist = new ArrayList<>();
    }

    public void addNode(T node){
        adjacencylist.add(new LinkedList<>());
    }

    public void addEdge(T source, T destination, int weight, double lengthWeight) {
        Edge<T> edge = new Edge<>(source, destination, weight, lengthWeight);
        adjacencylist.get(adjacencylist.indexOf(source)).addFirst(edge); //for directed graph
    }

    public void printGraph(){
        for (LinkedList<Edge<T>> linkedList : this.adjacencylist){
            for (Edge<T> edge: linkedList){
                System.out.println("Vertex " + edge.getSource() + "is connected to " + edge.getDestination() +
                        " With weight " + edge.getWeight());
            }
        }
    }
}
