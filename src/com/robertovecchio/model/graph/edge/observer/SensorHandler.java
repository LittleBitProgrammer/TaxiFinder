package com.robertovecchio.model.graph.edge.observer;

import java.util.ArrayList;
import java.util.List;

public class SensorHandler implements Sensorable {
    private final List<ArchObserver> observers = new ArrayList<>();
    private int trafficState;

    public int getState(){
        return this.trafficState;
    }

    public void setTrafficState(int trafficState){
        this.trafficState = trafficState;
        notifyObs(trafficState);
    }

    @Override
    public void attach(ArchObserver archObserver) {
        observers.add(archObserver);
    }

    @Override
    public void detach(ArchObserver archObserver) {
        observers.remove(archObserver);
    }

    @Override
    public void notifyObs(int trafficState) {
        for (ArchObserver observer : this.observers){
            observer.update(trafficState);
        }
    }
}
