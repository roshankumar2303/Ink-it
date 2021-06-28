package com.inkit.program;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Labels {
    static LinkedHashSet<String> allLabels;

    static {
        // [INCOMPLETE] This static block runs for the first time when "Labels" class has been invoked through a static method
        // [INCOMPLETE] Read all the labels from the file and store it in "allLabels"
        allLabels = new LinkedHashSet<>(); // --->> JUST A PLACEHOLDER FOR NOW

    }

    /**
     * This static method helps in choosing labels from pre-existing labels.
     * @param uniqueID The unique ID of a note whose labels are being chosen
     * @return  ArrayList of chosen Labels
     */
    public static ArrayList<String> chooseLabels(Integer uniqueID) {
        ArrayList<String> selectedLabels = new ArrayList<>();

        // Printing all the labels for the user to choose
        int i = 0;
        System.out.println("-- CHOOSE LABELS FROM THE BELOW ONES ----------");
        for(String label: allLabels) {
            System.out.println((i++) + " --> " + label);
        }

        // [INCOMPLETE] Choosing labels, and adding those to selected labels

        // Returning Selected Labels
        return selectedLabels;
    }
}
