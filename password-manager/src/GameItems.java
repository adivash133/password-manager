import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
// The GameItems class, extends from the PasswordItem class
// Fields are able to store the game name and the game developer
public class GameItems extends PasswordItem{
    private String gameName;
    private String gameDeveloper;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameDeveloper() {
        return gameDeveloper;
    }

    public void setGameDeveloper(String gameDeveloper) {
        this.gameDeveloper = gameDeveloper;
    }

    // Method has been used to add a new password entry for a game
    public void addPassword() {
        int id = this.getLastPasswordId() + 1;
        int userId = this.getUser().getUserId();
        String username = this.getUsername();
        String password = this.getPassword();
        String gameName = this.gameName;
        String gameDeveloper = this.gameDeveloper;
        Date dateCreated = this.getDateCreated();

        SimpleDateFormat dateCreatedFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateCreated = dateCreatedFormat.format(dateCreated);
// Creates a CSV line with password details, like game - related information
        String csvLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",id, userId, username, password, "", "",gameName,gameDeveloper,"", formattedDateCreated, "");

// Creates a File for the password storage and adds new line with each entry
        File file = new File(FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(csvLine);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
