package GUI;

import BackendCode.Booking;
import BackendCode.Car;
import BackendCode.CarOwner;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class Car_Details {

    private DefaultTableModel tablemodel;
    private JButton searchNameButton, searchRegNoButton, addButton, updateButton, removeButton, backButton, logoutButton;
    private JTextField searchNameTextField, searchRegNoTextField;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JPanel mainPanel;

    public Car_Details() {
        initializeComponents();
        setupLayout();
        addListeners();
        loadCarData();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void initializeComponents() {
        mainPanel = new JPanel();
        Parent_JFrame.getMainFrame().setTitle("Car Details - Rent-A-Car Management System");
        mainPanel.setLayout(new AbsoluteLayout());
        mainPanel.setMinimumSize(new Dimension(1366, 730));

        searchRegNoButton = new JButton("Search Reg_No");
        searchRegNoTextField = new JTextField();

        searchNameButton = new JButton("Search Name");
        searchNameTextField = new JTextField();

        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        removeButton = new JButton("Remove");
        backButton = new JButton("Back");
        logoutButton = new JButton("Logout");

        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();

        String[] columns = {"Sr#", "ID", "Maker", "Name", "Colour", "Type", "Seats", "Model", "Condition",
            "Reg No.", "Rent/hour", "Car Owner"};
        tablemodel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jTable1.setModel(tablemodel);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jTable1);
        jTable1.setFillsViewportHeight(true);
    }

    private void setupLayout() {
        mainPanel.add(searchRegNoButton, new AbsoluteConstraints(10, 15, 130, 22));
        mainPanel.add(searchRegNoTextField, new AbsoluteConstraints(145, 15, 240, 22));
        mainPanel.add(searchNameButton, new AbsoluteConstraints(390, 15, 130, 22));
        mainPanel.add(searchNameTextField, new AbsoluteConstraints(525, 15, 240, 22));
        mainPanel.add(jScrollPane1, new AbsoluteConstraints(10, 60, 1330, 550));
        mainPanel.add(removeButton, new AbsoluteConstraints(785, 625, 130, 22));
        mainPanel.add(addButton, new AbsoluteConstraints(450, 625, 130, 22));
        mainPanel.add(updateButton, new AbsoluteConstraints(620, 625, 130, 22));
        mainPanel.add(backButton, new AbsoluteConstraints(1106, 625, 100, 22));
        mainPanel.add(logoutButton, new AbsoluteConstraints(1236, 625, 100, 22));
    }

    private void addListeners() {
        ActionListener actionListener = new Car_Details_ActionListener();
        searchNameButton.addActionListener(actionListener);
        searchRegNoButton.addActionListener(actionListener);
        addButton.addActionListener(actionListener);
        updateButton.addActionListener(actionListener);
        removeButton.addActionListener(actionListener);
        backButton.addActionListener(actionListener);
        logoutButton.addActionListener(actionListener);
    }

    private void loadCarData() {
        ArrayList<Car> carObjects = Car.View();
        for (int i = 0; i < carObjects.size(); i++) {
            Car car = carObjects.get(i);
            int id = car.getID();
            String maker = car.getMaker();
            String name = car.getName();
            String color = car.getColour();
            String type = car.getType();
            int seatingCapacity = car.getSeatingCapacity();
            String model = car.getModel();
            String condition = car.getCondition();
            String regNo = car.getRegNo();
            int rentPerHour = car.getRentPerHour();
            CarOwner carOwner = car.getCarOwner();

            String[] oneRecord = {String.valueOf(i + 1), String.valueOf(id), maker, name, color, type,
                String.valueOf(seatingCapacity), model, condition, regNo, String.valueOf(rentPerHour),
                carOwner.getID() + ": " + carOwner.getName()};
            tablemodel.addRow(oneRecord);
        }

        // center aligning the text in all the columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // adjusting size of each column
        int[] columnWidths = {20, 20, 170, 170, 140, 150, 90, 90, 160, 170, 150, 150};
        for (int i = 0; i < columnWidths.length; i++) {
            jTable1.getColumnModel().getColumn(i).setMinWidth(columnWidths[i]);
        }

        jTable1.getTableHeader().setReorderingAllowed(false);
    }

    private class Car_Details_ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Search Reg_No":
                    searchByRegNo();
                    break;
                case "Search Name":
                    searchByName();
                    break;
                case "Add":
                    openAddCarDialog();
                    break;
                case "Update":
                    openUpdateCarDialog();
                    break;
                case "Remove":
                    openRemoveCarDialog();
                    break;
                case "Back":
                    navigateBack();
                    break;
                case "Logout":
                    logout();
                    break;
            }
        }

        private void searchByRegNo() {
            String regNo = searchRegNoTextField.getText().trim();
            if (!regNo.isEmpty()) {
                if (Car.isRegNoValid(regNo)) {
                    Car car = Car.SearchByRegNo(regNo);
                    if (car != null) {
                        JOptionPane.showMessageDialog(null, car.toString());
                        searchRegNoTextField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Required Car not found");
                        searchRegNoTextField.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Reg No.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please Enter Car Reg no first !");
            }
        }

        private void searchByName() {
            String name = searchNameTextField.getText().trim();
            if (!name.isEmpty()) {
                if (Car.isNameValid(name)) {
                    ArrayList<Car> carList = Car.SearchByName(name);
                    if (!carList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, carList.toString());
                        searchNameTextField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Required Car not found");
                        searchNameTextField.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Name !");
                    searchNameTextField.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please Enter Car Name first !");
            }
        }

        private void openAddCarDialog() {
            Parent_JFrame.getMainFrame().setEnabled(false);
            Car_Add addCarDialog = new Car_Add();
            addCarDialog.setVisible(true);
        }

        private void openUpdateCarDialog() {
            Parent_JFrame.getMainFrame().setEnabled(false);
            Car_Update updateCarDialog = new Car_Update();
            updateCarDialog.setVisible(true);
        }

        private void openRemoveCarDialog() {
            Parent_JFrame.getMainFrame().setEnabled(false);
            Car_Remove removeCarDialog = new Car_Remove();
            removeCarDialog.setVisible(true);
        }

        private void navigateBack() {
            Parent_JFrame.getMainFrame().setTitle("Rent-A-Car Management System [REBORN]");
            MainMenu mainMenu = new MainMenu();
            Parent_JFrame.getMainFrame().getContentPane().removeAll();
            Parent_JFrame.getMainFrame().add(mainMenu.getMainPanel());
            Parent_JFrame.getMainFrame().getContentPane().revalidate();
        }

        private void logout() {
            Parent_JFrame.getMainFrame().dispose();
            Runner runner = new Runner();
            JFrame frame = runner.getFrame();
            Login login = new Login();
            JPanel panel = login.getMainPanel();
            frame.add(panel);
            frame.setVisible(true);
        }
    }
}
