package com.inkit.program;

import java.io.Serializable;
import java.util.*;

/**
 * ConsoleUI class provides methods to present content on the Terminal in a better way
 */
public class TextUI implements Serializable {

    static Scanner inp = new Scanner(System.in);

    /**
     * ANSI ESCAPE CODES - COLORING
     * <p>Reference: https://www.lihaoyi.com/post/BuildyourownCommandLinewithANSIescapecodes.html</p>
     */
    static final String ANSI_RESET = "\u001B[0m";

    static final String ANSI_BLACK = "\u001B[30m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_PURPLE = "\u001B[35m";
    static final String ANSI_CYAN = "\u001B[36m";
    static final String ANSI_WHITE = "\u001B[37m";

    static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

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
     * @param to_do To-Do list as a Hashtable
     * @return String which contains a print-able To-Do list
     */
    public static String formatTodoList(LinkedHashMap<String, Boolean> to_do) {
        StringBuilder formatted = new StringBuilder();

        try {
            Set<String> todoItems = to_do.keySet();
            for(String item: todoItems) {
                // Adding the to-do list item
                formatted.append(item).append(" - ");

                // Adding the status of the to-do list item
                if (to_do.get(item))
                    formatted.append("✔");
                else
                    formatted.append("❌");
                formatted.append('\n');
            }
        }
        catch (NullPointerException npe) {
            // i.e., There is no to-do list
            return "Nothing here... Modify this note to add items to the list\n";
        }

        return formatted.toString();
    }

    /**
     * Returns a dashed line as a {@code String} with the specified length, with new-line character at its end
     * @param len Length of the line
     * @return The dashed line
     */
    public static String getLine(int len) {
        return new String(new char[len]).replace('\0', '=') + "\n";
    }

    /**
     * Draws/prints a dashed line, with the specified length. The cursor is then moved to the newline
     * @param len Length of the line
     */
    public static void drawLine(int len) {
        System.out.println(new String(new char[len]).replace('\0', '='));
    }


    public static void highlight(String message, int lineLen) {
        System.out.print("\n");
        drawLine(lineLen);
        System.out.println(message);
        drawLine(lineLen);
    }

    /**
     * Ask the user a YES (or) NO question. Reads the user's choice and returns it as a boolean
     * @param question The question being asked
     * @return User's choice as a boolean value
     */
    public static boolean yesOrNo(String question) {
        System.out.print(question + " " + ANSI_CYAN_BACKGROUND + ANSI_BLACK + " (Y/n) " + ANSI_RESET + " > ");
        return (inp.nextLine()).equalsIgnoreCase("Y");
    }

    /**
     * Displays all the options and asks the user to select one of the options by giving its corresponding number as input
     * @param options Varargs: The options to be asked
     * @return Corresponding number to the option chosen by the user
     */
    public static int selectFromOptions(String ...options) {
        int optNo = 1;
        for(String option: options) {
            System.out.println((optNo++) + ". " + option);
        }

        System.out.print("\nYour Choice > ");
        return Integer.parseInt(inp.nextLine());
    }
}
