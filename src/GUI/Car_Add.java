package GUI;

import BackendCode.Car;
import BackendCode.CarOwner;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.*;

public class Car_Add extends JFrame {

    JButton addButton, cancelButton;
    JLabel makerLabel, nameLabel, colorLabel, typeLabel, seatingCapacityLabel, modelLabel, conditionLabel, regNoLabel, rentPerHourLabel, ownerIDLabel;
    JLabel makerValidityLabel, nameValidityLabel, regNoValidityLabel, rentPerHourValidityLabel, ownerIDValidityLabel;
    JTextField makerTextField, nameTextField, regNoTextField, rentPerHourTextField, ownerIDTextField;
    JComboBox<String> colorComboBox, typeComboBox, modelComboBox, conditionComboBox;
    JSpinner seatingCapacitySpinner;

    public Car_Add() {
        super("Add Car");
        setLayout(new FlowLayout());
        setSize(new Dimension(450, 475));
        setResizable(false);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Parent_JFrame.getMainFrame().setEnabled(true);
                dispose();
            }
        });

        initializeComponents();
        addComponentsToFrame();
        registerActionListeners();
    }

    private void initializeComponents() {
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");

        makerLabel = new JLabel("Maker");
        nameLabel = new JLabel("Name");
        colorLabel = new JLabel("Color");
        modelLabel = new JLabel("Model");
        typeLabel = new JLabel("Car type");
        seatingCapacityLabel = new JLabel("Seating capacity");
        regNoLabel = new JLabel("Reg no (ABC-0123)");
        ownerIDLabel = new JLabel("Owner ID");
        rentPerHourLabel = new JLabel("Rent Per Hour (in PKR)");
        conditionLabel = new JLabel("Condition ");

        makerValidityLabel = new JLabel();
        nameValidityLabel = new JLabel();
        regNoValidityLabel = new JLabel();
        ownerIDValidityLabel = new JLabel();
        rentPerHourValidityLabel = new JLabel();

        makerTextField = new JTextField();
        nameTextField = new JTextField();
        regNoTextField = new JTextField();
        ownerIDTextField = new JTextField();
        rentPerHourTextField = new JTextField();

        String[] colours = {"White", "Red", "Silver", "Blue", "Black"};
        colorComboBox = new JComboBox<>(colours);
        String[] types = {"Familycar", "Comercial", "Microcar", "Compact car", "Mid-size car", "Supercar", "Convertible", "Sports cars"};
        typeComboBox = new JComboBox<>(types);

        int todaysYear = new Date().getYear() + 1900;
        int noOfYears = todaysYear - 1949;
        String[] years = new String[noOfYears];
        for (int i = 0; i < noOfYears; i++) {
            years[i] = todaysYear - i + "";
        }
        modelComboBox = new JComboBox<>(years);

        String[] conditions = {"Excellent", "Good", "Average", "Bad"};
        conditionComboBox = new JComboBox<>(conditions);

        seatingCapacitySpinner = new JSpinner();
        seatingCapacitySpinner.setModel(new SpinnerNumberModel(4, 1, null, 1));
        seatingCapacitySpinner.setFocusable(false);

        setComponentSizes();
        setValidityLabelColors();
    }

    private void setComponentSizes() {
        makerTextField.setPreferredSize(new Dimension(240, 22));
        nameTextField.setPreferredSize(new Dimension(240, 22));
        regNoTextField.setPreferredSize(new Dimension(240, 22));
        ownerIDTextField.setPreferredSize(new Dimension(240, 22));
        rentPerHourTextField.setPreferredSize(new Dimension(240, 22));

        makerLabel.setPreferredSize(new Dimension(175, 22));
        nameLabel.setPreferredSize(new Dimension(175, 22));
        regNoLabel.setPreferredSize(new Dimension(175, 22));
        ownerIDLabel.setPreferredSize(new Dimension(175, 22));
        rentPerHourLabel.setPreferredSize(new Dimension(175, 22));

        makerValidityLabel.setPreferredSize(new Dimension(415, 9));
        nameValidityLabel.setPreferredSize(new Dimension(415, 9));
        regNoValidityLabel.setPreferredSize(new Dimension(415, 9));
        ownerIDValidityLabel.setPreferredSize(new Dimension(415, 9));
        rentPerHourValidityLabel.setPreferredSize(new Dimension(415, 9));

        seatingCapacitySpinner.setPreferredSize(new Dimension(50, 22));
        addButton.setPreferredSize(new Dimension(100, 22));
        cancelButton.setPreferredSize(new Dimension(100, 22));
    }

    private void setValidityLabelColors() {
        makerValidityLabel.setForeground(Color.red);
        nameValidityLabel.setForeground(Color.red);
        regNoValidityLabel.setForeground(Color.red);
        ownerIDValidityLabel.setForeground(Color.red);
        rentPerHourValidityLabel.setForeground(Color.red);
    }

    private void addComponentsToFrame() {
        add(makerLabel);
        add(makerTextField);
        add(makerValidityLabel);

        add(nameLabel);
        add(nameTextField);
        add(nameValidityLabel);

        add(regNoLabel);
        add(regNoTextField);
        add(regNoValidityLabel);

        add(ownerIDLabel);
        add(ownerIDTextField);
        add(ownerIDValidityLabel);

        add(rentPerHourLabel);
        add(rentPerHourTextField);
        add(rentPerHourValidityLabel);

        add(modelLabel);
        add(modelComboBox);
        add(typeLabel);
        add(typeComboBox);
        add(seatingCapacityLabel);
        add(seatingCapacitySpinner);
        add(colorLabel);
        add(colorComboBox);
        add(conditionLabel);
        add(conditionComboBox);

        add(addButton);
        add(cancelButton);
    }

    private void registerActionListeners() {
        addButton.addActionListener(e -> handleAddAction());
        cancelButton.addActionListener(e -> handleCancelAction());
    }

    private void handleAddAction() {
        String maker = makerTextField.getText().trim();
        String name = nameTextField.getText().trim();
        String regNo = regNoTextField.getText().trim();
        String ownerID = ownerIDTextField.getText().trim();
        String rentPerHour = rentPerHourTextField.getText().trim();

        boolean isValid = validateInputs(maker, name, regNo, ownerID, rentPerHour);

        if (isValid) {
            try {
                CarOwner carOwner = CarOwner.SearchByID(Integer.parseInt(ownerID));
                Car car = Car.SearchByRegNo(regNo);

                if (carOwner != null && car == null) {
                    car = new Car(0, maker, name, colorComboBox.getSelectedItem().toString(),
                            typeComboBox.getSelectedItem().toString(),
                            Integer.parseInt(seatingCapacitySpinner.getValue().toString()),
                            modelComboBox.getSelectedItem().toString(),
                            conditionComboBox.getSelectedItem().toString(),
                            regNo, Integer.parseInt(rentPerHour), carOwner);
                    car.Add();
                    updateMainFrame();
                    JOptionPane.showMessageDialog(null, "Record Successfully Added!");
                    dispose();
                } else if (carOwner == null) {
                    JOptionPane.showMessageDialog(null, "Owner ID does not exist!");
                } else {
                    JOptionPane.showMessageDialog(null, "This Car Registration no is already registered!");
                }
            } catch (HeadlessException | NumberFormatException ex) {
                System.out.println(ex);
            }
        }
    }

    private boolean validateInputs(String maker, String name, String regNo, String ownerID, String rentPerHour) {
        boolean isValid = true;

        if (maker.isEmpty() || !Car.isNameValid(maker)) {
            makerValidityLabel.setText("Invalid Maker's Name!");
            isValid = false;
        } else {
            makerValidityLabel.setText("");
        }

        if (name.isEmpty() || !Car.isNameValid(name)) {
            nameValidityLabel.setText("Invalid Car Name!");
            isValid = false;
        } else {
            nameValidityLabel.setText("");
        }

        if (regNo.isEmpty() || !Car.isRegNoValid(regNo)) {
            regNoValidityLabel.setText("Invalid Reg No!");
            isValid = false;
        } else {
            regNoValidityLabel.setText("");
        }

        if (ownerID.isEmpty() || !isValidID(ownerID)) {
            ownerIDValidityLabel.setText("Invalid Owner ID!");
            isValid = false;
        } else {
            ownerIDValidityLabel.setText("");
        }

        if (rentPerHour.isEmpty() || !isValidRent(rentPerHour)) {
            rentPerHourValidityLabel.setText("Invalid Rent!");
            isValid = false;
        } else {
            rentPerHourValidityLabel.setText("");
        }

        return isValid;
    }

    private boolean isValidID(String ownerID) {
        try {
            int id = Integer.parseInt(ownerID);
            return id > 0;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean isValidRent(String rentPerHour) {
        try {
            int rent = Integer.parseInt(rentPerHour);
            return rent > 0;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private void updateMainFrame() {
        Parent_JFrame.getMainFrame().getContentPane().removeAll();
        Car_Details cd = new Car_Details();
        Parent_JFrame.getMainFrame().add(cd.getMainPanel());
        Parent_JFrame.getMainFrame().getContentPane().revalidate();
        Parent_JFrame.getMainFrame().setEnabled(true);
    }

    private void handleCancelAction() {
        Parent_JFrame.getMainFrame().setEnabled(true);
        dispose();
    }
}
