import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Creating data files
        createDataFiles();

        PasswordManager passwordManager = new PasswordManager();
        passwordManager.runUi();
    }

    private static void createDataFiles(){
        // Creating passwords.csv
        File passwords = new File("data/passwords.csv");
        try {
            if (!passwords.exists()) {
                passwords.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating/writing to file: " + e.getMessage());
            return;
        }


        // Creating users.csv
        File users = new File("data/users.csv");
        try {
            if (!users.exists()) {
                users.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating/writing to file: " + e.getMessage());
            return;
        }

        // Creating admins.csv
        File admins = new File("data/admins.csv");
        try {
            if (!admins.exists()) {
                admins.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating/writing to file: " + e.getMessage());
            return;
        }
    }
}