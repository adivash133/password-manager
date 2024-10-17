import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

// The User Class
public class User {
    private int userId;
    private String username;
    private String password;

    // File path that storing user data
    private static final String FILE_PATH = "data/users.csv";

    // Getter method for username and password
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Constructor to initialize username and password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
// Getter and setter method are used for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Reads each line from the CSV file and split the parts by comma
    // Returns UserID if the credentials match
    public int validateUser() {
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(FILE_PATH));

            while ((line = reader.readLine()) != null){
                String[] row = line.split(",");
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[1].equals(getUsername()) && parts[2].equals(getPassword())) {
                    userId = Integer.parseInt(parts[0]);
                    return userId;
                }
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
        return 0;
    }




}
