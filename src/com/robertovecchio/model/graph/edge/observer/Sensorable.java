package com.robertovecchio.model.graph.edge.observer;

public interface Sensorable {
     void attach(ArchObserver archObserver);
     void detach(ArchObserver archObserver);
     void notifyObs(int trafficState);
}
