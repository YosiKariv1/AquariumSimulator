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

public class SaveObjDialog extends JFrame implements ActionListener {

    private JPanel panel, SwimPanel, PlanPanel;
    private JButton swim, plan, save;
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

    public SaveObjDialog(CareTaker c)
    {
        careTaker = c;
        setTitle("Save Object State");
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
        save = new JButton("Save State");
        save.addActionListener(this);
        size = save.getPreferredSize();
        save.setBounds(160 + insets.left, 325 + insets.top, size.width , size.height);

        panel.add(swim);
        panel.add(plan);
        panel.add(save);

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

        if(e.getSource() == save)
        {
            if(row != -1)
            {
                if(PlanPanel.isVisible()) {
                    Memento memento = new Memento(Plants[row]);
                    careTaker.addImmobileMemento(memento);
                }
                else
                {
                    Memento memento= new Memento(Fishes[row]);
                    careTaker.addSwimmableMemento(memento);
                }
            }
            else
                JOptionPane.showMessageDialog(this, "You Need To Choose A Row First");

            dispose();
        }

    }

    public String[][] SwimTable() {

        Fishes = animal.toArray(new Swimmable[animal.size()]);
        int s = Fishes.length;
        data = new String[s][SwimName.length];

        for (int i = 0; i < s; i++) {
            data[i][0] = Fishes[i].getAnimalName();
            data[i][1] = Fishes[i].getColorName();
            data[i][2] = Integer.toString(Fishes[i].getSize());
            data[i][3] = Integer.toString(Fishes[i].getHorSpeed());
            data[i][4] = Integer.toString(Fishes[i].getVerSpeed());
            data[i][5] = Integer.toString(Fishes[i].getEatCount());
        }
        return data;
    }

    public String[][] PlantTable() {

        Plants = planet.toArray(new Immobile[planet.size()]);
        int s1 = planet.size();
        data1 = new String[s1][PlantName.length];

        for (int i = 0; i < s1; i++) {
            data1[i][0] = Plants[i].getPlanetName();
            data1[i][1] = Plants[i].getColorName();
            data1[i][2] = Integer.toString(Plants[i].getSize());
            data1[i][3] = "(" + Integer.toString(Plants[i].getXfront()) + "," + Integer.toString(Plants[i].getYfront()) + ")";
        }
        return data1;
    }
}
