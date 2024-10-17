import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

// WebsiteItem class extending PasswordItem
// Fields for website name and URL
public class WebsiteItem extends PasswordItem{
    private String websiteName;
    private String websiteUrl;

    public String getWebsiteName() {
        return websiteName;
    }

    // Getter method for website name
    // Setter method for website name
    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    // Getter method for website URL
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    // Setter method for website URL
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }


    // Method to add a new password for a website
    // Retrieve user details
    public void addPassword(){
        int id = this.getLastPasswordId() + 1;

        // Retrieve user details
        int userId = this.getUser().getUserId();
        String username = this.getUsername();
        String password = this.getPassword();

        // Retrieve website details
        String websiteName = this.websiteName;
        String websiteUrl = this.websiteUrl;

        //  Retrieve date created
        Date dateCreated = this.getDateCreated();

        // Format date created to a string

        SimpleDateFormat dateCreatedFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateCreated = dateCreatedFormat.format(dateCreated);


        String csvLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",id, userId, username, password, websiteName, websiteUrl,"","","", formattedDateCreated, "");

        File file = new File(FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(csvLine);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
