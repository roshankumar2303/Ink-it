package com.inkit.program;

import java.util.Hashtable;
import java.util.Scanner;

/**
 * This class is used to perform operations on Note objects
 */
public class NoteOperations {

    /**
     * This static method creates a new note and returns it
     * @return A note object
     */
    public static Note createNote() {
        Scanner inp = new Scanner(System.in);

        Note newNote = new Note();
        String choice;

        // [INCOMPLETE] GENERATE uniqueID FOR THE NEW NOTE

        // Reading Title of the note
        System.out.print("-- TITLE: ");
        newNote.setTitle(inp.nextLine());

        // Reading Content of the note
        System.out.println("-- CONTENT ------------------------------");
        newNote.setText(inp.nextLine());

        // Reading To-Do list of the note
        System.out.print("-- TO-DO (Y/n): ");
        if((inp.nextLine()).equalsIgnoreCase("Y")) {
            Hashtable<String, Boolean> toDo = new Hashtable<>();

            choice = "y";
            while (choice.equalsIgnoreCase("Y")) {
                System.out.print("* ");
                String item = inp.nextLine();

                toDo.put(item, false);

                System.out.print("|- Add Item (Y/n): ");
                choice = inp.nextLine();
            }

            newNote.setToDo(toDo);
        }

        // Labels of the note
        System.out.print("-- LABELS (Y/n): ");
        if((inp.nextLine()).equalsIgnoreCase("Y")) {
            String newNoteLabel = null;

            System.out.print("A. Create new Label\nB. Choose from existing ones\nYour choice: ");
            if((inp.nextLine()).equalsIgnoreCase("A")) {
                // Read new label from the user
                System.out.print("Enter the label: ");
                newNoteLabel = inp.nextLine();

                // Update allLabels with the new label
                Labels.updateLabels(newNoteLabel, newNote.getTitle());
            }
            else {
                // Choose label from the existing ones
                newNoteLabel = Labels.chooseLabels(newNote.getTitle());
            }

            newNote.setLabels(newNoteLabel);
        }

        System.out.println("-- NOTE CREATED ------------------------------");
        return newNote;
    }

    /*
     * [INCOMPLETE] This method helps in modifying the given note
     */
}
