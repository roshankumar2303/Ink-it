package com.inkit.program;

import java.util.LinkedHashMap;

public class Note {
    String title;
    String content;
    String labels;
    LinkedHashMap<String, Boolean> toDo;

    /* -------------------- Setters and Getters -------------------- */
    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getLabels() { return labels; }

    public void setLabels(String labels) { this.labels = labels; }

    public LinkedHashMap<String, Boolean> getToDo() { return toDo; }

    public void setToDo(LinkedHashMap<String, Boolean> toDo) { this.toDo = toDo; }

    @Override
    public String toString() {
        return  /* TITLE */
                TextUI.getLine(70) +
                TextUI.textWrap(title, 70) +
                TextUI.getLine(70) +

                /* NOTE CONTENT */
                TextUI.textWrap(content, 70) +

                /* TO-DO LIST */
                "\nTO-DO LIST:\n" +
                TextUI.formatTodoList(toDo) +

                /* LABELS */
                "\nLABELS: " + labels + "\n" +
                TextUI.getLine(70);
    }
}
