import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CustomerViewer extends JFrame implements ActionListener {

    private JLabel idLabel, lastNameLabel, firstNameLabel, phoneLabel;
    private JButton prevButton, nextButton;

    private int currentIndex = 0;
    private Customer[] customers;
    private int totalCustomers;

    record Customer(int customerId, String lastName, String firstName, String phone) {}

    public CustomerViewer() {
        setTitle("Customer");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel idTextLabel = new JLabel("ID:");
        idLabel = new JLabel();

        JLabel lastNameTextLabel = new JLabel("Last Name:");
        lastNameLabel = new JLabel();

        JLabel firstNameTextLabel = new JLabel("First Name:");
        firstNameLabel = new JLabel();

        JLabel phoneTextLabel = new JLabel("Phone:");
        phoneLabel = new JLabel();

        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");

        prevButton.addActionListener(this);
        nextButton.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(idTextLabel, gbc);
        gbc.gridx = 1;
        add(idLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lastNameTextLabel, gbc);
        gbc.gridx = 1;
        add(lastNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(firstNameTextLabel, gbc);
        gbc.gridx = 1;
        add(firstNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(phoneTextLabel, gbc);
        gbc.gridx = 1;
        add(phoneLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(prevButton, gbc);
        gbc.gridx = 1;
        add(nextButton, gbc);

        loadCustomers();
        displayCustomer(currentIndex);

        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new CustomerViewer();
    }

    private void loadCustomers() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/TestingDB";
            String user = "postgres";
            String password = "helen15121512";

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM customers");

            resultSet.last();
            totalCustomers = resultSet.getRow();
            customers = new Customer[totalCustomers];
            resultSet.beforeFirst();

            int index = 0;
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String lastName = resultSet.getString("customer_last_name");
                String firstName = resultSet.getString("customer_first_name");
                String phone = resultSet.getString("customer_phone");
                customers[index++] = new Customer(customerId, lastName, firstName, phone);
            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading customer data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayCustomer(int index) {
        if (index < 0 || index >= customers.length) {
            return;
        }
        Customer customer = customers[index];
        idLabel.setText(String.valueOf(customer.customerId()));
        lastNameLabel.setText(customer.lastName());
        firstNameLabel.setText(customer.firstName());
        phoneLabel.setText(customer.phone());

        prevButton.setEnabled(index > 0);
        nextButton.setEnabled(index < customers.length - 1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == prevButton && currentIndex > 0) {
            currentIndex--;
        } else if (e.getSource() == nextButton && currentIndex < customers.length - 1) {
            currentIndex++;
        }
        displayCustomer(currentIndex);
    }
}
