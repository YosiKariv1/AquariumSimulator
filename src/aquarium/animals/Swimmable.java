package aquarium.animals;

import aquarium.gui.AquaPanel;
import aquarium.events.Observer;
import aquarium.state.Satiated;
import aquarium.factory.SeaCreature;
import aquarium.state.HungerState;
import aquarium.state.Hungry;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.CyclicBarrier;

/**
 * This class represent the Swimmable animal. it extends from Thread class and it have multiple abstract classes that will be implemented in
 * Fish and Jellyfish classes.
 * @version 3.3 2021
 * @author Yosi kariv 205507460
 *         Amit Cohen 316432137
 */
public abstract class Swimmable extends Thread implements SeaCreature, Cloneable{

    protected static Boolean food = false;
    protected static AquaPanel callback = null;
    protected int horSpeed; //horizontal speed of the animal.
    protected int verSpeed; //vertical speed of the animal.
    protected int foodFreq;
    protected CyclicBarrier barrier;
    private PropertyChangeSupport support;
    protected int x_dir, y_dir, x_front, y_front;
    protected AquaPanel panel;
    public HungerState currentState = new Satiated();
    protected long timePassed;
    private int BeforeHungry;
    private Observer observer;


    /**
     * Constructor for initialize the verSpeed and the horSpeed to 0.
     * @return not return anything.
     */
    public Swimmable()
    {
        horSpeed = 0;
        verSpeed = 0;
        horSpeed = 0;
        verSpeed = 0;
        x_dir = 0;
        y_dir = 0;
        x_front = 0;
        y_front = 0;
        panel = null;
    }

    /**
     * Constructor for initialize the verSpeed and the horSpeed to given values.
     * @return not return anything.
     */
    public Swimmable( int horSpeed, int verSpeed, AquaPanel panel, int foodfreq)
    {
        this.horSpeed = horSpeed;
        this.verSpeed = verSpeed;
        this.panel = panel;
        this.foodFreq = foodfreq;
        support = new PropertyChangeSupport(this);
    }

    /**
     * Getter to return the horizontal speed of the animal..
     * @return horSpeed
     */
    public int getHorSpeed(){ return horSpeed; }

    /**
     * Getter to return the vertical speed of the animal.
     * @return verSpeed
     */
    public int getVerSpeed(){ return verSpeed; }


    /**
     * Setter to set the horSpeed.
     * @params int hor
     */
    public void setHorSpeed(int hor) { horSpeed = hor; }

    /**
     * Setter to set the verSpeed.
     * @params int ver
     */
    public void setVerSpeed(int ver) { verSpeed = ver; }

    /**
     * Abstract methods that will be implemented in the Fish anf Jellyfish classes.
     * @return not return anything.
     */
    abstract public int getX_dir() ;
    abstract public void setSize(int size) ;
    abstract public int getY_dir();
    abstract public int getY_front() ;
    abstract public int getX_front() ;
    abstract public void setY_dir(int y_dir);
    abstract public void setX_front(int x_front);
    abstract public void setX_dir(int x_dir);
    abstract public int getFoodFreq();
    abstract public void setEatCount(int eatCount);
    abstract public void setY_front(int y_front);
    abstract public String getAnimalName();
    abstract public void setSuspend();
    abstract public void setResume();
    abstract public void setBarrier(CyclicBarrier b);
    abstract public int getSize();
    abstract public void eatInc();
    abstract public int getEatCount();
    abstract public String getColorName();
    abstract public Color getColor();
    abstract public void setColor(Color color);
    abstract public Swimmable clone();
    abstract public void Upgrade(int size, int horSpeed, int verSpeed, Color col, String ColorName);



    public static void setCallBack(AquaPanel p)
    {
        callback = p;
    }

    /**
     * Set the swimming direction of the animal to the middle of the screen.
     */
    protected void swimToMiddle()
    {
        // Calculate appropriate direction to point to the middle
        if (x_front > panel.getWidth()/2)
            x_dir = -1;
        else
            x_dir = 1;

        if (y_front > panel.getHeight()/2)
            y_dir = -1;
        else
            y_dir = 1;
    }

    /**
     * Check if the fish has reached the food.
     *
     * @return				True if fish caught the food.
     * 						False otherwise.
     */
    protected Boolean reachedFood()
    {
        final double catchDistance = 5.0;		// The length of the bounds of the food for the collision detection.
        int middleX;

        if (this instanceof Fish)
        {
            if (x_dir == 1)
                middleX = x_front - getSize()/2;
            else
                middleX = x_front + getSize()/2;
        }
        else
            middleX = x_front;

        if (middleX - getSize()/2 > panel.getWidth()/2 + catchDistance)
            return false;
        if (middleX + getSize()/2 < panel.getWidth()/2 - catchDistance)
            return false;
        if (y_front - getSize()/4 > panel.getHeight()/2 + catchDistance)
            return false;
        if (y_front + getSize()/4 < panel.getHeight()/2 - catchDistance)
            return false;

        return true;
    }

    /**
     * Check collision of the fish with the screen's borders, and change the direction if needed.
     */
    protected void collisionWithBorders()
    {
        if (x_front + getSize()/2 <= 0)
        {
            x_dir = 1;
            x_front = getSize()/2;
        }
        else if (x_front - getSize()/2 >= panel.getWidth())
        {
            x_dir = -1;
            x_front = panel.getWidth() - getSize()/2;
        }
        if (y_front - getSize()/4 <= 0)
            y_dir = 1;
        else if (y_front + getSize()/4 >= panel.getHeight())
            y_dir = -1;
    }

    /**
     * Update the position of the animal and make it swim in the appropriate direction.
     */
    protected void swim()
    {
        this.x_front += this.x_dir * this.horSpeed;
        this.y_front += this.y_dir * this.verSpeed;
    }
    /**
     * Rotate the direction of the fish. Use it to make the fish swim to the opposite direction.
     */
    public void rotateDirection()
    {
        this.x_dir *= -1;
        this.y_dir *= -1;
    }

    public static void setFood(Boolean b) { food = b; }

    public void addPropertyChangeListener(PropertyChangeListener pcl)
    {
        support.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl)
    {
        support.removePropertyChangeListener(pcl);
    }


    public void setState(HungerState state) {
        currentState=state;
    }
    public HungerState getHungerState() { return currentState;}

    public void checkTimeForHungry() {
        if (!(getHungerState() instanceof Hungry))
            if (System.currentTimeMillis() - this.timePassed >= foodFreq*1000) {
                setState(new Hungry());
                this.notifyObserver(this.getColorName() + " " + this.getAnimalName() + " is hungry now! Please feed him.");
            }
    }

    public void resetTimeForHungry() {
        this.timePassed = System.currentTimeMillis();

    }

    public void notifyObserver(String msg) {
        if (this.observer != null)
            this.observer.update(msg);
    }

}
