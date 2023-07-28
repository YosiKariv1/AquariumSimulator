package aquarium.gui;

import aquarium.animals.Swimmable;
import aquarium.memento.Memento;
import aquarium.memento.CareTaker;
import aquarium.plants.Immobile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static aquarium.gui.AquaPanel.animal;
import static aquarium.gui.AquaPanel.planet;

public class RestoreObjState extends JFrame implements ActionListener {

    private JPanel panel, SwimPanel, PlanPanel;
    private JButton swim, plan, restore;
    private CareTaker careTaker;
    private String[] SwimName = {"Animal", "Color", "Size", "Hor. speed", "Ver. speed", "Eat counter"};
    private String[] PlantName = {"Plant", "Color", "Size", "Location"};
    private JTable Swimtable, Planttable;
    private JScrollPane pane1, pane2;
    private Insets insets;
    private Dimension size;
    private String[][] data, data1;
    private Swimmable[] Fishes;
    private Immobile[] Plants;
    private int row = -1;
    private MouseAdapter SwimMouseA = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            row = Swimtable.rowAtPoint(e.getPoint());
            System.out.println(row);
        }
    };

    private MouseAdapter PlantMouseA = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            row = Planttable.rowAtPoint(e.getPoint());
            System.out.println(row);

        }
    };

    public RestoreObjState()
    {
        setTitle("Restore Object State");
        setSize(450,400);
        setResizable(false);
        setVisible(true);
        setLayout(null);

        //first Panel
        panel = new JPanel(null);
        panel.setSize(this.getSize());
        panel.setVisible(true);
        insets = panel.getInsets();


        //swim panel with table
        Swimtable = new JTable(SwimTable(), SwimName);
        Swimtable.addMouseListener(SwimMouseA);

        pane1 = new JScrollPane(Swimtable);

        SwimPanel = new JPanel();
        SwimPanel.setBounds(0, 80, 450, 122);
        SwimPanel.add(pane1);
        SwimPanel.setVisible(false);

        //plant panel with table
        Planttable = new JTable(PlantTable(), PlantName);
        Planttable.addMouseListener(PlantMouseA);

        pane2 = new JScrollPane(Planttable);

        PlanPanel = new JPanel();
        PlanPanel.setBounds(0, 80, 450, 122);
        PlanPanel.add(pane2);
        PlanPanel.setVisible(false);

        panel.add(SwimPanel);
        panel.add(PlanPanel);

        //swim button
        swim = new JButton("Swimmeble");
        swim.addActionListener(this);
        size = swim.getPreferredSize();
        swim.setBounds(80 + insets.left, 40 + insets.top, size.width , size.height);

        //plant button
        plan = new JButton("Plants");
        plan.addActionListener(this);
        size = plan.getPreferredSize();
        plan.setBounds(250 + insets.left, 40 + insets.top, size.width , size.height);

        //save button
        restore = new JButton("Restore State");
        restore.addActionListener(this);
        size = restore.getPreferredSize();
        restore.setBounds(160 + insets.left, 325 + insets.top, size.width , size.height);

        panel.add(swim);
        panel.add(plan);
        panel.add(restore);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == swim)
        {
            if(PlanPanel.isVisible())
            {
                PlanPanel.setVisible(false);
                row = -1;
            }

            SwimPanel.setVisible(true);
        }

        if(e.getSource() == plan)
        {
            if(SwimPanel.isVisible())
            {
                SwimPanel.setVisible(false);
                row = -1;
            }

            PlanPanel.setVisible(true);
        }

        if(e.getSource() == restore)
        {
            if(row != -1)
            {
                if(PlanPanel.isVisible()) {
                    Plants = planet.toArray(new Immobile[planet.size()]);
                    setRestorePlant(Plants[row]);
                }
                else
                {
                    Fishes = animal.toArray(new Swimmable[animal.size()]);
                    setRestoreSwim(Fishes[row]);
                }
            }
            else
                JOptionPane.showMessageDialog(this, "You Need To Choose A Row First");

            dispose();
        }

    }

    public String[][] SwimTable() {

        Memento[] F = CareTaker.swimmableMementoList.toArray(new Memento[CareTaker.swimmableMementoList.size()]);
        int s = F.length;
        data = new String[s][SwimName.length];

        for (int i = 0; i < s; i++) {
            data[i][0] = F[i].getAnimalName();
            data[i][1] = F[i].getColorName();
            data[i][2] = Integer.toString(F[i].getSize());
            data[i][3] = Integer.toString(F[i].getHorSpeed());
            data[i][4] = Integer.toString(F[i].getVerSpeed());
            data[i][5] = Integer.toString(F[i].getEatCount());
        }
        return data;
    }

    public String[][] PlantTable() {

        Memento [] P =  CareTaker.plantesMementoList.toArray(new Memento[CareTaker.plantesMementoList.size()]);
        int s1 = P.length;
        data1 = new String[s1][PlantName.length];

        for (int i = 0; i < s1; i++) {
            data1[i][0] = P[i].getPlanetName();
            data1[i][1] = P[i].getColorName();
            data1[i][2] = Integer.toString(P[i].getSize());
            data1[i][3] = "(" + Integer.toString(P[i].getXfront()) + "," + Integer.toString(P[i].getYfront()) + ")";
        }
        return data1;
    }

    public void setRestoreSwim(Swimmable other)
    {
        Memento[] arr = CareTaker.swimmableMementoList.toArray(new Memento[CareTaker.swimmableMementoList.size()]);
        Memento m = arr[row];
        other.setVerSpeed(m.getVerSpeed());
        other.setColor(m.getColor());
        other.setHorSpeed(m.getHorSpeed());
        other.setX_front(m.getXfront());
        other.setY_front(m.getYfront());
        other.setSize(m.getSize());
        other.setEatCount(m.getEatCount());
    }

    public void setRestorePlant(Immobile other)
    {
        Memento[] arr = CareTaker.plantesMementoList.toArray(new Memento[CareTaker.plantesMementoList.size()]);
        Memento m = arr[row];
        other.setClr(m.getColor());
        other.setX(m.getXfront());
        other.setY(m.getYfront());
        other.setSize(m.getSize());
    }

}
