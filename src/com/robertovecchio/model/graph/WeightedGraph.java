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

    public int getNumberOfNode(){
        return this.adjacencylist.size();
    }

    public void addNode(){
        adjacencylist.add(new LinkedList<>());
    }

    public void removeNode(T node){
        for (int i = 0; i < adjacencylist.size(); i++) {
            LinkedList<Edge<T>> linked = adjacencylist.get(i);
            if (linked.get(i).getSource().equals(node)) {
                this.adjacencylist.remove(i);
                break;
            }
        }
    }

    public void addEdge(T source, T destination, int weight, double lengthWeight) {
        boolean isContained = false;

        Edge<T> edge = new Edge<>(source, destination, weight, lengthWeight);
        for (int i = 0; i < adjacencylist.size(); i++){
            LinkedList<Edge<T>> linked = adjacencylist.get(i);
            if (linked.get(i).getSource().equals(source)){
                linked.addFirst(edge);
                isContained = true;
                break;
            }
        }

        if (!isContained){
            for (LinkedList<Edge<T>> linked : adjacencylist) {
                if (linked.isEmpty()) {
                    linked.addFirst(edge);
                }
            }
        }
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
