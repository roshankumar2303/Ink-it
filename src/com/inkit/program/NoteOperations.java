package com.inkit.program;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

/* com.inkit.program.NoteOperations - Used to perform operations on Note objects */
public class NoteOperations {
    Scanner inp = new Scanner(System.in);

    /* NoteOperations.createNote() - This method creates a new note and returns it */
    Note createNote() {
        Note newNote = new Note();
        String choice;

        System.out.print("-- TITLE: ");
        newNote.setTitle(inp.nextLine());

        System.out.println("-- CONTENT ------------------------------");
        newNote.setText(inp.nextLine());

        System.out.print("-- TO-DO (Y/n): ");
        if((inp.nextLine()).equalsIgnoreCase("Y")) {
            Hashtable<String, Boolean> toDo = new Hashtable<>();

            choice = "y";
            while (choice.equalsIgnoreCase("Y")) {
                System.out.print("---- ");
                String item = inp.nextLine();

                toDo.put(item, false);

                System.out.print("-- Add Item (Y/n): ");
                choice = inp.nextLine();
            }

            newNote.setToDo(toDo);
        }

        System.out.print("-- LABELS (Y/n): ");
        if((inp.nextLine()).equalsIgnoreCase("Y")) {
            ArrayList<String> labels = new ArrayList<>();

            choice = "y";
            while (choice.equalsIgnoreCase("Y")) {
                System.out.print("---- ");
                String label = inp.nextLine();

                labels.add(label);

                System.out.print("-- Add Another (Y/n): ");
                choice = inp.nextLine();
            }

            newNote.setLabels(labels);
        }

        System.out.println("-- NOTE CREATED ------------------------------");
        return newNote;
    }

    /* NoteOperations.modifyNote() - This method helps in modifying the note and returns it */

    // FOR TESTING
    public static void main(String[] args) {
        Note note1 = new NoteOperations().createNote();

    }
}
