/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrationfeaturetest;

public class RegistrationFeature {

    private String username;
    private String password;
    private String cellphone;
    private String firstName;
    private String lastName;

    public String registerUser(String username, String password,
                               String cellphone, String firstName,
                               String lastName) {

        StringBuilder result = new StringBuilder();
        boolean valid = true;

        // Username validation
        if (username != null &&
                username.contains("_") &&
                username.length() <= 5) {

            this.username = username;
            result.append("Username successfully captured\n");

        } else {
            result.append("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.\n");
            valid = false;
        }

        // Password validation
        if (password != null &&
                password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[^a-zA-Z0-9].*")) {

            this.password = password;
            result.append("Password successfully captured\n");

        } else {
            result.append("Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n");
            valid = false;
        }

        // Cellphone validation
        if (cellphone != null &&
                cellphone.matches("^\\+27\\d{9}$")) {

            this.cellphone = cellphone;
            result.append("Cellphone number successfully captured\n");

        } else {
            result.append("Cellphone number is incorrectly formatted or does not contain an international code, please correct the number and try again.\n");
            valid = false;
        }

        // First name validation
        if (firstName != null &&
                firstName.matches("[A-Za-z]+")) {

            this.firstName = firstName;
            result.append("First name successfully captured\n");

        } else {
            result.append("First name is invalid.\n");
            valid = false;
        }

        // Last name validation
        if (lastName != null &&
                lastName.matches("[A-Za-z]+")) {

            this.lastName = lastName;
            result.append("Last name successfully captured\n");

        } else {
            result.append("Last name is invalid.\n");
            valid = false;
        }

        if (valid) {
            result.append("The Registration process is complete.");
        } else {
            result.append("Please try again.");
        }

        return result.toString();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}