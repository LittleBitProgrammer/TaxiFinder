package com.robertovecchio.model.graph.observer;

public abstract class ArchObserver {
    protected Sensorable sensorable;

    abstract void update();
}
