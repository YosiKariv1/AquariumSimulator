package aquarium.animals;
import aquarium.gui.AquaPanel;
import aquarium.decorator.MarineAnimal;

import java.awt.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * This class represent the Fish.it contains multiple methods for increasing the size of the Fish and draw the Fish.
 * In this class we also make the Fish move on the screen.
 * @version 3.3 2021
 * @author Yosi kariv 205507460
 *         Amit Cohen 316432137
 */

public class Fish extends Swimmable implements MarineAnimal
{
    //class variables
    private final int EAT_DISTANCE = 4;
    private  int size, eatCount;
    private boolean suspend = false; //boolean variable for the suspention of the animals.
    private Color col; //Color field for the color of the fish
    private String color;// fish color

    /**
     * Constructor for initialize the variables of the Fish.
     * @return not return anything.
     */
    public Fish(AquaPanel panel, int size, int horSpeed, int verSpeed, Color col, String colorName, int foodFreq) {
        super(horSpeed, verSpeed, panel, foodFreq);
        this.size = size;
        this.x_front = panel.getWidth()/2;
        this.y_front = panel.getHeight()/2;
        this.col = col;
        this.x_dir = 1;
        this.y_dir = 1;
        this.eatCount = 0;
        this.color = colorName;
    }

    /**
     * Setter to set the col to be the newColor.
     * @params newColor
     * @return not return anything.
     */
    public void setCol(Color newColor) {
        col = newColor;
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
    public void setEatCount(int eatCount) {
        this.eatCount = eatCount;
    }

    @Override
    public void setY_front(int y_front) {
        this.y_front = y_front;
    }

    /**
     * Getter to return the name of the animal.
     * @return the animal name.
     */
    @Override
    public String getAnimalName() {
        return "Fish";
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
     * Getter to return the eatCount of the fish.
     * @return eatCount
     */
    @Override
    public int getEatCount() {
        return this.eatCount;
    }

    /**
     * Getter to return the size of the fish.
     * @return size
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Getter to return the color of the fish.
     * @return col
     */
    @Override
    public String getColorName() {
        return this.color;
    }

    @Override
    public Color getColor() {
        return col;
    }

    @Override
    public void setColor(Color color) {
        col = color;
    }

    @Override
    public Swimmable clone()
    {
        return new Fish(panel, size, horSpeed, verSpeed, col, color, foodFreq);
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
     * Increase the amount of food the fish ate by 1.
     * Increase fish's size if he ate enough food.
     */
    @Override
    public void eatInc() {
        this.eatCount++;

        if(this.eatCount % this.EAT_DISTANCE == 0)
            this.size += 5; // Increment size
    }

    /**
     * changeFish method to change the size of the fish.
     * @return not return anything
     */
    public void changeFish(int size) {
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
    @Override
    protected void swimToMiddle()
    {
        int centerX;

        if (x_dir == 1)
            centerX = x_front - getSize()/2;
        else
            centerX = x_front + getSize()/2;

        if (x_front > panel.getWidth()/2)
        {
            x_dir = -1;
            x_front = centerX - getSize()/2;
        }
        else
        {
            x_dir = 1;
            x_front = centerX + getSize()/2;
        }

        if (y_front >  panel.getHeight()/2)
            y_dir = -1;
        else
            y_dir = 1;
    }

    /**
     * drawCreature method to draw the fish.
     * @params g
     * @return not return anything.
     */
    @Override
    public void drawCreature(Graphics g)
    {
        g.setColor(col);
        if (x_dir == 1) // fish swims to right side
        {
            // Body of fish
            g.fillOval(x_front - size, y_front - size / 4, size, size / 2);

            // Tail of fish
            int[] x_t = {x_front - size - size / 4, x_front - size - size / 4, x_front - size};
            int[] y_t = {y_front - size / 4, y_front + size / 4, y_front};
            Polygon t = new Polygon(x_t, y_t, 3);
            g.fillPolygon(t);

            // Eye of fish
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue()));
            g2.fillOval(x_front - size / 5, y_front - size / 10, size / 10, size / 10);

            // Mouth of fish
            if (size > 70)
                g2.setStroke(new BasicStroke(3));
            else if (size > 30)
                g2.setStroke(new BasicStroke(2));
            else
                g2.setStroke(new BasicStroke(1));
            g2.drawLine(x_front, y_front, x_front - size / 10, y_front + size / 10);
            g2.setStroke(new BasicStroke(1));
        } else // fish swims to left side
        {
            // Body of fish
            g.fillOval(x_front, y_front - size / 4, size, size / 2);

            // Tail of fish
            int[] x_t = {x_front + size + size / 4, x_front + size + size / 4, x_front + size};
            int[] y_t = {y_front - size / 4, y_front + size / 4, y_front};
            Polygon t = new Polygon(x_t, y_t, 3);
            g.fillPolygon(t);
            // Eye of fish
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue()));
            g2.fillOval(x_front + size / 10, y_front - size / 10, size / 10, size / 10);

            // Mouth of fish
            if (size > 70)
                g2.setStroke(new BasicStroke(3));
            else if (size > 30)
                g2.setStroke(new BasicStroke(2));
            else g2.setStroke(new BasicStroke(1));
            g2.drawLine(x_front, y_front, x_front + size / 10, y_front + size / 10);
            g2.setStroke(new BasicStroke(1));
        }
    }

    public String ColorToString()
    {
        if(col == Color.BLACK)
            return "Black";
        else if(col == Color.RED)
            return "Red";
        else if(col == Color.BLUE)
            return "Blue";
        else if(col == Color.GREEN)
            return "Green";
        else if(col == Color.CYAN)
            return "Cyan";
        else if(col == Color.ORANGE)
            return "Orange";
        else if(col == Color.YELLOW)
            return "Yellow";
        else if(col == Color.MAGENTA)
            return "Magenta";
        else if(col == Color.PINK)
            return "Pink";
        return null;
    }

    @Override
    public void PaintFish(Color c) {
        setCol(c);
    }
}




