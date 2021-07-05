package com.inkit.program;

import java.util.ArrayList;
import java.util.Scanner;

public class Run {
    static Scanner inp = new Scanner(System.in);

    public static void searchSuggestions(String query, ArrayList<String> list) {
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

    public static Note searchByTitle(String query, MyNotes user) {
        ArrayList<String> titles = user.getTitles();
        if(titles.contains(query))
            return user.getNotes(query);
        searchSuggestions(query, titles);
        return null;
    }

    public static ArrayList<String> searchByLabel(String query, MyNotes user) {
        ArrayList<String> labels = user.md.getLabels();
        if(labels.contains(query))
            return user.md.getLabelTitles(query);
        searchSuggestions(query, labels);
        return null;
    }

    public static Note search(MyNotes user) {
        Note result = null;
        switch(TextUI.selectOptions("Search by Title", "Search by label")) {
            case 1:
                while(result == null) {
                    System.out.println("Enter the title of your note (Press -1 to stop searching): ");
                    String title = inp.nextLine();
                    if(title.equalsIgnoreCase("-1"))
                        return null;
                    result = searchByTitle(title, user);
                }
                break;

            case 2:
                while(result == null){
                    System.out.println("Enter the label of your note (Press -1 to stop searching): ");
                    String label = inp.nextLine();
                    if(label.equalsIgnoreCase("-1"))
                        return null;
                    ArrayList<String> titles = searchByLabel(label, user);
                    if(titles == null)
                        continue;
                    int i = 1;
                    for(String title: titles)
                            System.out.println((i++) + ". " + title);
                    System.out.println("Enter your choice of title : ");
                    String title = inp.nextLine();
                    result = user.getNotes(title);
                }
                break;

            default:
                System.out.println("Invalid Input...");
        }
        return result;
    }

    public static void main(String[] args) {

        MyNotes user1 = new MyNotes();
        user1.createNote();
        user1.createNote();
        user1.createNote();

        System.out.println(search(user1));

        /* TEST FOR ConsoleUI.textWrap()
        String temp = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).";
        System.out.println(ConsoleUI.textWrap(temp, 70));
        */
    }
}
