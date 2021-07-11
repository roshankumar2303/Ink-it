package com.inkit.program;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * {@code Metadata} class provides methods to store and operate on note labels and titles
 */
public class Metadata implements Serializable {

    static Scanner inp = new Scanner(System.in);

    LinkedHashMap<String, ArrayList<String>> allLabels;

    Metadata(){
        allLabels = new LinkedHashMap<>();
    }


    /*-------------------- METHODS ON "LABELS" -------------------- */
    /**
     * This static method is used to create a new Label
     * @param noteTitle Title of the note
     * @return The new Label
     */
    public String createNewLabel(String noteTitle){
        System.out.print("| Enter new Label:");
        String newLabel = inp.nextLine();

        ArrayList<String> titles = new ArrayList<String>() {{
                    add(noteTitle);
        }};

        // Updating allLabels
        allLabels.put(newLabel, titles);
        return newLabel;
    }

    /**
     * This static method helps in choosing labels for a new note from pre-existing labels stored in a file as a {@code Hashtable} object.
     * @param noteTitle Title of the new note which is being created
     * @return label - String containing the chosen Label
     */
    public String chooseLabels(String noteTitle) {

        // Printing all the labels for the user to choose
        int i = 1;
        System.out.println("-- CHOOSE LABELS FROM THE BELOW ONES ----------");
        for(String label: allLabels.keySet()) {
            System.out.println((i++) + " --> " + label);
        }

        // Choosing the label
        String label;
        System.out.print("Enter your choice of label : ");
        label = inp.nextLine();

        // Update Titles array corresponding to the table
        ArrayList<String> titles = allLabels.get(label);
        titles.add(noteTitle);
        allLabels.put(label, titles);

        // Returning Label
        return label;
    }

    public ArrayList<String> getLabelTitles(String labelName){
        return allLabels.get(labelName);
    }

    public ArrayList<String> getLabels(){
        return new ArrayList<String>() {{
            addAll(allLabels.keySet());
        }};
    }
}
