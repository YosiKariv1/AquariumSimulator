package aquarium.singleton;
import aquarium.gui.AquaPanel;
import aquarium.animals.Swimmable;

import java.awt.*;

public class Singleton {
    private static Singleton instance = null;
    private boolean isAlive;
    private AquaPanel panel;

    /* A private Constructor prevents any other *
    class from instantiating.*/
    private Singleton(AquaPanel p) {
        isAlive = false;
        panel = p;
    }
    /* Static 'instance' method */

    public static Singleton getInstance(AquaPanel p) {
        if (instance == null)
            instance = new Singleton(p);
        return instance;
    }
    public static void set()
    {
        if(instance != null)
            instance = null;
    }

    public void setAlive(boolean b) {
        this.isAlive = b;
        Swimmable.setFood(b);
    }
    public boolean getAlive() { return this.isAlive; }

    /**
     * Draw the worm.
     *
     * @param g				Graphic object.
     */
    public void draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.orange);
        g2.drawArc(panel.getWidth() / 2, panel.getHeight() / 2 - 5, 10, 10, 30, 210);
        g2.drawArc(panel.getWidth() / 2, panel.getHeight() / 2 + 5, 10, 10, 180, 270);
    }

}
