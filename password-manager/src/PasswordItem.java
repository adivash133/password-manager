import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// The PasswordItem Class and the password information
public class PasswordItem {
    private User user;
    private String username;
    private String password;
    private Date dateCreated;
    private Date dateUpdate;

    // Relevant fields for password information
    public static final String FILE_PATH = "data/passwords.csv";

// File path for the CSV file that stores password data
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getter and setter methods for user
    public User getUser() {
        return user;
    }

// Method to get the last password ID that was assigned from the CSV
    public int getLastPasswordId() {
        int lastAssignedId = 0;
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(FILE_PATH));

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                if (parts.length > 0) {
                    lastAssignedId = Integer.parseInt(parts[0]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return lastAssignedId;
    }
// Method is used to check if the password ID is valid
    public boolean isPasswordIdValid(int passwordId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                if (parts.length > 0) {
                    int existingId = Integer.parseInt(parts[0]);
                    if (existingId == passwordId) {
                        return true;  // Found a match
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return false;  // No match found or an error occurred
    }

    // Method to edit password entry with the specified ID
    public void editPassword(int passwordId, String newUsername, String newPassword) {
        // Read the existing data from the CSV file
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Find and edit the password entry with the specified ID
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length > 0) {
                int existingId = Integer.parseInt(parts[0]);
                if (existingId == passwordId) {
                    // Update the username and password fields
                    parts[2] = newUsername;  // Update username
                    parts[3] = newPassword;  // Update password
                    lines.set(i, String.join(",", parts));
                    break;  // Stop searching once the entry is found and updated
                }
            }
        }

        // Write the modified data back to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    // Method to delete a password entry from the specified ID
    public void deletePassword(int passwordId) {
        // Read the existing data from the CSV file
        List<String> lines = new ArrayList<>();
        boolean passwordFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    int existingId = Integer.parseInt(parts[0]);
                    if (existingId == passwordId) {
                        passwordFound = true;
                    } else {
                        lines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Write the modified data back to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return;
        }

        if (passwordFound) {
            System.out.println("Password with ID " + passwordId + " deleted successfully.");
        } else {
            System.out.println("Password with ID " + passwordId + " not found. No deletion performed.");
        }
    }

// Searching method for a password
    public void searchPassword(String searchTerm) {
        // Read the existing data from the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            System.out.println("Search results for \"" + searchTerm + "\":");

            System.out.printf("%-20s", "Id");
            System.out.printf("%-20s", "UserId");
            System.out.printf("%-20s", "Username");
            System.out.printf("%-20s", "Password");
            System.out.printf("%-20s", "Website Name");
            System.out.printf("%-20s", "Website URL");
            System.out.printf("%-20s", "Game Name");
            System.out.printf("%-20s", "Game Developer");
            System.out.printf("%-20s", "Desktop App Name");
            System.out.printf("%-20s", "Date Created");
            System.out.printf("%-20s", "Updated Date");
            System.out.println();

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                if (parts.length > 2) {  // Ensure there are enough fields to check
                    String websiteName = parts[4];
                    String gameName = parts[6];
                    String desktopAppName = parts[8];

                    if (websiteName.equalsIgnoreCase(searchTerm) ||
                            gameName.equalsIgnoreCase(searchTerm) ||
                            desktopAppName.equalsIgnoreCase(searchTerm)) {
                        // Print the matching entry
                        String[] row = line.split(",");
                        for(String index : row){
                            System.out.printf("%-20s", index);
                        }
                        System.out.println();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    //  Viewing method for a password
    public void viewPasswords(int userId){
        String filePath ="data/passwords.csv";
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(filePath));
            System.out.printf("%-20s", "Id");
            System.out.printf("%-20s", "UserId");
            System.out.printf("%-20s", "Username");
            System.out.printf("%-20s", "Password");
            System.out.printf("%-20s", "Website Name");
            System.out.printf("%-20s", "Website URL");
            System.out.printf("%-20s", "Game Name");
            System.out.printf("%-20s", "Game Developer");
            System.out.printf("%-20s", "Desktop App Name");
            System.out.printf("%-20s", "Date Created");
            System.out.printf("%-20s", "Updated Date");
            System.out.println();


            while ((line = reader.readLine()) != null){

                String[] row = line.split(",");
                if(Integer.parseInt(row[1]) == userId){
                    for(String index : row){
                        System.out.printf("%-20s", index);
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void viewAllPasswords(){
        String filePath ="data/passwords.csv";
        BufferedReader reader = null;
        String line = "";

        // View method for a password
        try {
            reader = new BufferedReader(new FileReader(filePath));
            System.out.printf("%-20s", "Id");
            System.out.printf("%-20s", "UserId");
            System.out.printf("%-20s", "Username");
            System.out.printf("%-20s", "Password");
            System.out.printf("%-20s", "Website Name");
            System.out.printf("%-20s", "Website URL");
            System.out.printf("%-20s", "Game Name");
            System.out.printf("%-20s", "Game Developer");
            System.out.printf("%-20s", "Desktop App Name");
            System.out.printf("%-20s", "Date Created");
            System.out.printf("%-20s", "Updated Date");
            System.out.println();


            while ((line = reader.readLine()) != null){

                String[] row = line.split(",");
                for(String index : row){
                    System.out.printf("%-20s", index);
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
