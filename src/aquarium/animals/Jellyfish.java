package aquarium.animals;

import aquarium.gui.AquaPanel;
import aquarium.decorator.MarineAnimal;

import java.awt.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * This class represent the Jellyfish.it contains multiple methods for incresing the size of the jellyfish and draw the jellyfish.
 * In this class we also make the Jellyfish move on the screen.
 * @version 3.3 2021
 * @author Yosi kariv 205507460
 *         Amit Cohen 316432137
 */
public class Jellyfish extends Swimmable implements MarineAnimal
{
    //class variables
    private final int EAT_DISTANCE = 4;
    private int size; //size of the Jellyfish
    private Color col; //Color field for the color of the Jellyfish.
    private int eatCount; // eatCount to count the food of the Jellyfish.
    private boolean suspend = false; //boolean variable for the suspention of the animals.
    private String color; //jellyfish color


    /**
     * Constructor for initialize the variables of the Jellyfish.
     * @return not return anything.
     */
    public Jellyfish(AquaPanel panel, int size, int horSpeed, int verSpeed, Color col, String colorName, int foodFreq) {
        super(horSpeed, verSpeed, panel, foodFreq);
        this.panel = panel;
        this.size = size;
        this.x_front = panel.getWidth()/2;
        this.y_front = panel.getHeight()/2;
        this.col = col;
        this.x_dir = 1;
        this.y_dir = 1;
        this.eatCount = 0;
        this.color = colorName;
        this.foodFreq = foodFreq;
    }

    /**
     * Setter to set the col to be the newColor.
     * @params newColor
     * @return not return anything.
     */
    public void setCol(Color newColor)
    {
        col = newColor;
    }

    /**
     * Getter to return the name of the animal.
     * @return the animal name.
     */
    public String getAnimalName()
    {
        return "Jellyfish";
    }


    /**
     * drawCreature method to draw the Jellyfish.
     * @params g
     * @return not return anything.
     */
    @Override
    public void drawCreature(Graphics g)
    {
        int numLegs;
        if(size<40)
            numLegs = 5;
        else if(size<80)
            numLegs = 9;
        else numLegs = 12;
        g.setColor(col);
        g.fillArc(x_front - size/2, y_front - size/4, size, size/2, 0, 180);
        for(int i = 0; i < numLegs; i++)
            g.drawLine(x_front - size/2 + size/numLegs + size*i/(numLegs+1), y_front, x_front - size/2 + size/numLegs + size*i/(numLegs+1), y_front+size/3);
    }

    /**
     * setSuspend method to change the boolean variable to true and make the suspension.
     * @return not return anything.
     */
    @Override
    public void setSuspend() {
        this.suspend = true;
    }

    /**
     * setResume method to change the boolean variable to true and make the resume.
     * @return not return anything.
     */
    @Override
    public void setResume()
    {
        this.suspend = false;
        synchronized (this)
        {
            notify();
        }
    }

    @Override
    public void setBarrier(CyclicBarrier b) {
        this.swimToMiddle(); // Calculate the direction to the middle
        this.barrier = b; // Set the CyclicBarrier for food racing synchronization
    }

    /**
     * Getter to return the eatCount of the Jellyfish.
     * @return eatCount
     */
    public  int getEatCount(){ return eatCount; }

    /**
     * Getter to return the size of the fish.
     * @return size
     */
    public int getSize(){ return size; }

    /**
     * Getter to return the color of the fish.
     * @return col
     */
    @Override
    public String getColorName()
    {
        return color;
    }

    @Override
    public Color getColor() {
        return col;
    }

    @Override
    public Swimmable clone() {
        return new Jellyfish(panel, size, horSpeed, verSpeed, col, color, foodFreq);
    }

    @Override
    public void Upgrade(int size, int horSpeed, int verSpeed, Color col, String ColorName)
    {
        this.size = size;
        this.horSpeed = horSpeed;
        this.verSpeed = verSpeed;
        this.col = col;
        this.color = ColorName;
    }


    /**
     * Increase the amount of food the jellyfish ate by 1.
     * Increase jellyfish's size if he ate enough food.
     */
    @Override
    public void eatInc() {
        this.eatCount++;

        if(this.eatCount % this.EAT_DISTANCE == 0)
            this.size += 5; // Increment size
    }

    /**
     * changeFish method to change the size of the Jellyfish.
     * @return not return anything
     */
    public void changeJellyfish(int size)
    {
        if (size <= 0)
            size = 1;

        this.size = size;
    }

    /**
     * Implement the run method of the thread.
     * Updating the animal's position.
     */
    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                if(Swimmable.food)
                {
                    // Wait for all of the fished to be ready.
                    this.barrier.await();

                    // Run toward the food!
                    do
                    {
                        // Fix direction if needed
                        this.swimToMiddle();

                        // Update position
                        this.swim();
                        Thread.sleep(20);

                        // Check if reached food
                        if(this.reachedFood())
                            Swimmable.callback.callbackFunction(this);

                    } while(Swimmable.food);
                }
                else
                {
                    // Update position
                    this.swim();
                    Thread.sleep(20);

                    // Check collision with borders and fix direction
                    this.collisionWithBorders();

                    if (suspend)
                        synchronized (this) {
                            wait();
                        }

                    if(Thread.interrupted())
                        throw new InterruptedException();
                }
            }
            catch(InterruptedException | BrokenBarrierException e)
            {
                break;
            }
        }
    }
    /**
     * Override the collision detection method to make it accurate for the Jellyfish.
     */
    @Override
    protected void collisionWithBorders()
    {
        // Check collision with borders
        if (x_front - size/2 <= 0)
            x_dir = 1;
        else if (x_front + size/2 >= panel.getWidth())
            x_dir = -1;
        if (y_front - size/4 <= 0)
            y_dir = 1;
        else if (y_front + size/4 >= panel.getHeight())
            y_dir = -1;
    }

    @Override
    public void setColor(Color color) {
        col = color;
    }

    public String ColorToString()
    {
        if(this.col == Color.black)
            return "Black";
        if(this.col == Color.red)
            return "Red";
        if(this.col == Color.blue)
            return "Blue";
        if(this.col == Color.green)
            return "Green";
        if(this.col == Color.cyan)
            return "Cyan";
        if(this.col == Color.orange)
            return "Orange";
        if(this.col == Color.yellow)
            return "Yellow";
        if(this.col == Color.magenta)
            return "Magenta";
        if(this.col == Color.pink)
            return "Pink";
        return null;
    }

    @Override
    public void PaintFish(Color c) {
        setCol(c);
    }

    @Override
    public int getX_dir() {
        return x_dir;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getY_dir() {
        return y_dir;
    }

    @Override
    public int getY_front() {
        return y_front;
    }

    @Override
    public int getX_front() {
        return x_front;
    }

    @Override
    public void setY_dir(int y_dir) {
        this.y_dir = y_dir;
    }

    @Override
    public void setX_front(int x_front) {
        this.x_front = x_front;
    }

    @Override
    public void setX_dir(int x_dir) {
        this.x_dir = x_dir;
    }

    @Override
    public int getFoodFreq() {
        return foodFreq;
    }

    @Override
    public void setY_front(int y_front) {
        this.y_front = y_front;
    }

    @Override
    public void setEatCount(int eatCount) {
        this.eatCount = eatCount;
    }
}
