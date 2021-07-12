package com.inkit.program;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Run {

    private static MyNotes getNotesFromDB(Connection conn, String username, String password) throws SQLException, IOException, ClassNotFoundException {
        MyNotes currSession = null;

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM ink_it_users WHERE username= ? AND password= ? ;");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            byte[] blobDataInByte = rs.getBytes(3);

            ByteArrayInputStream bais = new ByteArrayInputStream(blobDataInByte);
            ObjectInputStream ois = new ObjectInputStream(bais);

            currSession = (MyNotes) ois.readObject();
            ois.close();
        }

        ps.close();
        return currSession;
    }

    private static MyNotes createUserInDB(Connection conn, String username, String password) throws IOException, SQLException {
        MyNotes newUserSession = new MyNotes();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(newUserSession);
        byte[] objectDataInByte = baos.toByteArray();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO ink_it_users VALUES(?, ?, ?);");
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setBinaryStream(3, new ByteArrayInputStream(objectDataInByte));

        if(ps.executeUpdate() == 1) {
            ps.close();
            return newUserSession;
        }
        else {
            ps.close();
            System.out.println("Couldn't create new user");
            return null;
        }
    }

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
            System.out.println("Changes successfully reflected in DB...");
        }
        else {
            System.out.println("Error in updating changes to DB...");
        }
        ps.close();
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

        Scanner inp = new Scanner(System.in);

        MyNotes currSession = null;
        String username = null, password = null;


        while(currSession == null) {
            switch (TextUI.selectFromOptions("Create an account", "Login", "Exit")) {
                // New user
                case 1:
                    System.out.print("\nEnter new username > ");
                    username = inp.nextLine();
                    System.out.print("Enter new password > ");
                    password = inp.nextLine();

                    currSession = createUserInDB(conn, username, password);
                    if(currSession != null)
                        System.out.println("\nNew user created successfully\nWelcome, " + username);
                    else
                        System.out.println("\nRedirecting back to Main menu...");
                    break;

                // Existing user
                case 2:
                    System.out.print("\nEnter username > ");
                    username = inp.nextLine();
                    System.out.print("Enter password > ");
                    password = inp.nextLine();

                    currSession = getNotesFromDB(conn, username, password);
                    if(currSession != null)
                        System.out.println("\nLogin successful\nWelcome back, " + username);
                    else
                        System.out.println("\nLogin unsuccessful\nRedirecting back to Main Menu...");
                    break;

                // Closing Application
                case 3:
                    System.out.println("Closing application...");
                    System.exit(0);

                default:
                    System.out.println("Invalid input\nRedirecting back to Main Menu...");
                    break;
            }
        }

        // Menu driven system here...
        System.out.println(currSession.allNotes.get("n1"));
        /*
        1. Display all Notes
        2. Search for note by Lame
        3. Search for note by Labels
        4. Edit note -> Search(note) -> edit...
           |-> Edit To-Do
           |-> Edit Labels
           |-> Edit Content
           |-> Edit Title

        Account Options
        5. Change Password
        6. Delete Account

        7. Exit app
        */
        // In every cycle of the menu driven approach, update database....
        updateNotesToDB(conn, username, currSession);

        conn.close();
    }
}
