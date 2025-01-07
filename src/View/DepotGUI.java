package View;

import Model.Customer;
import Model.Parcel;
import Model.ParcelMap;
import Model.QueueOfCustomers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DepotGUI extends JFrame {
    private JPanel cardPanel;
    private ParcelMap parcelMap;
    private QueueOfCustomers queueOfCustomers;
    private DefaultTableModel parcelTableModel, customerTableModel;
    private JTable parcelTable, customerTable;

    public DepotGUI() {
        initializeComponents();
        parcelMap = new ParcelMap();
        queueOfCustomers = new QueueOfCustomers();
    }

    private void initializeComponents() {
        setTitle("Depot System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Removed worker, admin, and customer buttons

        // Add search and calculate fee buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton searchCustomerBtn = new JButton("Search Customer");
        JButton calculateFeeBtn = new JButton("Calculate Fee");

        searchCustomerBtn.addActionListener(e -> searchCustomer());
        calculateFeeBtn.addActionListener(e -> calculateFee());

        buttonPanel.add(searchCustomerBtn);
        buttonPanel.add(calculateFeeBtn);

        add(buttonPanel, BorderLayout.NORTH);

        cardPanel = new JPanel(new CardLayout());
        cardPanel.add(createWorkerPanel(), "Worker");
        // Removed admin and customer panels
        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createWorkerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel tablePanel = new JPanel(new GridLayout(1, 2));

        parcelTableModel = new DefaultTableModel(new Object[]{"ID", "Days in Depot", "Weight", "Length", "Width", "Height", "Status"}, 0);
        parcelTable = new JTable(parcelTableModel);
        JScrollPane parcelScrollPane = new JScrollPane(parcelTable);

        customerTableModel = new DefaultTableModel(new Object[]{"Queue No", "Name", "Parcel ID"}, 0);
        customerTable = new JTable(customerTableModel);
        JScrollPane customerScrollPane = new JScrollPane(customerTable);

        tablePanel.add(parcelScrollPane);
        tablePanel.add(customerScrollPane);
        panel.add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(createButton("Load Parcels", "src/Parcels.csv", parcelTableModel));
        buttonPanel.add(createButton("Load Customers", "src/Custs.csv", customerTableModel));

        // New functionality buttons
        JButton addCustomerBtn = new JButton("Add Customer");
        JButton removeCustomerBtn = new JButton("Remove Customer");
        JButton addParcelBtn = new JButton("Add Parcel");
        JButton markParcelBtn = new JButton("Mark Parcel");

        addCustomerBtn.addActionListener(e -> addCustomer());
        removeCustomerBtn.addActionListener(e -> removeCustomer());
        addParcelBtn.addActionListener(e -> addParcel());
        markParcelBtn.addActionListener(e -> markParcel());

        buttonPanel.add(addCustomerBtn);
        buttonPanel.add(removeCustomerBtn);
        buttonPanel.add(addParcelBtn);
        buttonPanel.add(markParcelBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createButton(String title, String filePath, DefaultTableModel model) {
        JButton button = new JButton(title);
        button.addActionListener(e -> loadData(filePath, model));
        return button;
    }

    private void loadData(String filePath, DefaultTableModel model) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (filePath.contains("Parcels")) {
                    String id = data[0].trim();
                    int daysInDepot = Integer.parseInt(data[1].trim());
                    double weight = Double.parseDouble(data[2].trim());
                    int length = Integer.parseInt(data[3].trim());
                    int width = Integer.parseInt(data[4].trim());
                    int height = Integer.parseInt(data[5].trim());
                    Parcel parcel = new Parcel(id, daysInDepot, weight, length, width, height);
                    parcelTableModel.addRow(new Object[]{parcel.getId(), parcel.getDaysInDepot(), parcel.getWeight(),
                            parcel.getLength(), parcel.getWidth(), parcel.getHeight(), parcel.getStatus()});
                } else if (filePath.contains("Custs")) {
                    String name = data[0].trim();
                    String parcelId = data[1].trim();
                    int queueNumber = customerTableModel.getRowCount() + 1;
                    Customer customer = new Customer(queueNumber, name, parcelId);
                    queueOfCustomers.addCustomer(customer);
                    customerTableModel.addRow(new Object[]{customer.getQueueNumber(), customer.getName(), customer.getParcelId()});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
        }
    }

    private void addCustomer() {
        String name = JOptionPane.showInputDialog(this, "Enter Customer Name:");
        String parcelId = JOptionPane.showInputDialog(this, "Enter Parcel ID:");
        if (name != null && parcelId != null) {
            int queueNumber = customerTableModel.getRowCount() + 1; // Increment queue number
            Customer customer = new Customer(queueNumber, name, parcelId);
            queueOfCustomers.addCustomer(customer);
            customerTableModel.addRow(new Object[]{customer.getQueueNumber(), customer.getName(), customer.getParcelId()});
        }
    }

    private void removeCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            customerTableModel.removeRow(selectedRow);
            queueOfCustomers.removeCustomer(selectedRow + 1); // Adjust for queue number
        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to remove.");
        }
    }

    private void addParcel() {
        String id = JOptionPane.showInputDialog(this, "Enter Parcel ID:");
        String daysInDepotStr = JOptionPane.showInputDialog(this, "Enter Days in Depot:");
        String weightStr = JOptionPane.showInputDialog(this, "Enter Weight:");
        String lengthStr = JOptionPane.showInputDialog(this, "Enter Length:");
        String widthStr = JOptionPane.showInputDialog(this, "Enter Width:");
        String heightStr = JOptionPane.showInputDialog(this, "Enter Height:");

        if (id != null && daysInDepotStr != null && weightStr != null && lengthStr != null && widthStr != null && heightStr != null) {
            try {
                int daysInDepot = Integer.parseInt(daysInDepotStr);
                double weight = Double.parseDouble(weightStr);
                int length = Integer.parseInt(lengthStr);
                int width = Integer.parseInt(widthStr);
                int height = Integer.parseInt(heightStr);

                Parcel parcel = new Parcel(id, daysInDepot, weight, length, width, height);
                parcelMap.addParcel(parcel);
                parcelTableModel.addRow(new Object[]{parcel.getId(), parcel.getDaysInDepot(), parcel.getWeight(),
                        parcel.getLength(), parcel.getWidth(), parcel.getHeight(), parcel.getStatus()});
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error: Please enter valid numbers for days, weight, and dimensions.");
            }
        }
    }

    private void markParcel() {
        int selectedRow = parcelTable.getSelectedRow();
        if (selectedRow != -1) {
            String parcelId = (String) parcelTableModel.getValueAt(selectedRow, 0);
            Parcel parcel = Parcel.getParcelById(parcelId);
            if (parcel != null) {
                parcel.setStatus("Processed");
                parcelTableModel.setValueAt(parcel.getStatus(), selectedRow, 6); // Update status in table
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a parcel to mark.");
        }
    }

    private void searchCustomer() {
        String parcelId = JOptionPane.showInputDialog(this, "Enter Parcel ID to search for the associated customer:");
        if (parcelId != null) {
            Customer foundCustomer = queueOfCustomers.getCustomers().stream()
                    .filter(customer -> customer.getParcelId().equalsIgnoreCase(parcelId))
                    .findFirst()
                    .orElse(null);

            if (foundCustomer != null) {
                JOptionPane.showMessageDialog(this, "Customer found: " + foundCustomer.getName());
            } else {
                JOptionPane.showMessageDialog(this, "Customer not found for Parcel ID: " + parcelId);
            }
        }
    }

    private void calculateFee() {
        int selectedRow = parcelTable.getSelectedRow();
        if (selectedRow != -1) {
            String parcelId = (String) parcelTableModel.getValueAt(selectedRow, 0);
            Parcel parcel = Parcel.getParcelById(parcelId);
            if (parcel != null) {
                double fee = parcel.calculateFee();
                JOptionPane.showMessageDialog(this, "Fee for Parcel " + parcelId + ": " + fee);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a parcel to calculate fee.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DepotGUI depotView = new DepotGUI();
            depotView.setVisible(true);
        });
    }
}


