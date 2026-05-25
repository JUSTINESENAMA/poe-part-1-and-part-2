/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.login1;

import java.util.regex.Pattern;

/**
 *
 * @author Student
 */

public class Login1 {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellNumber;

    // Method to ensure username contains an underscore and is <= 5 characters
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Method to check password complexity rules
    public boolean checkPasswordComplexity(String password) {
        boolean hasCap = !password.equals(password.toLowerCase());
        boolean hasNum = password.matches(".*\\d.*");
        boolean hasSpec = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
        boolean isLongEnough = password.length() >= 8;

        return hasCap && hasNum && hasSpec && isLongEnough;
    }

    // Method to check if cell phone has international code (starts with +)
    public boolean checkCellPhoneNumber(String cellNumber) {
        // Requirements specify international code; usually implies starting with '+'
        return cellNumber.startsWith("+") && cellNumber.length() >= 10;
    }

    // Method to register the user and return status messages
    public String registerUser(String username, String password, String cellNumber, String firstName, String lastName) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellNumber = cellNumber;

        return "Password successfully captured.";
    }

    // Verifies login details
    public boolean loginUser(String enteredUser, String enteredPass) {
        return enteredUser.equals(this.username) && enteredPass.equals(this.password);
    }

    // Returns the login status message
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}   