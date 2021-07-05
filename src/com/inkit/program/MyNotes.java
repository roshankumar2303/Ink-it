package com.inkit.program;

import java.io.Serializable;
import java.util.*;

/**
 * {@code NoteOperations} class is used to perform operations on {@code Note} objects
 */
public class MyNotes implements Serializable {
    Metadata md;
    HashMap<String, Note> allNotes;

    MyNotes(){
        allNotes = new HashMap<String, Note>();
        md = new Metadata();
    }

    public Note getNotes(String title){
        return allNotes.get(title);
    }

    public ArrayList<String> getTitles(){
        return new ArrayList<String>() {{
            addAll(allNotes.keySet());
        }};
    }

    public void createNote() {
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
        if(TextUI.yesOrNo("| ADD TO-DO LIST?")) {
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

            if(!Metadata.allLabels.isEmpty()) {
                System.out.print("| A. Create new Label\n| B. Choose from existing ones\n| Your choice: ");
                if ((inp.nextLine()).equalsIgnoreCase("A")) {
                    newNoteLabel = Metadata.createNewLabel(newNote.getTitle());
                } else {
                    // Choose label from the existing ones
                    newNoteLabel = Metadata.chooseLabels(newNote.getTitle());
                }
                newNote.setLabel(newNoteLabel);
            }
            else {
                System.out.println("| No labels found...");
                newNoteLabel = Metadata.createNewLabel(newNote.getTitle());
                newNote.setLabel(newNoteLabel);
            }

        }

        System.out.println("== NOTE CREATED " + TextUI.getLine(54));
        return newNote;
    }

    public void updateTodo(String noteTitle){
        Note n1 = allNotes.get(noteTitle);
        if(n1 == null) {
            System.out.println("No Matches Found");
            return;
        }
        LinkedHashMap<String, Boolean> toDo = n1.getToDo();
        System.out.println(TextUI.formatTodoList(toDo));
        Scanner inp = new Scanner(System.in);
        boolean f = true;
        while(f){
            System.out.println("Which task should be marked as done?");
            String s = inp.nextLine();
            toDo.put(s, true);
            f = TextUI.yesOrNo("Do you want to continue?");
        }
        n1.setToDo(toDo);
        allNotes.replace(noteTitle, n1);
    }
}
