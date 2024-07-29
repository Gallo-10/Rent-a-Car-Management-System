package GUI;

import BackendCode.CarOwner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class CarOwner_Update {

    private JButton okButton, cancelButton;
    private JLabel idLabel, idValidityLabel;
    private JTextField idTextField;
    JFrame frame;
    private CarOwner carOwner;

    public CarOwner_Update() {
        frame = new JFrame("Update CarOwner");
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

        initializeComponents();
        addComponentsToFrame();
        registerActionListeners();
    }

    private void initializeComponents() {
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        idLabel = new JLabel("Enter ID to be Updated");
        idValidityLabel = new JLabel();
        idTextField = new JTextField();

        idTextField.setPreferredSize(new Dimension(240, 22));
        idLabel.setPreferredSize(new Dimension(175, 22));
        idValidityLabel.setPreferredSize(new Dimension(240, 9));
        idValidityLabel.setForeground(Color.red);
    }

    private void addComponentsToFrame() {
        frame.add(idLabel, new AbsoluteConstraints(10, 5));
        frame.add(idTextField, new AbsoluteConstraints(195, 5));
        frame.add(idValidityLabel, new AbsoluteConstraints(195, 30));
        frame.add(okButton, new AbsoluteConstraints(100, 225, 100, 22));
        frame.add(cancelButton, new AbsoluteConstraints(250, 225, 100, 22));
    }

    private void registerActionListeners() {
        okButton.addActionListener(e -> handleOkAction());
        cancelButton.addActionListener(e -> handleCancelAction());
    }

    private void handleOkAction() {
        String id = idTextField.getText().trim();
        if (id.isEmpty()) {
            idValidityLabel.setText("Enter ID !");
            return;
        }
        if (!CarOwner.isIDvalid(id)) {
            idValidityLabel.setText("Invalid ID !");
            return;
        }

        carOwner = CarOwner.SearchByID(Integer.parseInt(id));
        if (carOwner != null) {
            Parent_JFrame.getMainFrame().setEnabled(false);
            frame.dispose();
            new UpdateCarOwner_Inner(carOwner).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Required ID is not found !");
        }
    }

    private void handleCancelAction() {
        Parent_JFrame.getMainFrame().setEnabled(true);
        frame.dispose();
    }

    public class UpdateCarOwner_Inner extends JFrame {

        private JButton updateButton, cancelButton;
        private JLabel cnicLabel, nameLabel, contactLabel;
        private JLabel cnicValidityLabel, nameValidityLabel, contactValidityLabel;
        private JTextField cnicTextField, nameTextField, contactTextField;
        private CarOwner carOwner;

        public UpdateCarOwner_Inner(CarOwner carOwner) {
            super("Update CarOwner");
            this.carOwner = carOwner;
            setLayout(new AbsoluteLayout());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(new Dimension(450, 290));
            setResizable(false);
            setLocationRelativeTo(this);

            initializeComponents();
            addComponentsToFrame();
            registerActionListeners();
        }

        private void initializeComponents() {
            updateButton = new JButton("Update");
            cancelButton = new JButton("Cancel");

            cnicLabel = new JLabel("Enter CNIC (without dashes)");
            nameLabel = new JLabel("Enter Name");
            contactLabel = new JLabel("Enter Contact");

            cnicTextField = new JTextField(carOwner.getCNIC());
            nameTextField = new JTextField(carOwner.getName());
            contactTextField = new JTextField(carOwner.getContact_No());

            cnicValidityLabel = new JLabel();
            nameValidityLabel = new JLabel();
            contactValidityLabel = new JLabel();

            setPreferredSizes();
        }

        private void setPreferredSizes() {
            cnicTextField.setPreferredSize(new Dimension(240, 22));
            nameTextField.setPreferredSize(new Dimension(240, 22));
            contactTextField.setPreferredSize(new Dimension(240, 22));

            cnicLabel.setPreferredSize(new Dimension(175, 22));
            nameLabel.setPreferredSize(new Dimension(175, 22));
            contactLabel.setPreferredSize(new Dimension(175, 22));

            cnicValidityLabel.setPreferredSize(new Dimension(240, 9));
            nameValidityLabel.setPreferredSize(new Dimension(240, 9));
            contactValidityLabel.setPreferredSize(new Dimension(240, 9));

            cnicValidityLabel.setForeground(Color.red);
            nameValidityLabel.setForeground(Color.red);
            contactValidityLabel.setForeground(Color.red);
        }

        private void addComponentsToFrame() {
            add(cnicLabel, new AbsoluteConstraints(10, 5));
            add(cnicTextField, new AbsoluteConstraints(195, 5));
            add(cnicValidityLabel, new AbsoluteConstraints(195, 30));
            add(nameLabel, new AbsoluteConstraints(10, 42));
            add(nameTextField, new AbsoluteConstraints(195, 42));
            add(nameValidityLabel, new AbsoluteConstraints(195, 66));
            add(contactLabel, new AbsoluteConstraints(10, 77));
            add(contactTextField, new AbsoluteConstraints(195, 77));
            add(contactValidityLabel, new AbsoluteConstraints(195, 102));
            add(updateButton, new AbsoluteConstraints(100, 225, 100, 22));
            add(cancelButton, new AbsoluteConstraints(250, 225, 100, 22));
        }

        private void registerActionListeners() {
            updateButton.addActionListener(e -> handleUpdateAction());
            cancelButton.addActionListener(e -> handleCancelAction());
        }

        private void handleUpdateAction() {
            String cnic = validateCNIC();
            String name = validateName();
            String contact = validateContact();

            if (cnic != null && name != null && contact != null) {
                carOwner.setCNIC(cnic);
                carOwner.setName(name);
                carOwner.setContact_No(contact);
                carOwner.Update();
                updateMainFrame();
                JOptionPane.showMessageDialog(null, "Record Successfully Updated !");
                dispose();
            }
        }

        private String validateCNIC() {
            String cnic = cnicTextField.getText().trim();
            if (cnic.isEmpty()) {
                cnicValidityLabel.setText("Enter CNIC !");
                return null;
            }
            if (!CarOwner.isCNICValid(cnic)) {
                cnicValidityLabel.setText("Invalid CNIC !");
                return null;
            }
            CarOwner existingOwner = CarOwner.SearchByCNIC(cnic);
            if (existingOwner != null && !cnic.equals(carOwner.getCNIC())) {
                JOptionPane.showMessageDialog(null, "This CNIC is already registered !");
                return null;
            }
            return cnic;
        }

        private String validateName() {
            String name = nameTextField.getText().trim();
            if (name.isEmpty()) {
                nameValidityLabel.setText("Enter Name !");
                return null;
            }
            if (!CarOwner.isNameValid(name)) {
                nameValidityLabel.setText("Invalid Name !");
                return null;
            }
            return name;
        }

        private String validateContact() {
            String contact = contactTextField.getText().trim();
            if (contact.isEmpty()) {
                contactValidityLabel.setText("Enter Contact Number !");
                return null;
            }
            if (!CarOwner.isContactNoValid(contact)) {
                contactValidityLabel.setText("Invalid Contact Number!");
                return null;
            }
            return contact;
        }

        private void updateMainFrame() {
            Parent_JFrame.getMainFrame().getContentPane().removeAll();
            CarOwner_Details cd = new CarOwner_Details();
            Parent_JFrame.getMainFrame().add(cd.getMainPanel());
            Parent_JFrame.getMainFrame().getContentPane().revalidate();
            Parent_JFrame.getMainFrame().setEnabled(true);
        }

        private void handleCancelAction() {
            Parent_JFrame.getMainFrame().setEnabled(true);
            dispose();
        }
    }
}
