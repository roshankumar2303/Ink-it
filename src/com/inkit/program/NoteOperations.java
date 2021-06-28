package com.inkit.program;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * This class is used to perform operations on Note objects
 */
public class NoteOperations {
    Scanner inp = new Scanner(System.in);

    /**
     * This method creates a new note and returns it
     * [AN IDEA] Maybe make these methods "static" as well ?? -- Roshan
     * @return A note object
     */
    Note createNote() {
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
            ArrayList<String> newNoteLabels = new ArrayList<>();

            // [INCOMPLETE] Create and add new labels


            // Choose labels from the existing ones
            newNoteLabels.addAll(
                    Labels.chooseLabels(newNote.getUniqueID())
            );

            newNote.setLabels(newNoteLabels);
        }

        System.out.println("-- NOTE CREATED ------------------------------");
        return newNote;
    }

    /*
     * [INCOMPLETE] This method helps in modifying the given note
     */
}
