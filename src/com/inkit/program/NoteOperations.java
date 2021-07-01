package com.inkit.program;

import java.util.Hashtable;
import java.util.Scanner;

/**
 * {@code NoteOperations} class is used to perform operations on {@code Note} objects
 */
public class NoteOperations {

    /**
     * This static method creates a new {@code Note} and returns it
     * @return A {@code Note} object
     */
    public static Note createNote() {
        Scanner inp = new Scanner(System.in);

        Note newNote = new Note();
        String choice;

        // Reading Title of the note
        System.out.print(ConsoleUI.line(70));
        System.out.print("| TITLE: ");
        newNote.setTitle(inp.nextLine());

        // Reading Content of the note
        System.out.print("| CONTENT " + ConsoleUI.line(60) + "| ");
        newNote.setContent(inp.nextLine());

        // Reading To-Do list of the note
        System.out.print("| TO-DO (Y/n): ");
        if((inp.nextLine()).equalsIgnoreCase("Y")) {
            Hashtable<String, Boolean> toDo = new Hashtable<>();

            choice = "y";
            while (choice.equalsIgnoreCase("Y")) {
                System.out.print("| * ");
                String item = inp.nextLine();

                toDo.put(item, false);

                System.out.print("| |-- Add Item (Y/n): ");
                choice = inp.nextLine();
            }

            newNote.setToDo(toDo);
        }

        // Labels of the note
        System.out.print("| LABELS (Y/n): ");
        if((inp.nextLine()).equalsIgnoreCase("Y")) {
            String newNoteLabel;

            System.out.print("| A. Create new Label\n| B. Choose from existing ones\n| Your choice: ");
            if((inp.nextLine()).equalsIgnoreCase("A")) {
                // Read new label from the user
                System.out.print("| Enter the label: ");
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

        System.out.println("== NOTE CREATED " + ConsoleUI.line(54));
        return newNote;
    }

    /*
     * [INCOMPLETE] The next method helps in modifying the given note
     */
}
