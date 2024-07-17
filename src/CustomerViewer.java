import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerViewer extends JFrame implements ActionListener {

    private JLabel idLabel, lastNameLabel, firstNameLabel, phoneLabel;
    private JButton prevButton, nextButton;

    private int currentIndex = 0;

    private String[][] customers = {
            {"1", "Chenda", "Sovisal", "092888999"},
            {"2", "Kom", "Lina", "092008999"},
            {"3", "Chan", "Seyha", "092777666"}
    };

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

        displayCustomer(currentIndex);

        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new CustomerViewer();
    }

    private void displayCustomer(int index) {
        if (index < 0 || index >= customers.length) {
            return;
        }
        idLabel.setText(customers[index][0]);
        lastNameLabel.setText(customers[index][1]);
        firstNameLabel.setText(customers[index][2]);
        phoneLabel.setText(customers[index][3]);

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
