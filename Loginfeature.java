/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.loginfeature;

/**
 *
 * @author Justine
 */
public class LoginFeature {

    private final RegistrationFeature registrationFeature;

    private boolean isLoggedIn;

    public LoginFeature(RegistrationFeature registrationFeature) {
        this.registrationFeature = registrationFeature;
        this.isLoggedIn = false;
    }

    public boolean loginUser(String username, String password) {

        isLoggedIn = username != null &&
                     password != null &&
                     username.equals(registrationFeature.getUsername()) &&
                     password.equals(registrationFeature.getPassword());

        return isLoggedIn;
    }

    public String returnLoginStatus() {

        if (isLoggedIn) {
            return "Welcome back "
                    + registrationFeature.getFirstName()
                    + " "
                    + registrationFeature.getLastName()
                    + "!\nIt is great to see you.";
        }

        return "Username or password incorrect, please try again!";
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public RegistrationFeature getRegistrationFeature() {
        return registrationFeature;
    }
}