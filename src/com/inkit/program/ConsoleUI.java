package com.inkit.program;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * ConsoleUI class provides methods to present content on the Terminal in a better way
 */
public class ConsoleUI {

    /**
     * Returns the wrapped sentence with the specified wrap-width
     * @param str The sentence which is to be wrapped
     * @param width The wrap-width
     * @return Wrapped sentence as a {@code String}
     */
    public static String textWrap(String str, int width) {
        int currLen = 0;
        String[] words = str.split("\\s+");
        StringBuilder finalStr = new StringBuilder();

        for(String word: words) {
            if(currLen + word.length() >= width) {
                if(word.length() < width) {
                    finalStr.append("\n").append(word).append(" ");
                    currLen = word.length() + 1;
                }
                else {
                    // The word itself is longer than the wrap limit
                    // In that case, it has to be split
                    StringBuilder temp = new StringBuilder(word);
                    temp.insert(width - currLen, '\n');
                    finalStr.append(temp).append(" ");
                    currLen = word.length() - (width - currLen) + 1;
                    // Neglecting a possible fail-case, which might occur at really small wrap-widths
                }
            }
            else {
                finalStr.append(word).append(" ");
                currLen += word.length() + 1;
            }
        }

        finalStr.append("\n");
        return finalStr.toString();
    }

    /**
     * Returns the to-do list as a String
     * @param toDo To-Do list as a Hashtable
     * @return String which contains a print-able To-Do list
     */
    public static String formatTodoList(Hashtable<String, Boolean> toDo) {
        StringBuilder formatted = new StringBuilder();

        try {
            Enumeration<String> td = toDo.keys();
            while (td.hasMoreElements()) {
                // Adding the to-do list item
                String currItem = td.nextElement();
                formatted.append(currItem).append(" - ");

                // Adding the status of the to-do list item
                if (toDo.get(currItem))
                    formatted.append("✔");
                else
                    formatted.append("❌");
                formatted.append('\n');
            }
        }
        catch (NullPointerException npe) {
            // i.e., There is no to-do list
            return "Nothing here. Modify this note to add items to the list\n";
        }

        return formatted.toString();
    }

    /**
     * Returns a dashed line as a {@code String} with the specified length, with new-line character at its end
     * @param len Length of the line
     * @return The dashed line
     */
    public static String line(int len) {
        return new String(new char[len]).replace('\0', '=') + "\n";
    }

}
