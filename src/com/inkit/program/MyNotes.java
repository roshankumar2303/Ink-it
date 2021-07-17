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
        System.out.print("TITLE > ");
        newNote.setTitle(inp.nextLine());
        TextUI.drawLine(70);

        // Reading Content of the note
        System.out.print("CONTENT\n");
        newNote.setContent(inp.nextLine());
        TextUI.drawLine(70);

        // Reading To-Do list of the note
        if(TextUI.yesOrNo("ADD TO-DO LIST?")) {
            LinkedHashMap<String, Boolean> toDo = new LinkedHashMap<>();
            do {
                System.out.print("* ");
                String item = inp.nextLine();

                toDo.put(item, false);
            }
            while (TextUI.yesOrNo("|-- Add Another Item"));

            newNote.setToDo(toDo);
        }
        TextUI.drawLine(70);

        // Labels of the note
        if(TextUI.yesOrNo("CREATE LABEL?")) {
            String newNoteLabel;

            if(!md.allLabels.isEmpty()) {
                if(TextUI.selectFromOptions("Create new Label", "Choose from existing ones") == 1)
                    newNoteLabel = md.createNewLabel(newNote.getTitle());
                else
                    newNoteLabel = md.chooseLabels(newNote.getTitle());
            }
            else {
                System.out.println("No labels found...");
                newNoteLabel = md.createNewLabel(newNote.getTitle());
            }
            newNote.setLabel(newNoteLabel);
        }

        TextUI.highlight("NOTE CREATED", 70);
        allNotes.put(newNote.getTitle(), newNote);
    }

    public void updateTodo(Note n1) {
        LinkedHashMap<String, Boolean> toDo = n1.getToDo();
        System.out.println(TextUI.formatTodoList(toDo));

        if(toDo != null){
            boolean f = true;
            switch(TextUI.selectFromOptions("Mark task as done", "Add new task")) {
                case 1:
                    while (f) {
                        System.out.println(TextUI.formatTodoList(toDo));
                        System.out.println("Which task should be marked as done?");
                        String s = inp.nextLine();
                        if(toDo.containsKey(s))
                            toDo.put(s, true);
                        else
                            System.out.println("ToDo list doesn't contain task \"" + s + "\"");
                        f = TextUI.yesOrNo("Do you want to continue?");
                    }
                    break;

                case 2:
                    while (f) {
                        System.out.print("Enter the task >");
                        String s = inp.nextLine();
                        toDo.put(s, false);
                        f = TextUI.yesOrNo("Do you want to continue?");
                    }
                    break;

                default:
                    System.out.println("Invalid input...");
                    break;
            }
            System.out.println(TextUI.formatTodoList(toDo));
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
                System.out.println(TextUI.formatTodoList(toDo));
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
            else
                return;
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
        System.out.println("Couldn't find exact matches\nFinding similar matches");
        for(String title: list) {
            if(title.toLowerCase().contains(query.toLowerCase())) {
                System.out.println("* " + title);
                noneFound = false;
            }
        }
        if(noneFound)
            System.out.println("No Matches Found. Try something else");
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
            return md.getTitlesUnderLabel(query);
        searchSuggestions(query, labels);
        return null;
    }

    /**
     * Search for a note, either by title, or by label.
     * @return Returns the {@code Note} object if required note is found, else {@code null}
     */
    public Note search() {
        Note result = null;
        switch(TextUI.selectFromOptions("Search by Title", "Search by label")) {
            // 1. Search by Title
            case 1:
                while(result == null) {
                    // Read search term (Will be compared with titles)
                    System.out.print("Enter search term (-1 to stop searching) > ");
                    String title = inp.nextLine();

                    // Add search term to history
                    history.add(title);

                    // Return null if user chose to exit search
                    if(title.equalsIgnoreCase("-1")) {
                        System.out.println("Search Stopped...");
                        return null;
                    }

                    // Performing Search by Title
                    result = searchByTitle(title);
                }
                break;

            // 2. Search by Label
            case 2:
                while(result == null) {
                    // Read search term (Will be compared with labels)
                    System.out.print("Enter search term (-1 to stop searching) > ");
                    String label = inp.nextLine();

                    // Return null if user chose to exit search
                    if(label.equalsIgnoreCase("-1")) {
                        System.out.println("Search Stopped...");
                        return null;
                    }

                    // Performing search by label. For more info, see method description
                    ArrayList<String> titles = searchByLabel(label);

                    // No perfect match of labels; Continue
                    if(titles == null)
                        continue;

                    // Selecting a note from titles of all notes under that label
                    String noteTitle = titles.get(TextUI.selectFromOptions(titles.toArray(new String[0])));
                    history.add(noteTitle);
                    result = getNotes(noteTitle);
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
