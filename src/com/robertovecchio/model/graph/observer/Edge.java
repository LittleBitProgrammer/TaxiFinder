package com.robertovecchio.model.graph.observer;

public class Edge<T, W> extends ArchObserver {
    private T source;
    private T destination;
    private W weight;
    private double lengthWeight;

    public Edge(T source, T destination, W weight, double lengthWeight){
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.lengthWeight = lengthWeight;

    }

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }

    public T getDestination() {
        return destination;
    }

    public void setDestination(T destination) {
        this.destination = destination;
    }

    public W getWeight() {
        return weight;
    }

    public void setWeight(W weight) {
        this.weight = weight;
    }

    public double getLengthWeight() {
        return lengthWeight;
    }

    public void setLengthweight(double lengthWeight) {
        this.lengthWeight = lengthWeight;
    }

    @Override
    void update() {
        // stub
    }
}
