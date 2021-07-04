package com.inkit.program;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * {@code Labels} class provides methods to operate on Note labels
 */
public class Labels {

    static Scanner inp = new Scanner(System.in);
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
    public static String chooseLabels(String noteTitle) {

        // Printing all the labels for the user to choose
        int i = 1;
        System.out.println("-- CHOOSE LABELS FROM THE BELOW ONES ----------");
        for(String label: allLabels.keySet()) {
            System.out.println((i++) + " --> " + label);
        }

        // Choosing the label
        String label;
        System.out.println("Enter your choice of label : ");
        Scanner sc = new Scanner(System.in);
        label = sc.nextLine();

        // Update Titles array corresponding to the table
        ArrayList<String> linkedNotes = allLabels.get(label);
        linkedNotes.add(noteTitle);
        allLabels.put(label, linkedNotes);

        // Returning Label
        return label;
    }

}
