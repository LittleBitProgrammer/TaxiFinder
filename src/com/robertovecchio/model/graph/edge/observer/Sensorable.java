package com.robertovecchio.model.graph.observer;

public interface Sensorable {
     void attach(ArchObserver archObserver);
     void detach(ArchObserver archObserver);
     void notifyObs();
}
