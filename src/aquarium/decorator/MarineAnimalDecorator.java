package aquarium.decorator;

import java.awt.*;

public class MarineAnimalDecorator implements MarineAnimal{
    private MarineAnimal animal;

    public MarineAnimalDecorator(MarineAnimal animal) {
        this.animal = animal;
    }

    @Override
    public void PaintFish(Color c) {
        animal.PaintFish(c);
    }
}