package com.inkit.program;

import java.util.ArrayList;
import java.util.Hashtable;

public class Labels {

    /**
     * <p>{@code key: String} - Label</p>
     * <p>{@code value: ArrayList of Strings} - Titles of all the notes under that label</p>
     */
    static LinkedHashMap<String, ArrayList<String>> allLabels;

    static {
        // [INCOMPLETE] This static block runs for the first time when "Labels" class has been invoked through a static method
        // [INCOMPLETE] Retrieve all the labels from the file and store it in "allLabels"

        /* TEMPORARY */
        allLabels = new LinkedHashMap<>();
        allLabels.put("School", new ArrayList<String>(){
            {
                add("HM");
                add("SM");
            }
        });

    }

    /**
     * This static method helps in choosing labels for a new note from pre-existing labels stored in a file as a {@code Hashtable} object.
     * @param noteTitle Title of the new note which is being created
     * @return label - String containing the chosen Label
     */
    public static ArrayList<String> chooseLabels(Integer uniqueID) {
        ArrayList<String> selectedLabels = new ArrayList<>();

        // Printing all the labels for the user to choose
        int i = 0;
        System.out.println("-- CHOOSE LABELS FROM THE BELOW ONES ----------");
        for(String label: allLabels.keySet()) {
            System.out.println((i++) + " --> " + label);
        }

        // [INCOMPLETE] Choosing labels, and adding those to selectedLabels, and Updating allLabels to include uniqueID of the current new note


        // Returning Selected Labels
        return selectedLabels;
    }

    /**
     * This static method updates {@code allLabels} to include the newly created label for the newly created note
     * @param newLabel The new label which has been created
     * @param noteTitle The title of the new note which has been created
     */
    public static void updateLabels(String newLabel, String noteTitle) {
        allLabels.put(newLabel, new ArrayList<String>(){
            {
                add(noteTitle);
            }
        });
    }
}
