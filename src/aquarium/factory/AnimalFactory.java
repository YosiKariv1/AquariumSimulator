package aquarium.factory;

import aquarium.animals.Fish;
import aquarium.animals.Jellyfish;
import aquarium.gui.AquaPanel;

import java.awt.*;

public class AnimalFactory implements AbstractSeaFactory {
    private int size, horSpeed, verSpeed;
    private Color col;
    private AquaPanel panel;
    private String colorName;
    private int foodFreq;

    public AnimalFactory(AquaPanel panel, int size, int horSpeed, int verSpeed, Color col, String colorName, int foodFreq) {
        this.panel = panel;
        this.size = size;
        this.horSpeed = horSpeed;
        this.verSpeed = verSpeed;
        this.col = col;
        this.colorName = colorName;
        this.foodFreq = foodFreq;
    }

    /**
     * interface with one method to create sea creature :swimmable\immobile object
     *
     * @return the object by the message
     */
    @Override
    public SeaCreature produceSeaCreature(String type) {
        if (type.equalsIgnoreCase("Fish"))
        {
            return new Fish(panel, size, horSpeed, verSpeed, col, colorName, foodFreq);
        } else if (type.equalsIgnoreCase("Jellyfish"))
            return new Jellyfish(panel, size, horSpeed, verSpeed, col, colorName, foodFreq);

        return null;
    }
}
