package aquarium.gui;

import aquarium.animals.Swimmable;
import aquarium.gui.AquaPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.lang.Integer.parseInt;
import static aquarium.gui.AquaPanel.animal;

public class DuplicateAnimal extends JFrame implements ActionListener
{
    private AquaPanel p;
    private JPanel infoPanel, panel;
    private String[][] data;
    private Swimmable[] Fishes;
    private String[] SwimName = {"Animal", "Color", "Size", "Hor. speed", "Ver. speed", "Eat counter"}; //Columns for the table
    private JTable table; //new table
    private JScrollPane sp; // new ScrollPane for the table.
    private JButton duplicate;
    private Insets insets; //Insests object for chanfing the location on the panel.
    private Dimension size; //Simension object to determine the size.
    private int row = -1;
    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            row = table.rowAtPoint(e.getPoint());
            System.out.println(row);
            textField.setText(Integer.toString(Fishes[row].getSize()));
            horspeed_js.setValue(Fishes[row].getHorSpeed());
            verspeed_js.setValue(Fishes[row].getVerSpeed());
            colorList.setSelectedIndex(setColor(Fishes[row].getColorName()));
        }
    };
    private JLabel size_lb, horspeed_lb, verspeed_lb, color_lb ; //labels for the add animal window.
    private JTextField textField, textField1; //textField for the size entering.
    private JSlider horspeed_js, verspeed_js; //sliders for horSpeed and verSpeed.
    private JComboBox colorList; //ComboBox for the color list.
    private String[] colors = {"Black", "Red", "Blue", "Green", "Cyan", "Orange", "Yellow", "Magenta", "Pink"}; //list of Colors.

    public DuplicateAnimal(AquaPanel p)
    {
        this.p = p;
        System.out.println(row);
//----------------------------------------------------Setting the frame------------------------------
        setTitle("Duplicate Dialog");
        setSize(450,450);
        setResizable(false);
        setVisible(true);
        setLayout(null);
        panel = new JPanel(null);
        panel.setBounds(0, 0, 450, 450);
        panel.setVisible(true);
        insets = panel.getInsets();
        add(panel);

//-----------------------------------------------Setting Table On infoPanel--------------------------
        table = new JTable(fillTable(), SwimName);
        table.validate();
        table.removeEditor();
        table.addMouseListener(mouseAdapter);
        sp = new JScrollPane(table);

//---------------------------------------------Setting new panel for the table------------------------
        infoPanel = new JPanel();
        infoPanel.setBounds(0, 0, 450, 122);
        infoPanel.add(sp);
        infoPanel.setVisible(true);

//--------------------------------------------------duplicate Button---------------------------------
        duplicate = new JButton("Duplicate");
        duplicate.addActionListener(this);
        size = duplicate.getPreferredSize();
        duplicate.setBounds(150 + insets.left, 375 + insets.top, size.width , size.height);

        panel.add(infoPanel);
        panel.add(duplicate);
        SetField();
        fillTable();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == duplicate)
        {
            if(row != -1)
            {
                Swimmable swim = Fishes[row].clone();
                swim.Upgrade(parseInt(textField.getText()), horspeed_js.getValue(), verspeed_js.getValue(), StringToColor((String)colorList.getItemAt(colorList.getSelectedIndex())), (String)colorList.getItemAt(colorList.getSelectedIndex()));
                animal.add(swim);
                swim.start();
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

        for (int i = 0; i < animal.size(); i++) {
            data[i][0] = Fishes[i].getAnimalName();
            data[i][1] = Fishes[i].getColorName();
            data[i][2] = Integer.toString(Fishes[i].getSize());
            data[i][3] = Integer.toString(Fishes[i].getHorSpeed());
            data[i][4] = Integer.toString(Fishes[i].getVerSpeed());
            data[i][5] = Integer.toString(Fishes[i].getEatCount());
        }
        return data;
    }

    public void SetField()
    {

//--------------------------------2.Text Field for the second option--------------------------------------------
        //create label and TextField for the second option.
        size_lb = new JLabel("Size (Only Between 20 - 320):");
        textField = new JTextField(10);
        textField.addActionListener(this);

        //determine the location for the option2 label.
        size = size_lb.getPreferredSize();
        size_lb.setBounds(50 + insets.left, 140 + insets.top, size.width, size.height);

        //determine the location for the textField.
        size = textField.getPreferredSize();
        textField.setBounds(250 + insets.left, 140 + insets.top, size.width, size.height);

        //adding the label and the textField to the panel.
        panel.add(size_lb);
        panel.add(textField);

//------------------------------------3.Slider for the third option--------------------------------------------
        //create label and slider for the third option.
        horspeed_lb = new JLabel("Horizontal Speed:");
        horspeed_js = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);

        //slider configuration
        horspeed_js.setMajorTickSpacing(1);
        horspeed_js.setMinorTickSpacing(1);
        horspeed_js.setPaintTicks(true);
        horspeed_js.setPaintLabels(true);

        //determine the location for the option3 label.
        size = horspeed_lb.getPreferredSize();
        horspeed_lb.setBounds(50 + insets.left, 190 + insets.top, size.width, size.height);

        //determine the location for the option3 slider.
        size = horspeed_js.getPreferredSize();
        horspeed_js.setBounds(200 + insets.left, 190 + insets.top, size.width, size.height);

        //adding the label and the slider to the panel.
        panel.add(horspeed_lb);
        panel.add(horspeed_js);

//------------------------------------4.Slider for the fourth option--------------------------------------------
        //create label and slider for the fourth option.
        verspeed_lb = new JLabel("Vertical Speed:");
        verspeed_js = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);

        //slider configuration
        verspeed_js.setMajorTickSpacing(1);
        verspeed_js.setMinorTickSpacing(1);
        verspeed_js.setPaintTicks(true);
        verspeed_js.setPaintLabels(true);

        //determine the location for the option4 label.
        size = verspeed_lb.getPreferredSize();
        verspeed_lb.setBounds(50 + insets.left, 250 + insets.top, size.width, size.height);

        //determine the location for the slider2.
        size = verspeed_js.getPreferredSize();
        verspeed_js.setBounds(200 + insets.left, 250 + insets.top, size.width, size.height);

        //adding the label and the slider to the panel.
        panel.add(verspeed_lb);
        panel.add(verspeed_js);

//------------------------------------5.Combo Box for the fifth option--------------------------------------------
        //create label and ComboBox for the fifth option.
        color_lb = new JLabel("Color:");
        colorList = new JComboBox(colors);
        colorList.setSelectedIndex(4);
        colorList.addActionListener(this);

        //determine the location for the option5 label.
        size = color_lb.getPreferredSize();
        color_lb.setBounds(50 + insets.left, 320 + insets.top, size.width, size.height);

        //determine the location for the ComboBox.
        size = colorList.getPreferredSize();
        colorList.setBounds(200 + insets.left, 320 + insets.top, size.width, size.height);

        //adding the label and the ComboBox to the panel
        panel.add(color_lb);
        panel.add(colorList);
    }

    public int setColor(String name)
    {
        for(int i = 0; i < colors.length; i++)
        {
            if(colors[i] == name)
                return i;
        }
        return 0;
    }

    /**
     * StringToColor function that convert a String received to a Color object.
     * @param col
     * @return color Object
     */
    public Color StringToColor(String col)
    {
        Color color;
        switch (col) {
            case "Red":
                color = new Color(255, 0, 0);
                break;
            case "Blue":
                color = new Color(0, 0, 255);
                break;
            case "Green":
                color = new Color(0, 255, 0);
                break;
            case "Orange":
                color = new Color(255, 165, 0);
                break;
            case "Cyan":
                color = new Color(0, 255, 255);
                break;
            case "Pink":
                color = new Color(255, 105, 180);
                break;
            case "Black":
                color = new Color(0, 0, 0);
                break;
            case "Magenta":
                color = new Color(255, 0, 255);
                break;
            case "Yellow":
                color = new Color(255, 255, 0);
                break;
            default:
                color = new Color(255, 240, 240);
                break;
        }
        return color;
    }
}
