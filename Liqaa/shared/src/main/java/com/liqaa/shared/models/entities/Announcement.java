package com.liqaa.shared.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Announcement implements Serializable {
    private int id;
    private String title, content;

    public Announcement() {
    }

    public Announcement(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Announcement(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
