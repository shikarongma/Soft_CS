// ContactApp.java  
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DataBase.*;
import Server.ContactService;

public class ContactApp extends JFrame implements AutoCloseable{
    private ContactService contactService = new ContactService();
    private DefaultTableModel tableModel;
    private JTable contactTable;


    JTextField nameField = new JTextField(20);
    JTextField phoneField = new JTextField(20);
    JTextField emailField = new JTextField(20);

    public ContactApp() {
        setTitle("Personal Address Book");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table model
        tableModel = new DefaultTableModel(new String[]{"Name", "Phone", "Email"}, 0);
        contactTable = new JTable(tableModel);
        updateTable();

        // Create buttons and panels
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JTextField nameField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        JButton addButton = new JButton("Add");
        JButton viewButton = new JButton("View");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(addButton);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(viewButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(new JLabel()); // Empty label for spacing

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(contactTable);
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.NORTH);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewContact();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContact();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });
    }

    private void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
            Contact contact = new Contact(name, phone, email);
            contactService.addContact(contact);
            updateTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewContact() {
        int selectedRow = contactTable.getSelectedRow();
        if (selectedRow >= 0) {
            Contact contact = contactService.getContactByIndex(selectedRow);
            JOptionPane.showMessageDialog(this, contact.toString(), "Contact Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a contact", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateContact() {
        int selectedRow = contactTable.getSelectedRow();
        if (selectedRow >= 0) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
                Contact contact = new Contact(name, phone, email);
                contactService.updateContact(selectedRow, contact);
                updateTable();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a contact", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteContact() {
        int selectedRow = contactTable.getSelectedRow();
        if (selectedRow >= 0) {
            contactService.deleteContact(selectedRow);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a contact", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable() {
        List<Contact> contacts = contactService.getAllContacts();
        tableModel.setRowCount(0); // Clear table  

        for (Contact contact : contacts) {
            tableModel.addRow(new Object[]{contact.getName(), contact.getPhone(), contact.getEmail()});
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    @Override
    public void close() throws Exception {

    }
}