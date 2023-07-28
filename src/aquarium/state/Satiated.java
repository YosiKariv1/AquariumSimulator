package aquarium.state;

import aquarium.animals.Swimmable;
import aquarium.state.HungerState;

public class Satiated implements HungerState {

    @Override
    public String toString(){
        return "Satiated";
    }

    @Override
    public void doAction(Swimmable objSwim) {
        objSwim.setState(this);
    }

}