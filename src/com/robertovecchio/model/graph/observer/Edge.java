package com.robertovecchio.model.graph.observer;

public class Edge<T> extends ArchObserver {
    private T source;
    private T destination;
    private int weight;
    private double lengthWeight;

    public Edge(T source, T destination, int weight, double lengthWeight){
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
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
