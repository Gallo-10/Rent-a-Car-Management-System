package GUI;

import BackendCode.Car;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author @AbdullahShahid01
 */
public class Car_Remove extends JFrame {

    private static final String INVALID_ID_MESSAGE = "Invalid ID!";
    private static final String ID_CANNOT_BE_ZERO_OR_NEGATIVE = "ID cannot be '0' or negative!";
    private static final String ENTER_CAR_ID_MESSAGE = "Enter Car ID!";
    private static final String CAR_ID_NOT_FOUND_MESSAGE = "Car ID not found!";
    private static final String CONFIRMATION_MESSAGE = "You are about to remove this car \n %s \n Are you sure you want to continue?";

    private JButton removeButton, cancelButton;
    private JLabel carIDLabel, carIDValidityLabel;
    private JTextField carIDTextField;

    public Car_Remove() {
        super("Remove Car");
        initializeComponents();
        setupLayout();
        addListeners();
    }

    private void initializeComponents() {
        setLayout(new FlowLayout());
        setSize(new Dimension(300, 140));
        setResizable(false);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        removeButton = new JButton("Remove");
        cancelButton = new JButton("Cancel");

        carIDLabel = new JLabel("Enter Car ID to be removed");
        carIDValidityLabel = new JLabel();
        carIDTextField = new JTextField();
        carIDTextField.setPreferredSize(new Dimension(240, 22));
        carIDValidityLabel.setPreferredSize(new Dimension(415, 9));
        carIDValidityLabel.setForeground(Color.red);

        add(carIDLabel);
        add(carIDTextField);
        add(carIDValidityLabel);
        add(removeButton);
        add(cancelButton);
    }

    private void setupLayout() {
        // Layout configuration, if needed
    }

    private void addListeners() {
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRemoveAction();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCloseAction();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                handleCloseAction();
            }
        });
    }

    private void handleRemoveAction() {
        String carID = carIDTextField.getText().trim();
        if (!carID.isEmpty()) {
            try {
                int id = Integer.parseInt(carID);
                if (id > 0) {
                    carIDValidityLabel.setText("");
                    Car car = Car.SearchByID(id);
                    if (car != null) {
                        confirmAndRemoveCar(car);
                    } else {
                        showMessage(CAR_ID_NOT_FOUND_MESSAGE);
                    }
                } else {
                    showMessage(ID_CANNOT_BE_ZERO_OR_NEGATIVE);
                }
            } catch (NumberFormatException ex) {
                showMessage(INVALID_ID_MESSAGE);
            }
        } else {
            showMessage(ENTER_CAR_ID_MESSAGE);
        }
    }

    private void confirmAndRemoveCar(Car car) {
        int response = JOptionPane.showConfirmDialog(
                null, String.format(CONFIRMATION_MESSAGE, car.toString()), "Confirmation",
                JOptionPane.OK_CANCEL_OPTION);

        if (response == JOptionPane.OK_OPTION) {
            car.Remove();
            refreshMainFrame();
            handleCloseAction();
        }
    }

    private void refreshMainFrame() {
        Parent_JFrame.getMainFrame().getContentPane().removeAll();
        Car_Details cd = new Car_Details();
        Parent_JFrame.getMainFrame().add(cd.getMainPanel());
        Parent_JFrame.getMainFrame().getContentPane().revalidate();
    }

    private void handleCloseAction() {
        Parent_JFrame.getMainFrame().setEnabled(true);
        dispose();
    }

    private void showMessage(String message) {
        carIDValidityLabel.setText("                                                            " + message);
    }
}
