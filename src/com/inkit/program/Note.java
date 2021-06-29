package com.inkit.program;

import java.util.Hashtable;

public class Note {
    String title;
    String text;
    String labels;
    Hashtable<String, Boolean> toDo;

    /* -------------------- Setters and Getters -------------------- */
    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public String getLabels() { return labels; }

    public void setLabels(String labels) { this.labels = labels; }

    public Hashtable<String, Boolean> getToDo() { return toDo; }

    public void setToDo(Hashtable<String, Boolean> toDo) { this.toDo = toDo; }

    @Override
    public String toString() {
        return  "---------- " + title + " ----------\n" +
                text + "\n" +
                toDo + "\n" +
                "\nThis note falls under:" + labels + "\n";
    }
}
