/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package st10503287;

import javax.swing.JOptionPane;

/**
 *
 * @author Justine
 */
public class LoginScreen extends javax.swing.JFrame {

    private LoginFeature loginFeature;

    public LoginScreen(LoginFeature loginFeature) {

        this.loginFeature = loginFeature;

        initComponents();

        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        loginPanel = new javax.swing.JPanel();
        loginTitle1 = new javax.swing.JLabel();
        loginTitle2 = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        logo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login Screen");

        loginPanel.setBackground(new java.awt.Color(255, 255, 255));

        loginTitle1.setFont(new java.awt.Font("Segoe UI", 1, 24));
        loginTitle1.setText("Login");

        loginTitle2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        loginTitle2.setText("Enter your login details");

        usernameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        usernameLabel.setText("Username");

        passwordLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        passwordLabel.setText("Password");

        usernameField.setFont(new java.awt.Font("Segoe UI", 0, 14));

        passwordField.setFont(new java.awt.Font("Segoe UI", 0, 14));

        loginButton.setFont(new java.awt.Font("Segoe UI", 1, 14));
        loginButton.setText("Login");

        loginButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        logo.setFont(new java.awt.Font("Segoe UI", 1, 18));
        logo.setText("Messaging App");

        javax.swing.GroupLayout loginPanelLayout =
                new javax.swing.GroupLayout(loginPanel);

        loginPanel.setLayout(loginPanelLayout);

        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loginTitle1)
                    .addComponent(loginTitle2)
                    .addComponent(usernameLabel)
                    .addComponent(usernameField,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            250,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordField,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            250,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginButton,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            250,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logo))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(logo)
                .addGap(20, 20, 20)
                .addComponent(loginTitle1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loginTitle2)
                .addGap(20, 20, 20)
                .addComponent(usernameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameField,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        35,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(passwordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordField,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        35,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(loginButton,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        40,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout =
                new javax.swing.GroupLayout(getContentPane());

        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)
        );

        pack();
    }

private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {

    String username = usernameField.getText();
    String password = new String(passwordField.getPassword());

    boolean accessGranted = loginFeature.loginUser(username, password);

    String loginFeedback = loginFeature.returnLoginStatus();

    if (accessGranted) {

        JOptionPane.showMessageDialog(
                this,
                loginFeedback,
                "Access Granted",
                JOptionPane.INFORMATION_MESSAGE
        );

        MessageFeature.setLoggedInUsername(
                loginFeature.getRegistrationFeature().getFirstName()
                + " "
                + loginFeature.getRegistrationFeature().getLastName()
        );

        MessageFeature.loadAllMessagesFromJsonFiles();

        MessageScreen messageScreen = new MessageScreen(
                loginFeature.getRegistrationFeature(),
                loginFeature
        );

        messageScreen.setVisible(true);
        messageScreen.setLocationRelativeTo(null);

        dispose();

    } else {

        JOptionPane.showMessageDialog(
                this,
                loginFeedback,
                "Access Denied",
                JOptionPane.ERROR_MESSAGE
        );
    }
}

    private javax.swing.JButton loginButton;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JLabel loginTitle1;
    private javax.swing.JLabel loginTitle2;
    private javax.swing.JLabel logo;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
}