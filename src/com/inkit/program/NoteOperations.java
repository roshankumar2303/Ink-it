package com.inkit.program;

import java.util.LinkedHashMap;
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

        // Reading Title of the note
        TextUI.drawLine(70);
        System.out.print("| TITLE: ");
        newNote.setTitle(inp.nextLine());

        // Reading Content of the note
        System.out.print("| CONTENT " + TextUI.getLine(60) + "| ");
        newNote.setContent(inp.nextLine());

        // Reading To-Do list of the note
        if(TextUI.yesOrNo("| TO-DO")) {
            LinkedHashMap<String, Boolean> toDo = new LinkedHashMap<>();
            do {
                System.out.print("| * ");
                String item = inp.nextLine();

                toDo.put(item, false);
            } while (TextUI.yesOrNo("| |-- Add Item"));

            newNote.setToDo(toDo);
        }

        // Labels of the note
        if(TextUI.yesOrNo("| CREATE LABEL?")) {
            String newNoteLabel;

            if(!Labels.allLabels.isEmpty()) {
                System.out.print("| A. Create new Label\n| B. Choose from existing ones\n| Your choice: ");
                if ((inp.nextLine()).equalsIgnoreCase("A")) {
                    newNoteLabel = Labels.createLabel(newNote.getTitle());
                } else {
                    // Choose label from the existing ones
                    newNoteLabel = Labels.chooseLabels(newNote.getTitle());
                }
                newNote.setLabel(newNoteLabel);
            }

        }

        System.out.println("== NOTE CREATED " + TextUI.getLine(54));
        return newNote;
    }

    /*
     * [INCOMPLETE] The next method helps in modifying the given note
     */
}
