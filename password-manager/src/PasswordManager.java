import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
// The PasswordManager Class
public class PasswordManager {

    // The user that is currently logged in
    private User loggedInUser;

    public void runUi(){
        login();
    }

    private void login(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Are you a admin or user? Type [1] User, [2] Admin : ");
        int type = scanner.nextInt();
        scanner.nextLine();

        if(type != 1 && type != 2){
            System.out.println("Wrong input, try again");
            login();
            return;
        }

        boolean isValidCredentials = false;
        do {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
// Checks to user type and validate the credentials
            if(type == 1){
                User user = new User(username, password);
                var userId = user.validateUser();
                if (userId > 0) {
                    loggedInUser = user;
                    loggedInUser.setUserId(userId);
                    isValidCredentials = true;
                } else {
                    System.out.println("Entered Username or Password is not correct. Please try again.");
                }
            }
            else {
                Admin admin = new Admin(username, password);
                var userId = admin.validateAdmin();
                if (userId > 0) {
                    loggedInUser = admin;
                    loggedInUser.setUserId(userId);
                    isValidCredentials = true;
                } else {
                    System.out.println("Entered Username or Password is not correct. Please try again.");
                }
            }
        } while (!isValidCredentials);
// Proceed main menu
        mainMenu();
        scanner.close();
    }
// Options the user can select from the main menu
    private void mainMenu(){
        Scanner scanner = new Scanner(System.in);
        if (loggedInUser instanceof Admin) {
            adminMenu();
        } else {
            int choiceSelection;
            System.out.println("Welcome, please select your option: ");
            System.out.println("---------------------\n");
            System.out.println("1- Add new Passwords");
            System.out.println("2- Edit Passwords");
            System.out.println("3- View Passwords");
            System.out.println("4- Sort Passwords");
            System.out.println("5- Delete Passwords");
            System.out.println("6- Search Passwords");
            System.out.println("7- Exit");

// A switch used for the user actions
            System.out.println("You have selected the option: ");
            choiceSelection = scanner.nextInt();

            switch (choiceSelection) {
                case 1:
                    addNewPassword();
                    break;
                case 2:
                    editPasswords();
                    break;
                case 3:
                    viewPasswords(loggedInUser.getUserId());
                    break;
                case 4:
                    sortPasswords();
                    break;
                case 5:
                    deletePasswords();
                    break;
                case 6:
                    searchPasswords();
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }

        scanner.close();
    }

    // Method to be added to new password
    // Gets the user to enter basic information relating new password
    private void addNewPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add New Password");
        System.out.println("---------------------\n");

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        System.out.print("What type of password you want to save, [1] Website, [2] Game, [3] Desktop Application : ");
        int type = scanner.nextInt();
        scanner.nextLine();

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
// User enters a website password
        switch (type) {
            case 1:
                System.out.print("Enter Website Name: ");
                String websiteName = scanner.nextLine();

                System.out.print("Enter Website Url: ");
                String websiteUrl = scanner.nextLine();

                WebsiteItem website = new WebsiteItem();
                website.setUsername(username);
                website.setPassword(password);
                website.setWebsiteName(websiteName);
                website.setWebsiteUrl(websiteUrl);
                website.setDateCreated(currentDate);
                website.setUser(loggedInUser);
                website.addPassword();

                System.out.println("Password information written to file.");
                waitForKeyPress();
                mainMenu();
                break;
            case 2:
                // Enter game password
                System.out.print("Enter Game Name: ");
                String gameName = scanner.nextLine();

                System.out.print("Enter Game Developer: ");
                String gameDeveloper = scanner.nextLine();

                GameItems game = new GameItems();
                game.setUsername(username);
                game.setPassword(password);
                game.setGameName(gameName);
                game.setGameDeveloper(gameDeveloper);
                game.setDateCreated(currentDate);
                game.setUser(loggedInUser);
                game.addPassword();

                System.out.println("Password information written to file.");
                waitForKeyPress();
                mainMenu();
                break;
            case 3:
                // Desktop Application password
                System.out.print("Enter Desktop Application Name: ");
                String desktopAppName = scanner.nextLine();

                DesktopAppItem desktopApp = new DesktopAppItem();
                desktopApp.setUsername(username);
                desktopApp.setPassword(password);
                desktopApp.setDesktopAppName(desktopAppName);
                desktopApp.setDateCreated(currentDate);
                desktopApp.setUser(loggedInUser);
                desktopApp.addPassword();

                System.out.println("Password information written to file.");
                waitForKeyPress();
                mainMenu();
                break;
            default:
                System.out.println("Wrong input, try again");
                addNewPassword();
                break;
        }
        scanner.close();

    }

    // Edit the existing password
    private void editPasswords() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Password ID : ");
        int passwordId = scanner.nextInt();
        scanner.nextLine();

        PasswordItem passwordItem = new PasswordItem();
        if(!passwordItem.isPasswordIdValid(passwordId)){
            System.out.print("Password Id is not correct.");
        }
        else{
            System.out.print("Enter new username: ");
            String username = scanner.nextLine();

            System.out.print("Enter new password: ");
            String password = scanner.nextLine();

            passwordItem.editPassword(passwordId, username, password);
            System.out.println("Password information written to file.");
        }

        waitForKeyPress();
        mainMenu();
        scanner.close();
    }

    // Search the existing password
    private void searchPasswords() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Website, Game or Desktop Application Name : ");
        String search = scanner.nextLine();

        PasswordItem passwordItem = new PasswordItem();
        passwordItem.searchPassword(search);

        waitForKeyPress();
        mainMenu();
        scanner.close();
    }

    // Delete the existing password
    private void deletePasswords() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Password ID : ");
        int passwordId = scanner.nextInt();
        scanner.nextLine();

        PasswordItem passwordItem = new PasswordItem();
        if(!passwordItem.isPasswordIdValid(passwordId)){
            System.out.print("Password Id is not correct.");
        }
        else{
            passwordItem.deletePassword(passwordId);
            System.out.println("Password information written to file.");
        }

        waitForKeyPress();
        mainMenu();
        scanner.close();
    }
    // Sort the existing password
    private void sortPasswords() {
        System.out.println("Unimplemented method 'sortPasswords'");
        waitForKeyPress();
        mainMenu();
    }
    // View the existing password
    private void viewPasswords(int userId) {
        PasswordItem passwordItem = new PasswordItem();
        passwordItem.viewPasswords(userId);
        waitForKeyPress();
        mainMenu();
    }

    private void viewAllPasswords() {
        PasswordItem passwordItem = new PasswordItem();
        passwordItem.viewAllPasswords();
        waitForKeyPress();
        mainMenu();
    }


    private void adminMenu(){
        Scanner scanner = new Scanner(System.in);

        // Desktop application password
        int choiceSelection;
        System.out.println("Welcome, please select your option: ");
        System.out.println("---------------------\n");
        System.out.println("1- View ALl Passwords");
        System.out.println("2- Edit Any Passwords");
        System.out.println("3- Delete Any Passwords");
        System.out.println("4- Exit");

        System.out.println("You have selected the option: ");
        choiceSelection = scanner.nextInt();

        switch (choiceSelection) {
            case 1:
                viewAllPasswords();
                break;
            case 2:
                editPasswords();
                break;
            case 3:
                deletePasswords();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                break;
        }

        scanner.close();
    }
// Method is used to edit existing passwords
    private void waitForKeyPress() {
        System.out.println("Press Enter to continue...");

        try {
            System.in.read(); // Read a character from the console
        } catch (Exception e) {
            System.out.println("Error reading input: " + e.getMessage());
        }
    }

}
