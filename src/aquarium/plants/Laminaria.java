package aquarium.plants;

import aquarium.gui.AquaPanel;

import java.awt.*;

public class Laminaria extends Immobile {
    private AquaPanel panel;

    public Laminaria(AquaPanel panel, int size, int x, int y)
    {
        super(size, x, y,"Laminaria");
        this.panel = panel;
    }

    @Override
    public void drawCreature(Graphics g)
    {
        g.setColor(getColor());
        g.fillArc(getXfront()-getSize()/20, getYfront()-getSize(), getSize()/10, getSize()*4/5,0, 360);
        g.fillArc(getXfront()-getSize()*3/20, getYfront()-getSize()*13/15, getSize()/10, getSize()*2/3,0, 360);
        g.fillArc(getXfront()+getSize()/20,getYfront()-getSize()*13/15,getSize()/10, getSize()*2/3, 0, 360);
        g.drawLine(getXfront(), getYfront(), getXfront(), getYfront()-getSize()/5);
        g.drawLine(getXfront(),getYfront(),getXfront()-getSize()/10,getYfront()-getSize()/5);
        g.drawLine(getXfront(), getYfront(), getXfront()+getSize()/10, getYfront()-getSize()/5);
    }
}
