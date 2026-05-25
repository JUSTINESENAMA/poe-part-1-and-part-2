/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registrationsreen;

/**
 *
 * @author Justine
 */
public class import javax.swing.JOptionPane;

public class RegistrationScreen extends javax.swing.JFrame {

    private RegistrationFeature registrationFeature;

    public RegistrationScreen() {

        registrationFeature = new RegistrationFeature();

        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        registrationFeature.setFirstName(firstName);
        registrationFeature.setLastName(lastName);
        registrationFeature.setUsername(username);
        registrationFeature.setPassword(password);

        JOptionPane.showMessageDialog(
                this,
                "Registration successful!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );

        LoginFeature loginFeature = new LoginFeature(registrationFeature);

        LoginScreen loginScreen = new LoginScreen(loginFeature);
        loginScreen.setVisible(true);

        dispose();
    }

    private javax.swing.JTextField firstNameField;
    private javax.swing.JLabel firstNameLabel;

    private javax.swing.JTextField lastNameField;
    private javax.swing.JLabel lastNameLabel;

    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;

    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;

    private javax.swing.JButton registerButton;

    private javax.swing.JPanel registrationPanel;

    private javax.swing.JLabel titleLabel;
}
