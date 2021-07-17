package com.inkit.program;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Run {

    /**
     * This method is to obtain the {@code MyNotes} object of the user with the given valid credentials, from the DB
     * @param conn The {@code Connection} Object
     * @param username Username of the user
     * @param password Password of the user
     * @return {@code MyNotes} object, if the credentials match, else {@code null}
     */
    private static MyNotes getNotesFromDB(Connection conn, String username, String password) throws SQLException, IOException, ClassNotFoundException {
        MyNotes currSession = null;

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM ink_it_users WHERE username= ? AND password= ? ;");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        // If the record exists, i.e., Valid username and password
        if(rs.next()) {
            byte[] blobDataInByte = rs.getBytes(3);

            // Using ByteArrayInputStream and ObjectInputStream to read Object from Blob data
            ByteArrayInputStream bais = new ByteArrayInputStream(blobDataInByte);
            ObjectInputStream ois = new ObjectInputStream(bais);
            currSession = (MyNotes) ois.readObject();

            ois.close();
        }

        ps.close();
        return currSession;
    }

    /**
     * This method is to update changes made to the {@code MyNotes} object of the current user, in the DB
     * @param conn The {@code Connection} object
     * @param username Username of the current user
     * @param currSession {@code MyNotes} object of the current user
     */
    private static void updateNotesToDB(Connection conn, String username, MyNotes currSession) throws IOException, SQLException {
        // Maybe make this method to return a boolean to inform whether database changes were successful
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(currSession);
        byte[] objectDataInByte = baos.toByteArray();

        PreparedStatement ps = conn.prepareStatement("UPDATE ink_it_users SET my_notes = ? where username = ?;");
        ps.setBinaryStream(1, new ByteArrayInputStream(objectDataInByte));
        ps.setString(2, username);

        if(ps.executeUpdate() == 1) {
            System.out.println("\nChanges successfully reflected in DB...");
        }
        else {
            System.out.println("\nError in updating changes to DB...");
        }
        ps.close();
    }

    /**
     * This method is to create a new user with the credentials provided. It creates a new record in the DB, and a new {@code MyNotes} object to operate on, and returns it
     * @param conn The {@code Connection} Object
     * @param username Username of the new user
     * @param password Password of the new user
     * @return {@code MyNotes} object, newly created for the new user, or {@code null} if user already exists
     */
    private static MyNotes createUserInDB(Connection conn, String username, String password) throws IOException, SQLException {
        MyNotes newUserSession = new MyNotes();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(newUserSession);
            byte[] objectDataInByte = baos.toByteArray();

            PreparedStatement ps = conn.prepareStatement("INSERT INTO ink_it_users VALUES(?, ?, ?);");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setBinaryStream(3, new ByteArrayInputStream(objectDataInByte));

            ps.executeUpdate();
            ps.close();
        }
        catch(SQLIntegrityConstraintViolationException userExists) {
            return null;
        }
        return newUserSession;
    }

    private static void updatePwdInDB(Connection conn, String username, String newPass) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE ink_it_users SET password = ? WHERE username = ?;");
        ps.setString(1, newPass);
        ps.setString(2, username);
        if(ps.executeUpdate() == 1)
            System.out.println("Password changed successfully...");
        else
            System.out.println("Couldn't change password...\nPlease try again later...");
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        // APP HEADER
        TextUI.drawLine(70);
        System.out.println("INK-IT");
        TextUI.drawLine(70);
        System.out.println("Welcome to Ink-it");
        System.out.println("Ink-it is where you can create, edit and organize your notes");
        TextUI.drawLine(70);

        // CONNECTING TO THE DATABASE
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ink_it", "root", "root");
        System.out.println("Connected to Database...");

        // Credentials and MyNotes Object of the particular user
        Scanner inp = new Scanner(System.in);
        MyNotes currSession = null;
        String username = null, password = null;

        // USER-SELECT MENU
        while(currSession == null) {
            switch (TextUI.selectFromOptions("Create an account", "Login", "Exit")) {
                // 1. Creating a new user
                case 1:
                    TextUI.highlight("NEW USER", 70);
                    System.out.print("Enter new username > ");
                    username = inp.nextLine();
                    System.out.print("Enter new password > ");
                    password = inp.nextLine();

                    currSession = createUserInDB(conn, username, password);
                    if(currSession != null)
                        System.out.println("\nNew user created successfully\nWelcome, " + username);
                    else
                        System.out.println("\nUser already exists\nRedirecting back to User-Select menu...");
                    break;

                // 2. Login for an existing user
                case 2:
                    TextUI.highlight("LOGIN", 70);
                    System.out.print("Enter username > ");
                    username = inp.nextLine();
                    System.out.print("Enter password > ");
                    password = inp.nextLine();

                    currSession = getNotesFromDB(conn, username, password);
                    if(currSession != null)
                        System.out.println("\nLogin successful\nWelcome back, " + username);
                    else
                        System.out.println("\nInvalid Credentials\nRedirecting back to User-Select Menu...");
                    break;

                // 3. Exit application
                case 3:
                    System.out.println("Exiting application...");
                    System.exit(0);

                // Invalid input
                default:
                    System.out.println("Invalid input\nRedirecting back to User-Select Menu...");
                    break;
            }
        }

        // Menu driven system below...
        // System.out.println(currSession.allNotes.get("n1")); // Temporary...
        /*
        1. Create new note
        2. Display all Titles
        3. Search
        4. Edit note -> Search(note) -> edit...
           |-> Edit To-Do
           |-> Edit Labels
           |-> Edit Content
           |-> Edit Title

        Account Options
        5. Change Password
        6. Delete Account

        7. Log Out maybe??????????
           |-> Which means the control should move back to the Main Menu....
        8. Exit
        */

        String[] options = {
                "Create Notes",
                "Display all Titles",
                "Search Note",
                "Edit Note",
                "Delete Note",
                "Show History",
                "Clear History",
                "Change password",
                "Delete Account and Exit",
                "Exit Application"
        };
        String query;

        while(true) {
            TextUI.highlight("MAIN MENU", 70);
            switch (TextUI.selectFromOptions(options)) {
                // 1. Creating a New Note
                case 1:
                    TextUI.highlight("NEW NOTE", 70);
                    currSession.createNote();
                    updateNotesToDB(conn, username, currSession);
                    break;

                // 2. Displaying all the titles
                case 2:
                    TextUI.highlight("ALL NOTES", 70);
                    if(currSession.getTitles().isEmpty())
                        System.out.println("No Notes found to display");
                    else {
                        for (String title : currSession.getTitles())
                            System.out.println(title);
                    }
                    break;

                // 3. Search for Note
                case 3:
                    TextUI.highlight("SEARCH", 70);
                    Note temp = currSession.search();
                    if(temp != null)
                        System.out.println(temp);
                    break;

                // Edit note
                case 4:
                    TextUI.highlight("EDIT NOTES", 70);
                    System.out.print("Enter the title of the note which is to be updated > ");
                    query = inp.nextLine();
                    currSession.update(query);
                    updateNotesToDB(conn, username, currSession);
                    break;

                // Delete note
                case 5:
                    TextUI.highlight("DELETE NOTE", 70);
                    System.out.print("Enter the title of the note to be deleted > ");
                    query = inp.nextLine();
                    currSession.deleteNote(query);
                    updateNotesToDB(conn, username, currSession);
                    break;

                case 6:
                    TextUI.highlight("HISTORY", 70);
                    currSession.displayHistory();
                    break;

                case 7:
                    TextUI.highlight("CLEAR HISTORY", 70);
                    currSession.clearHistory();
                    break;

                case 8:
                    TextUI.highlight("CHANGE PASSWORD", 70);
                    // Read old password again
                    System.out.print("\nTo proceed changing the password\nPlease enter your old password > ");
                    String oldPass = inp.nextLine();

                    // Enters valid password: Allow changing password
                    if(oldPass.equals(password)) {
                        System.out.print("Enter new password > ");
                        String newPass = inp.nextLine();
                        if(!newPass.equals(oldPass)) {
                            password = newPass;
                            updatePwdInDB(conn, username, newPass);
                        }
                        else
                            System.out.println("New password same as old password...\nPlease change...");
                    }
                    // Enters invalid password: Deny changing password
                    else {
                        System.out.println("Password doesn't match...");
                    }
                    break;

                case 9:
                    TextUI.highlight("DELETE ACCOUNT", 70);
                    if(!TextUI.yesOrNo("Are you sure you want to delete the account ? (irreversible)"))
                        break;
                    System.out.print("Enter password to continue > ");
                    if((inp.nextLine()).equals(password)){
                        PreparedStatement ps = conn.prepareStatement("DELETE from ink_it_users WHERE username = ?;");
                        ps.setString(1, username);
                        if(ps.executeUpdate() == 1) {
                            conn.close();
                            System.out.println("Account Successfully Deleted...\nExiting application...");
                            System.exit(0);
                        }
                        else
                            System.out.println("Deletion Unsuccessful...\nPlease try again later...");
                    }
                    break;

                case 10:
                    updateNotesToDB(conn, username, currSession);
                    conn.close();
                    System.out.println("\nExiting application...");
                    System.exit(0);

                default:
                    System.out.println("Invalid Input...");
                    break;
            }
        }
    }
}
