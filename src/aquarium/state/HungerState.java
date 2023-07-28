package aquarium.state;

import aquarium.animals.Swimmable;

public interface HungerState {

    public void doAction(Swimmable objSwim);
    public String toString();

}
