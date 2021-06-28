package com.inkit.program;

import java.util.ArrayList;
import java.util.Hashtable;

public class Labels {
    // KEY: String -> Label
    // VAL: Integer[] -> uniqueIDs of all the notes under that label
    static Hashtable<String, Integer[]> allLabels;

    static {
        // [INCOMPLETE] This static block runs for the first time when "Labels" class has been invoked through a static method
        // [INCOMPLETE] Retrieve all the labels from the file and store it in "allLabels"
        // Hashtable is serializable. Not sure about Integer[] tho. But I guess we can write Hashtable object to the file, and it can be retrieved -- Roshan
        allLabels = new Hashtable<>(); // --->> JUST A PLACEHOLDER FOR NOW

    }

    /**
     * This static method helps in choosing labels from pre-existing labels stored in a file as a {@code Hashtable} object.
     * @param uniqueID The unique ID of a note whose labels are being chosen
     * @return  ArrayList of chosen Labels
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
}
