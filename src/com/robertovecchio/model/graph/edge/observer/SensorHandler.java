package com.robertovecchio.model.graph.edge.observer;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SensorHandler implements Sensorable {
    private final List<ArchObserver> observers = new ArrayList<>();
    private LocalTime trafficState;

    public LocalTime getState(){
        return this.trafficState;
    }

    public void setTrafficState(LocalTime trafficState){
        this.trafficState = trafficState;
        notifyObs();
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
    public void notifyObs() {
        for (ArchObserver observer : this.observers){
            observer.update();
        }
    }
}
