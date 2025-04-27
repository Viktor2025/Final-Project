import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame {
    private JPanel panel1;
    private JTextField textField1; 
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel email;
    private JLabel password;
    private JLabel notAMember;
    private JLabel title;

    public Login() {
        setTitle("Login Page");
        setContentPane(panel1);
        setSize(450, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loadLoginLabelsFromDB();
        loginButton.setText("Login");
        registerButton.setText("Register");
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailInput = textField1.getText().trim();
                String passwordInput = String.valueOf(passwordField1.getPassword()).trim();

                if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Email and password are required.");
                    return;
                }

                if (!emailInput.contains("@") || !emailInput.contains(".")) {
                    JOptionPane.showMessageDialog(null, "Invalid email format.");
                    return;
                }

                if (Connect.loginUser(emailInput, passwordInput)) {
                    JOptionPane.showMessageDialog(null, "Welcome back, " + emailInput + "!", "Login Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new HomePage1();
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Check credentials or create an account.");
                }
            }
        });

        registerButton.addActionListener(e -> {
            dispose();
            new Register(); 
        });
    }

    private void loadLoginLabelsFromDB() {
        try (Connection conn = Connect.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT label_name, label_text FROM login_labels");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("label_name");
                String text = rs.getString("label_text");

                switch (name) {
                    case "title" -> title.setText(text);
                    case "email" -> email.setText(text);
                    case "password" -> password.setText(text);
                    case "notAMember" -> notAMember.setText(text);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Could not load login labels:\n" + e.getMessage());
        }
    }
}
