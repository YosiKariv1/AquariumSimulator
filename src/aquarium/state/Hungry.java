package aquarium.state;

import aquarium.animals.Swimmable;

public class Hungry implements HungerState {

    @Override
    public String toString() {
        return "Hungry";
    }

    public void doAction(Swimmable other) {
        other.setState(this);
    }
}