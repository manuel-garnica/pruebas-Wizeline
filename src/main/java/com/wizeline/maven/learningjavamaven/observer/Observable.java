package com.wizeline.maven.learningjavamaven.observer;

import com.wizeline.maven.learningjavamaven.observer.observers.Observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private List<Observer> observers = new ArrayList<>();
    private boolean fueEditado;

    public boolean isFueEditado() {
        return fueEditado;
    }

    public void setFueEditado(boolean fueEditado) {
        this.fueEditado = fueEditado;
        notifyAllObservers();
    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update(this.fueEditado);
        }
    }
}