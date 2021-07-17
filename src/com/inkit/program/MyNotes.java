package com.inkit.program;

import java.io.Serializable;
import java.util.*;

/**
 * {@code NoteOperations} class is used to perform operations on {@code Note} objects
 */
public class MyNotes implements Serializable {
    Metadata md;
    HashMap<String, Note> allNotes;
    ArrayList<String> history;

    public static Scanner inp = new Scanner(System.in);

    MyNotes(){
        allNotes = new HashMap<>();
        md = new Metadata();
        history = new ArrayList<>();
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

    public void updateTodo(Note n1) {
        LinkedHashMap<String, Boolean> toDo = n1.getToDo();
        System.out.println(TextUI.formatTodoList(toDo));

        if(toDo != null){
            boolean f = true;
            while (f) {
                System.out.println("Which task should be marked as done?");
                String s = inp.nextLine();
                toDo.put(s, true);
                f = TextUI.yesOrNo("Do you want to continue?");
            }
            n1.setToDo(toDo);
            allNotes.replace(n1.getTitle(), n1);
        }
        else {
            if(TextUI.yesOrNo("| ADD TO-DO LIST?")) {
                toDo = new LinkedHashMap<>();
                do {
                    System.out.print("| * ");
                    String item = inp.nextLine();

                    toDo.put(item, false);
                } while (TextUI.yesOrNo("| |-- Add Item"));

                n1.setToDo(toDo);
                allNotes.replace(n1.getTitle(), n1);
            }
        }
    }

    public void updateContent(Note n1) {
        System.out.println("Current content in the note...");
        System.out.println(n1.getContent());

        String newContent = null;
        switch(TextUI.selectFromOptions("Rewrite Content", "Add Content")) {
            case 1:
                    System.out.println("Enter new content...");
                    newContent = inp.nextLine();
                    break;

            case 2:
                    System.out.println("Enter the content you want to add...");
                    newContent = inp.nextLine();
                    newContent = n1.getContent() + newContent;
                    break;

        }
        n1.setContent(newContent);
        allNotes.replace(n1.getTitle(), n1);
    }

    public void update(String noteTitle){
        Note n1 = allNotes.get(noteTitle);
        if(n1 == null) {
            if(TextUI.yesOrNo("No exact matches found. Would you like to search for it?")) {
                n1 = search();
                if(n1 == null)
                    return;
            }
            else {
                System.out.println("Redirecting back to main menu...");
                return;
            }
        }
        switch(TextUI.selectFromOptions("Edit Content", "Edit To-Do List")){
            case 1:
                    updateContent(n1);
                    break;
            case 2:
                    updateTodo(n1);
                    break;
        }
    }

    //
    // ============================ SEARCHING ============================ //
    //

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
                    history.add(title);
                    if(title.equalsIgnoreCase("-1")) {
                        System.out.println("Search Stopped Successfully");
                        return null;
                    }
                    result = searchByTitle(title);
                }
                break;

            case 2:
                while(result == null){
                    System.out.println("Enter the label of your note (Press -1 to stop searching): ");
                    String label = inp.nextLine();
                    if(label.equalsIgnoreCase("-1")) {
                        System.out.println("Search Stopped Successfully");
                        return null;
                    }
                    ArrayList<String> titles = searchByLabel(label);
                    if(titles == null)
                        continue;
                    int i = 1;
                    for(String title: titles)
                        System.out.println((i++) + ". " + title);
                    System.out.println("Enter your choice of title : ");
                    String title = inp.nextLine();
                    history.add(title);
                    result = getNotes(title);
                }
                break;

            default:
                System.out.println("Invalid Input...");
        }
        return result;
    }

    public void deleteNote(String noteTitle){
        if(!allNotes.containsKey(noteTitle)){
            System.out.println("No Matches Found");
            return;
        }
        allNotes.remove(noteTitle);
        System.out.println("Deletion Successful");
    }

    public void displayHistory(){
        int i = 1;
        for(String title : history)
            System.out.println((i++) + ". " + title);
    }

    public void clearHistory(){
        switch(TextUI.selectFromOptions("Choose and Clear", "Clear All")){
            case 1:
                displayHistory();
                System.out.print("Select the entry to be cleared > ");
                int choice = Integer.parseInt(inp.nextLine());
                history.remove(choice - 1);
                System.out.println("Your entry has been cleared from history");
                break;
            case 2:
                history.clear();
                System.out.println("Entire history cleared successfully");
        }
    }
}
