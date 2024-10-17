import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DesktopAppItem extends PasswordItem{
    private String desktopAppName;

// Getter and Setter method
//  Getter method to retreive the desktop application name
// Setter method for setting desktop application name
    public String getDesktopAppName() {
        return desktopAppName;
    }

    public void setDesktopAppName(String desktopAppName) {
        this.desktopAppName = desktopAppName;
    }
// A method to add a new password for Desktop Application

    public void addPassword(){
        int id = this.getLastPasswordId() + 1;
        int userId = this.getUser().getUserId();
        String username = this.getUsername();
        String password = this.getPassword();
        String desktopAppName = this.desktopAppName;
        Date dateCreated = this.getDateCreated();

        // Formats the date in a "yyyy-MM-dd" FORMAT
        SimpleDateFormat dateCreatedFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateCreated = dateCreatedFormat.format(dateCreated);

// Creates a CSV line with the password detaisl
        String csvLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",id, userId, username, password, "", "","","",desktopAppName, formattedDateCreated, "");

        File file = new File(FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(csvLine);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }





}
