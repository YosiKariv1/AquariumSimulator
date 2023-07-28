package aquarium.gui;

import aquarium.factory.AbstractSeaFactory;
import aquarium.factory.PlantFactory;
import aquarium.plants.Immobile;
import aquarium.factory.SeaCreature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import static aquarium.gui.AquaPanel.planet;

import static java.lang.Integer.parseInt;

public class AddPlantDialog extends JDialog implements ActionListener {

    private JPanel panel;//new panel.
    private AquaPanel p; //AquaPanel object.
    private JLabel option1, option2, option3, option4, option5, title;
    private JRadioButton Zostera, Laminaria;
    private Insets insets; //Insests object for chanfing the location on the panel.
    private Dimension size; //Simension object to determine the size.
    private ButtonGroup group;
    private JTextField textField;
    private JButton addButton;
    private AbstractSeaFactory seaFactory;
    private SeaCreature plants;

    public AddPlantDialog(AquaPanel p)
    {
        this.p = p;
//----------------------------------------setting up the dialog--------------------------------------------------
        this.setSize(430, 340);
        this.setResizable(false);
        this.setVisible(true);
        this.getContentPane().setLayout(null);
        this.setTitle("Add Plant");
//--------------------------------------setting up the panel---------------------------------------------------
        panel = new JPanel(null);
        panel.setBounds(0, 0, 430, 340);
        this.getContentPane().add(panel, BorderLayout.CENTER);
//--------------------------------1.Radio Button for the first option--------------------------------------------
        //create label and RadioButtons for the first option.
        option1 = new JLabel("1. Choose Plant: ");
        Zostera = new JRadioButton("Zostera");
        Laminaria = new JRadioButton("Laminaria");
        Zostera.setSelected(true);

        //adding Fish and JellyFish to the ActionListener
        Zostera.addActionListener(this);
        Laminaria.addActionListener(this);

        //new ButtonGroup for the RadioButtons.
        group = new ButtonGroup();

        //setting up the location on the screen for the label and the radio button
        insets = panel.getInsets();
        size = option1.getPreferredSize();
        option1.setBounds(20 + insets.left, 131 + insets.top, size.width, size.height);

        //determine the location of the Fish RadioButton.
        size = Zostera.getPreferredSize();
        Zostera.setBounds(135 + insets.left, 130 + insets.top, size.width, size.height);

        //determine the location for JellyFish RadioButton.
        size = Laminaria.getPreferredSize();
        Laminaria.setBounds(245 + insets.left, 130 + insets.top, size.width, size.height);

        //adding the RadioButton to the groupButton and the label and RadioButton to the panel.
        group.add(Zostera);
        group.add(Laminaria);
        panel.add(option1);
        panel.add(Zostera);
        panel.add(Laminaria);

        //--------------------------------2.Text Field for the second option--------------------------------------------
        //create label and TextField for the second option.
        option2 = new JLabel("2. Choose The Size (Only Between 20 - 320): ");
        textField = new JTextField(10);
        textField.addActionListener(this);

        //determine the location for the option2 label.
        size = option2.getPreferredSize();
        option2.setBounds(20 + insets.left, 190 + insets.top, size.width, size.height);

        //determine the location for the textField.
        size = textField.getPreferredSize();
        textField.setBounds(280 + insets.left, 190 + insets.top, size.width, size.height);

        //adding the label and the textField to the panel.
        panel.add(option2);
        panel.add(textField);
//-----------------------------------------------Create Button----------------------------------------
        //create new button for the crete button
        addButton = new JButton("Create");
        addButton.addActionListener(this);

        //determine the location for the addButton.
        size = addButton.getPreferredSize();
        addButton.setBounds(110 + insets.left, 250 + insets.top, 150, 30);

        //adding the addButton to the panel.
        panel.add(addButton);
//---------------------------------------setting the title-----------------------------------------------
        //create label for the AddAnimal button.
        title = new JLabel("Add Plant");
        title.setFont(new Font(title.getName(), Font.BOLD, 30));

        //determine the location for the label.
        size = title.getPreferredSize();
        title.setBounds(130 + insets.left, 50 + insets.top, size.width, size.height);

        //adding the label to the panel.
        panel.add(title);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == addButton)
        {
            int size = parseInt(textField.getText());;
            int x,y;
            Random rand = new Random();
            x = rand.nextInt(1120 - size);
            y = rand.nextInt(650 - size);

            if(Laminaria.isSelected())
            {
                seaFactory = new PlantFactory(p,size,x,y);
                plants = seaFactory.produceSeaCreature("Laminaria");
                planet.add((Immobile) plants);
            }

            else if(Zostera.isSelected())
            {
                seaFactory = new PlantFactory(p,size,x,y);
                plants = seaFactory.produceSeaCreature("Zostera");
                planet.add((Immobile) plants);
            }
            dispose();
        }
    }
}
