package GUI;

import BackendCode.CarOwner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CarOwner_Add {

    private JButton addButton, cancelButton;
    private JLabel cnicLabel, nameLabel, contactLabel, emailLabel, userNameLabel, passwordLabel;
    private JLabel cnicValidityLabel, nameValidityLabel, contactValidityLabel, emailValidityLabel, userNameValidityLabel, passwordValidityLabel;
    private JTextField cnicTextField, nameTextField, contactTextField, emailTextField, userNameTextField, passwordTextField;
    public JFrame frame;

    private static final String WINDOW_TITLE = "Add CarOwner";
    private static final String CNIC_PROMPT = "Enter CNIC (without dashes)";
    private static final String NAME_PROMPT = "Enter Name";
    private static final String CONTACT_PROMPT = "Enter Contact";
    private static final String EMAIL_PROMPT = "Enter Email";
    private static final String USERNAME_PROMPT = "Enter Username";
    private static final String PASSWORD_PROMPT = "Enter Password";
    private static final String CNIC_INVALID_MESSAGE = "Invalid CNIC";
    private static final String CNIC_ALREADY_REGISTERED_MESSAGE = "This CNIC is already registered!";
    private static final String NAME_INVALID_MESSAGE = "Invalid Name!";
    private static final String CONTACT_INVALID_MESSAGE = "Invalid contact no.!";
    private static final String CAR_OWNER_ADDED_MESSAGE = "Car Owner added successfully!";
    private static final Dimension WINDOW_SIZE = new Dimension(450, 290);

    public CarOwner_Add() {
        frame = new JFrame(WINDOW_TITLE);
        setupWindow();
        initializeComponents();
        layoutComponents();
        addEventListeners();
    }

    private void setupWindow() {
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Parent_JFrame.getMainFrame().setEnabled(true);
                frame.dispose();
            }
        });
        frame.setSize(WINDOW_SIZE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(Parent_JFrame.getMainFrame());
    }

    private void initializeComponents() {
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");

        cnicLabel = new JLabel(CNIC_PROMPT);
        nameLabel = new JLabel(NAME_PROMPT);
        contactLabel = new JLabel(CONTACT_PROMPT);
        emailLabel = new JLabel(EMAIL_PROMPT);
        userNameLabel = new JLabel(USERNAME_PROMPT);
        passwordLabel = new JLabel(PASSWORD_PROMPT);

        cnicTextField = new JTextField();
        nameTextField = new JTextField();
        contactTextField = new JTextField();
        emailTextField = new JTextField();
        userNameTextField = new JTextField();
        passwordTextField = new JTextField();

        cnicValidityLabel = new JLabel();
        nameValidityLabel = new JLabel();
        contactValidityLabel = new JLabel();
        emailValidityLabel = new JLabel();
        userNameValidityLabel = new JLabel();
        passwordValidityLabel = new JLabel();

        setPreferredSizeForTextFields();
        setPreferredSizeForLabels();
        setLabelColors();
    }

    private void setPreferredSizeForTextFields() {
        Dimension textFieldDimension = new Dimension(240, 22);
        cnicTextField.setPreferredSize(textFieldDimension);
        nameTextField.setPreferredSize(textFieldDimension);
        contactTextField.setPreferredSize(textFieldDimension);
        emailTextField.setPreferredSize(textFieldDimension);
        userNameTextField.setPreferredSize(textFieldDimension);
        passwordTextField.setPreferredSize(textFieldDimension);
    }

    private void setPreferredSizeForLabels() {
        Dimension labelDimension = new Dimension(175, 22);
        cnicLabel.setPreferredSize(labelDimension);
        nameLabel.setPreferredSize(labelDimension);
        contactLabel.setPreferredSize(labelDimension);
        emailLabel.setPreferredSize(labelDimension);
        userNameLabel.setPreferredSize(labelDimension);
        passwordLabel.setPreferredSize(labelDimension);

        Dimension validityLabelDimension = new Dimension(240, 9);
        cnicValidityLabel.setPreferredSize(validityLabelDimension);
        nameValidityLabel.setPreferredSize(validityLabelDimension);
        contactValidityLabel.setPreferredSize(validityLabelDimension);
        emailValidityLabel.setPreferredSize(validityLabelDimension);
        userNameValidityLabel.setPreferredSize(validityLabelDimension);
        passwordValidityLabel.setPreferredSize(validityLabelDimension);
    }

    private void setLabelColors() {
        cnicValidityLabel.setForeground(Color.red);
        nameValidityLabel.setForeground(Color.red);
        contactValidityLabel.setForeground(Color.red);
        emailValidityLabel.setForeground(Color.red);
        userNameValidityLabel.setForeground(Color.red);
        passwordValidityLabel.setForeground(Color.red);
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        addToFrame(cnicLabel, 0, 0, gbc);
        addToFrame(cnicTextField, 1, 0, gbc);
        addToFrame(cnicValidityLabel, 1, 1, gbc);

        addToFrame(nameLabel, 0, 2, gbc);
        addToFrame(nameTextField, 1, 2, gbc);
        addToFrame(nameValidityLabel, 1, 3, gbc);

        addToFrame(contactLabel, 0, 4, gbc);
        addToFrame(contactTextField, 1, 4, gbc);
        addToFrame(contactValidityLabel, 1, 5, gbc);

        addToFrame(emailLabel, 0, 6, gbc);
        addToFrame(emailTextField, 1, 6, gbc);
        addToFrame(emailValidityLabel, 1, 7, gbc);

        addToFrame(userNameLabel, 0, 8, gbc);
        addToFrame(userNameTextField, 1, 8, gbc);
        addToFrame(userNameValidityLabel, 1, 9, gbc);

        addToFrame(passwordLabel, 0, 10, gbc);
        addToFrame(passwordTextField, 1, 10, gbc);
        addToFrame(passwordValidityLabel, 1, 11, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        addToFrame(addButton, 0, 12, gbc);
        addToFrame(cancelButton, 1, 12, gbc);
    }

    private void addToFrame(Component component, int gridx, int gridy, GridBagConstraints gbc) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        frame.add(component, gbc);
    }

    private void addEventListeners() {
        addButton.addActionListener(new AddCarOwnerActionListener());
        cancelButton.addActionListener(e -> cancelAddCarOwner());
    }

    private void cancelAddCarOwner() {
        Parent_JFrame.getMainFrame().setEnabled(true);
        frame.dispose();
    }

    private class AddCarOwnerActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            handleAddCarOwner();
        }

        private void handleAddCarOwner() {
            String cnic = cnicTextField.getText().trim();
            String name = nameTextField.getText().trim();
            String contact = contactTextField.getText().trim();

            try {
                validateInputs(cnic, name, contact);
                CarOwner carOwner = CarOwner.SearchByCNIC(cnic);
                if (carOwner == null) {
                    new CarOwner(0, 0, cnic, name, contact).Add(); // ID is Auto
                    refreshMainFrame();
                    JOptionPane.showMessageDialog(frame, CAR_OWNER_ADDED_MESSAGE);
                    frame.dispose();
                } else {
                    showErrorMessage(CNIC_ALREADY_REGISTERED_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                showErrorMessage(ex.getMessage());
            }
        }

        private void validateInputs(String cnic, String name, String contact) {
            if (!CarOwner.isCNICValid(cnic)) {
                throw new IllegalArgumentException(CNIC_INVALID_MESSAGE);
            }
            if (!CarOwner.isNameValid(name)) {
                throw new IllegalArgumentException(NAME_INVALID_MESSAGE);
            }
            if (!CarOwner.isContactNoValid(contact)) {
                throw new IllegalArgumentException(CONTACT_INVALID_MESSAGE);
            }
        }

        private void showErrorMessage(String message) {
            JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
        }

        private void refreshMainFrame() {
            Parent_JFrame.getMainFrame().getContentPane().removeAll();
            CarOwner_Details carOwnerDetails = new CarOwner_Details();
            Parent_JFrame.getMainFrame().add(carOwnerDetails.getMainPanel());
            Parent_JFrame.getMainFrame().getContentPane().revalidate();
            Parent_JFrame.getMainFrame().setEnabled(true);
        }
    }
}
