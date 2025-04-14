import javax.swing.*;
import java.awt.event.*;

public class Login extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton registerButton;

    public Login() {
        setTitle("Login Page");
        setContentPane(panel1);
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = textField1.getText().trim();
                String password = String.valueOf(passwordField1.getPassword()).trim();

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Email and password are required.");
                    return;
                }

                if (!email.contains("@")) {
                    JOptionPane.showMessageDialog(null, "Invalid email format.");
                    return;
                }

                if (Connect.loginUser(email, password)) {
                    JOptionPane.showMessageDialog(null, "Welcome back!");
                    // TODO: Navigate to next window (e.g., dashboard)
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Check credentials or create an account.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Register();
            }
        });
    }
}
