package aquarium.plants;

import aquarium.factory.SeaCreature;

import java.awt.*;

public abstract class Immobile<GREEN> implements SeaCreature {
    private String name;
    private int size, x, y;
    private Color clr = Color.GREEN;
    private String ColorName = "Green";

    public Immobile(int size, int x, int y, String name) {
        this.name = name;
        this.size = size;
        this.x = x;
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setClr(Color clr) {
        this.clr = clr;
    }

    public String getPlanetName() {
        return name;
    }

    public String getColorName() {
        return "green";
    }

    public Color getColor() {
        return clr;
    }

    public int getSize() {
        return this.size;
    }

    public int getXfront() {
        return this.x;
    }

    public int getYfront() {
        return this.y;
    }

    public Color getColorPlanet() {
        return this.clr;
    }
}