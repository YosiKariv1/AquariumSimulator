package aquarium.memento;

import aquarium.memento.Memento;

import java.util.HashSet;


public class CareTaker {
    public static HashSet<Memento> swimmableMementoList;
    public static HashSet<Memento> plantesMementoList;

    public CareTaker()
    {
        swimmableMementoList = new HashSet<Memento>();
        plantesMementoList = new HashSet<Memento>();
    }

    public void addSwimmableMemento(Memento memento)
    {
        swimmableMementoList.add(memento);
    }
    public void addImmobileMemento(Memento memento)
    {
        plantesMementoList.add(memento);
    }
}