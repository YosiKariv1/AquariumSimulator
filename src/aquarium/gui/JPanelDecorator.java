package aquarium.gui;

import aquarium.animals.Swimmable;
import aquarium.decorator.MarineAnimal;
import aquarium.decorator.MarineAnimalDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static aquarium.gui.AquaPanel.animal;

public class JPanelDecorator extends JFrame implements ActionListener {
    private JPanel infoPanel, panel;
    private JButton changeColor;
    private String[][] data;
    private Swimmable[] Fishes;
    private String[] SwimName = {"Animal", "Color", "Size", "Hor. speed", "Ver. speed", "Eat counter"}; //Columns for the table
    private JTable table; //new table
    private JScrollPane sp; // new ScrollPane for the table.
    private int row = -1;
    private Insets insets;
    private Dimension size; //Simension object to determine the size.
    private JColorChooser col;
    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            row = table.rowAtPoint(e.getPoint());
            System.out.println(row);
        }
    };

    public JPanelDecorator()
    {
//----------------------------------------------------Setting the frame------------------------------
        setTitle("Decorator Dialog");
        setSize(450,350);
        setResizable(false);
        setVisible(true);
        setLayout(null);
        panel = new JPanel(null);
        panel.setBounds(0, 0, 450, 350);
        panel.setVisible(true);
        insets = panel.getInsets();
        add(panel);
//-----------------------------------------------Setting Table On infoPanel--------------------------
        table = new JTable(fillTable(), SwimName);
        table.removeEditor();
        table.addMouseListener(mouseAdapter);
        sp = new JScrollPane(table);

//---------------------------------------------Setting new panel for the table------------------------
        infoPanel = new JPanel();
        infoPanel.setBounds(0, 0, 450, 122);
        infoPanel.add(sp);
        infoPanel.setVisible(true);

//------------------------------------------------Change Color Button---------------------------------
        changeColor = new JButton("Change Color");
        changeColor.addActionListener(this);
        size = changeColor.getPreferredSize();
        changeColor.setBounds(150 + insets.left, 275 + insets.top, size.width , size.height);

        fillTable();
        panel.add(infoPanel);
        panel.add(changeColor);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == changeColor)
        {
            if(row != -1)
            {
                Swimmable swim = Fishes[row];
                Color newColor = JColorChooser.showDialog(this, "Choose a color", swim.getColor());
                MarineAnimal DecorateAnimal = new MarineAnimalDecorator((MarineAnimal) Fishes[row]);
                DecorateAnimal.PaintFish(newColor);
                dispose();
            }
            else
                JOptionPane.showMessageDialog(this, "You Need To Choose A Row First");


        }
    }

    public String[][] fillTable() {
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
}
