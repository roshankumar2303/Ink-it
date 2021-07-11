package com.inkit.program;

import java.io.Serializable;
import java.util.*;

/**
 * {@code NoteOperations} class is used to perform operations on {@code Note} objects
 */
public class MyNotes implements Serializable {
    Metadata md;
    HashMap<String, Note> allNotes;

    public static Scanner inp = new Scanner(System.in);

    MyNotes(){
        allNotes = new HashMap<>();
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

            if(!md.allLabels.isEmpty()) {
                System.out.print("| A. Create new Label\n| B. Choose from existing ones\n| Your choice: ");
                if ((inp.nextLine()).equalsIgnoreCase("A")) {
                    newNoteLabel = md.createNewLabel(newNote.getTitle());
                } else {
                    // Choose label from the existing ones
                    newNoteLabel = md.chooseLabels(newNote.getTitle());
                }
            }
            else {
                System.out.println("| No labels found...");
                newNoteLabel = md.createNewLabel(newNote.getTitle());
            }
            newNote.setLabel(newNoteLabel);
        }

        System.out.println("== NOTE CREATED " + TextUI.getLine(54));
        allNotes.put(newNote.getTitle(), newNote);
    }

    public void updateTodo(String noteTitle){
        Note n1 = allNotes.get(noteTitle);
        if(n1 == null) {
            System.out.println("No Matches Found");
            return;
        }
        LinkedHashMap<String, Boolean> toDo = n1.getToDo();
        System.out.println(TextUI.formatTodoList(toDo));

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

    public void searchSuggestions(String query, ArrayList<String> list) {
        boolean noneFound = true;
        System.out.println("Couldn't find exact matches. Suggestions below...");
        for(String title: list) {
            if(title.contains(query)) {
                System.out.println("* " + title);
                noneFound = false;
            }
        }
        if(noneFound)
            System.out.println("No Matches Found");
    }

    public Note searchByTitle(String query) {
        ArrayList<String> titles = getTitles();
        if(titles.contains(query))
            return getNotes(query);
        searchSuggestions(query, titles);
        return null;
    }

    public ArrayList<String> searchByLabel(String query) {
        ArrayList<String> labels = md.getLabels();
        if(labels.contains(query))
            return md.getLabelTitles(query);
        searchSuggestions(query, labels);
        return null;
    }

    public Note search() {
        Note result = null;
        switch(TextUI.selectFromOptions("Search by Title", "Search by label")) {
            case 1:
                while(result == null) {
                    System.out.println("Enter the title of your note (Press -1 to stop searching): ");
                    String title = inp.nextLine();
                    if(title.equalsIgnoreCase("-1"))
                        return null;
                    result = searchByTitle(title);
                }
                break;

            case 2:
                while(result == null){
                    System.out.println("Enter the label of your note (Press -1 to stop searching): ");
                    String label = inp.nextLine();
                    if(label.equalsIgnoreCase("-1"))
                        return null;
                    ArrayList<String> titles = searchByLabel(label);
                    if(titles == null)
                        continue;
                    int i = 1;
                    for(String title: titles)
                        System.out.println((i++) + ". " + title);
                    System.out.println("Enter your choice of title : ");
                    String title = inp.nextLine();
                    result = getNotes(title);
                }
                break;

            default:
                System.out.println("Invalid Input...");
        }
        return result;
    }
}
