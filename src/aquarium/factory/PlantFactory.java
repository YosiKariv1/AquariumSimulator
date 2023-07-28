package aquarium.factory;

import aquarium.plants.Laminaria;
import aquarium.plants.Zostera;
import aquarium.gui.AquaPanel;

import java.awt.*;

public class PlantFactory implements AbstractSeaFactory {
    private int size,x,y;
    private AquaPanel panel;
    private Color colorr;

    public PlantFactory(AquaPanel panel,int size,int x,int y)
    {
        this.panel=panel;
        this.size=size;
        this.x = x;
        this.y = y;
        this.colorr = Color.green;
    }

    public SeaCreature produceSeaCreature(String type)
    {
        if(type.equalsIgnoreCase("Laminaria"))
            return new Laminaria(panel,size,x,y);

        else if(type.equalsIgnoreCase("Zostera")){
            return new Zostera(panel,size,x,y);
        }
        return null;
    }
}
