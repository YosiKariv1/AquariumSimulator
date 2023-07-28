package aquarium.plants;

import aquarium.gui.AquaPanel;

import java.awt.*;

public class Zostera extends Immobile {
    private AquaPanel panel;

    public Zostera(AquaPanel panel, int size, int x, int y)
    {
        super(size, x, y,"Zostera");
        this.panel = panel;
    }

    @Override
    public void drawCreature(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(getColor());
        g.drawLine(getXfront(), getYfront(), getXfront(), getYfront() - getSize());
        g.drawLine(getXfront() - 2, getYfront(), getXfront() - 10, getYfront() - getSize()*9/10);
        g.drawLine(getXfront() + 2, getYfront(), getXfront() + 10, getYfront() - getSize()*9/10);
        g.drawLine(getXfront() - 4, getYfront(), getXfront() - 20, getYfront() - getSize()*4/5);
        g.drawLine(getXfront() + 4, getYfront(), getXfront() + 20, getYfront() - getSize()*4/5);
        g.drawLine(getXfront() - 6, getYfront(), getXfront() - 30, getYfront() - getSize()*7/10);
        g.drawLine(getXfront() + 6, getYfront(), getXfront() + 30, getYfront() - getSize()*7/10);
        g.drawLine(getXfront() - 8, getYfront(), getXfront() - 40, getYfront() - getSize()*4/7);
        g.drawLine(getXfront() + 8, getYfront(), getXfront() + 40, getYfront() - getSize()*4/7);
        g2.setStroke(new BasicStroke(1));
    }
}
