package aquarium.memento;

import aquarium.plants.Immobile;
import aquarium.animals.Swimmable;

import java.awt.*;

public class Memento {

    private int size, x_front, y_front, horSpeed, verSpeed, eatCount;
    private double x_dir,y_dir;
    private Color col;
    private String ColorName, animalType;
    //private Swimmable swim = null;

    public Memento(Swimmable swin) {

        //this.swim = swin
        animalType = swin.getAnimalName();
        eatCount = swin.getEatCount();
        size = swin.getSize();
        x_front = swin.getX_front();
        x_dir = swin.getX_dir();
        y_front = swin.getY_front();
        y_dir = swin.getY_dir();
        horSpeed = swin.getHorSpeed();
        verSpeed = swin.getVerSpeed();
        col = swin.getColor();
        ColorName = swin.getColorName();

    }

    public Memento(Immobile imm) {

        animalType = imm.getPlanetName();
        size = imm.getSize();
        x_front = imm.getXfront();
        y_front = imm.getYfront();
        col = imm.getColor();
        ColorName = imm.getColorName();
    }

    public int getSize(){return size;}
    public Color getColor(){return col;}
    public int getXfront(){return x_front;}
    public int getYfront(){return y_front;}
    public int getHorSpeed(){return horSpeed;}
    public int getVerSpeed(){return verSpeed;}
    public String getAnimalName(){return animalType;}
    public String getColorName(){return ColorName;}
    public double getX_dir(){return x_dir;}
    public double getY_dir(){return y_dir;}
    public int getEatCount(){return eatCount;}
    public String getPlanetName() {return animalType;}
    //public HungerState getHungerStatus(){return swimObj.getHungerState();}

}
