package com.robertovecchio.model.graph.edge.observer;

public abstract class ArchObserver {
    protected Sensorable sensorable;

    abstract void update(int trafficState);
}
