package GUI;

import BackendCode.Car;
import BackendCode.CarOwner;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class CarOwner_Remove {

    private JButton Remove_Button, Cancel_Button;
    private JLabel ID_Label, IDValidity_Label;
    private JTextField ID_TextField;
    JFrame frame = new JFrame();

    public CarOwner_Remove() {
        frame.setTitle("Remove CarOwner");
        frame.setLayout(new AbsoluteLayout());
        frame.setSize(new Dimension(450, 290));
        frame.setResizable(false);
        frame.setLocationRelativeTo(Parent_JFrame.getMainFrame());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Parent_JFrame.getMainFrame().setEnabled(true);
                frame.dispose();
            }
        });

        initComponents();
        addActionListeners();
    }

    private void initComponents() {
        Remove_Button = new JButton("Remove");
        Cancel_Button = new JButton("Cancel");

        ID_Label = new JLabel("Enter ID (without dashes)");
        IDValidity_Label = new JLabel();
        ID_TextField = new JTextField();

        ID_TextField.setPreferredSize(new Dimension(240, 22));

        ID_Label.setPreferredSize(new Dimension(175, 22));
        IDValidity_Label.setPreferredSize(new Dimension(240, 9));
        IDValidity_Label.setForeground(Color.red);
        frame.add(ID_Label, new AbsoluteConstraints(10, 5));
        frame.add(ID_TextField, new AbsoluteConstraints(195, 5));
        frame.add(IDValidity_Label, new AbsoluteConstraints(195, 30));
        frame.add(Remove_Button, new AbsoluteConstraints(100, 225, 100, 22));
        frame.add(Cancel_Button, new AbsoluteConstraints(250, 225, 100, 22));
    }

    private void addActionListeners() {
        Remove_Button.addActionListener(e -> handleRemove());
        Cancel_Button.addActionListener(e -> handleCancel());
    }

    private void handleRemove() {
        String id = ID_TextField.getText().trim();
        if (CarOwner.isIDvalid(id)) {
            CarOwner carOwner = CarOwner.SearchByID(Integer.parseInt(id));
            if (carOwner != null) {
                int confirm = showConfirmDialog("You are about to remove the following Car Owner.\n" + carOwner.toString() +
                        "\nAll the data including Cars and Balance for this car owner will also be deleted  !" +
                        "\nAre you sure you want to continue?");
                if (confirm == JOptionPane.OK_OPTION) {
                    removeCarOwnerAndCars(carOwner);
                    reloadCarOwnerDetails();
                    showMessageDialog("Record successfully Removed !");
                    closeWindow();
                }
            } else {
                showMessageDialog("This ID does not exist!");
            }
        } else {
            showMessageDialog("Enter a valid ID!\n(A valid ID is an integer number greater than 0)");
        }
    }

    private void handleCancel() {
        Parent_JFrame.getMainFrame().setEnabled(true);
        frame.dispose();
    }

    private void removeCarOwnerAndCars(CarOwner carOwner) {
        ArrayList<Car> cars = carOwner.getAllCars();
        for (Car car : cars) {
            car.Remove();
        }
        carOwner.Remove();
    }

    private void reloadCarOwnerDetails() {
        Parent_JFrame.getMainFrame().getContentPane().removeAll();
        CarOwner_Details cd = new CarOwner_Details();
        Parent_JFrame.getMainFrame().add(cd.getMainPanel());
        Parent_JFrame.getMainFrame().getContentPane().revalidate();
    }

    private void closeWindow() {
        Parent_JFrame.getMainFrame().setEnabled(true);
        frame.dispose();
    }

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    private int showConfirmDialog(String message) {
        return JOptionPane.showConfirmDialog(frame, message, "Remove Car Owner", JOptionPane.OK_CANCEL_OPTION);
    }
}
