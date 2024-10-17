import java.io.*;
import java.util.Scanner;

// The Admin Class that extends from the User Class
public class Admin extends User{

    // Storing Admin Data for the CSV file
    private static final String FILE_PATH = "data/admins.csv";


    // Takes the username and password from the Admin class
    public Admin(String username, String password) {
        super(username, password);
    }

    // Validates Admin credentials by reading from admins.csv
    public int validateAdmin() {
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(FILE_PATH));
    // Using commas to spilt up the line
            while ((line = reader.readLine()) != null){
                String[] row = line.split(",");
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[1].equals(getUsername()) && parts[2].equals(getPassword())) {
                    return Integer.parseInt(parts[0]);
                }
            }
            // Display to the user an error has happned when during the file
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
