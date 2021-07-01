package com.inkit.program;

import java.util.Hashtable;

public class Note {
    String title;
    String content;
    String labels;
    Hashtable<String, Boolean> toDo;

    /* -------------------- Setters and Getters -------------------- */
    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getLabels() { return labels; }

    public void setLabels(String labels) { this.labels = labels; }

    public Hashtable<String, Boolean> getToDo() { return toDo; }

    public void setToDo(Hashtable<String, Boolean> toDo) { this.toDo = toDo; }

    @Override
    public String toString() {
        return  /* TITLE */
                ConsoleUI.line(70) +
                ConsoleUI.textWrap(title, 70) +
                ConsoleUI.line(70) +

                /* NOTE CONTENT */
                ConsoleUI.textWrap(content, 70) +

                /* TO-DO LIST */
                "\nTo-Do:\n" +
                ConsoleUI.formatTodoList(toDo) +

                /* LABELS */
                "\nLABELS: " + labels + "\n" +
                ConsoleUI.line(70);
    }
}
