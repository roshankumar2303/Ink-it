package com.inkit.program;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Run {

    private static MyNotes getNotesFromDB(Connection conn, String username, String password) throws SQLException, IOException, ClassNotFoundException {
        MyNotes currSession = null;

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM ink_it_users WHERE username=\"" + username + "\" AND password=\"" + password + "\";");

        if(rs.next()) {
            byte[] blobDataInBytes = rs.getBytes(3);

            ByteArrayInputStream bais = new ByteArrayInputStream(blobDataInBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);

            currSession = (MyNotes) ois.readObject();
            ois.close();
        }

        stmt.close();
        return currSession;
    }

//    private static void updateNotesToDB() {
//
//    }

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

        int choice = 0;
        while(currSession == null) {
            choice = TextUI.selectFromOptions("Create an account", "Login", "Exit");
            switch (choice) {
                // New user
                case 1:
                    System.out.print("\nEnter new username > ");
                    username = inp.nextLine();
                    System.out.print("Enter new password > ");
                    password = inp.nextLine();

                    currSession = new MyNotes();
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


        // All done... updating the session
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(currSession);
        byte[] data = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);

        if(choice == 1) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ink_it_users VALUES(?, ?, ?);");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setBinaryStream(3, bais);
            System.out.println(ps.executeUpdate());
        }

        else{
            PreparedStatement ps = conn.prepareStatement("UPDATE ink_it_users SET my_notes = ? where username = ?;");
            ps.setBinaryStream(1, bais);
            ps.setString(2, username);
        }
        conn.close();
    }
}
