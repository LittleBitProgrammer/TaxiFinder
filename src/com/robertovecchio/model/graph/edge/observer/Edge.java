package com.robertovecchio.model.graph.observer;

import com.robertovecchio.model.graph.node.Node;

import java.util.Objects;

/**
 * Classe utile ad astrarre il concetto di collegamento tra nodi (arco). Inoltre tale classe sarà utile all'algoritmo di
 * Dijkstra per risolvere il problema dello shortest path, pertanto l'edge, ovvero la lineaa sarà definita come
 * direzionale.
 * @author robertovecchio
 * @version 1.0
 * @since 14/01/2021
 */
public class Edge extends ArchObserver {
    private Node destination;
    private int weight;

    public Edge(T destination, int weight){
        this.destination = destination;
        this.weight = weight;

    }

    public T getDestination() {
        return destination;
    }

    public void setDestination(T destination) {
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    void update() {
        // stub
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge<?> edge = (Edge<?>) o;
        return weight == edge.weight &&
                Objects.equals(destination, edge.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, weight);
    }
}
