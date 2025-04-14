import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame {
    private JPanel panel1;
    private JTextField textField2;
    private JTextField textField3;
    private JPasswordField passwordField1;
    private JButton registerButton;
    private JButton tapHereToLogButton;

    public Register() {
        setTitle("Register Now!");
        setContentPane(panel1);
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField2.getText().trim();
                String email = textField3.getText().trim();
                String password = String.valueOf(passwordField1.getPassword()).trim();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields must be completed.");
                    return;
                }

                if (!email.contains("@") || !email.contains(".")) {
                    JOptionPane.showMessageDialog(null, "Enter a valid email address.");
                    return;
                }

                if (Connect.registerUser(username, email, password)) {
                    JOptionPane.showMessageDialog(null, "Registration successful!");
                    dispose();
                    new Login();
                } else {
                    JOptionPane.showMessageDialog(null, "Registration failed. Email or username may already be in use.");
                }
            }
        });
        tapHereToLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });
    }
}
