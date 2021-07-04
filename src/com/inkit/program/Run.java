package com.inkit.program;

import java.sql.Array;
import java.util.ArrayList;

class TitleOps {
    ArrayList<String> titles = new ArrayList<>();
    void addTitle(String title){
        titles.add(title);
    }
    void updateTitlesFile(){

    }
}

public class Run {
    public static void main(String[] args) {
        Note note1 = NoteOperations.createNote();
        System.out.println(note1);

        /* TEST FOR ConsoleUI.textWrap()
        String temp = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).";
        System.out.println(ConsoleUI.textWrap(temp, 70));
        */
    }
}
