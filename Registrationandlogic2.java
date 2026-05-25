/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registrationandlogic2;

/**
 *
 * @author Student
 */
import java.util.Scanner;
public class Registrationandlogic2 {

  public static void main(String[] args) {
     Scanner input = new Scanner(System.in);
    
    System.out.print("First Name: ");
    String firstName = input.nextLine();
    
    System.out.print("Last Name: ");
    String lastName = input.nextLine();
    
    System.out.print("Username: ");
    String username = input.nextLine();
    
    System.out.print("Password: ");
    String password = input.nextLine();
    
    System.out.print("Enter cellphone eg +276911056 : ");
    String cellphone = input.nextLine();
   
    
    boolean hasUnderscore = username.contains("_");
    boolean isCorrectLength = username.length() <= 5;
    boolean hasCorrectLength = password.length() >=8;
    boolean hasUppercase = password.matches(".[A-Z].");
    boolean hasNumber = password.matches(".[0-9].");
    boolean hasSpecial = password.matches(".[^a-zA-Z0-9].");
    boolean isFormattedCorrectly = cellphone.startsWith("+") && cellphone.length() <=10;
   
     
     
    if (hasUnderscore && isCorrectLength) {
    System.out.println("Username successfully captured.");
    }
    else{ System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than  five characters in length.");
    
    }
    if(hasCorrectLength && hasUppercase && hasNumber && hasSpecial) {
    System.out.println("Password successully captured.");
    }
    else{ System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
    }
    
    if (isFormattedCorrectly) {
        System.out.println("Cellphone number succesfully added.");    
        }
    else{ System.out.println("Cellphone number incorrectly formatted or does not contain international code.");
        }
        
        
     System.out.println("\n         Login       ");
     System.out.print("Enter Username: ");
     String User = input.nextLine();
     System.out.print("Enter Password: ");
     String Pass = input.nextLine();
     
     boolean LoggedIn = User.equals(username) && Pass.equals(password);

     System.out.println(" ");

     if (LoggedIn) {
      System.out.println("Welcome " + firstName + ", " + lastName + " it is great to see you.");
     } else {
      System.out.println("Username or password incorrect, please try again.");
     }

    
    input.close();
    }
}