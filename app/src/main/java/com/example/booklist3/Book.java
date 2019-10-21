package com.example.booklist3;

/**
 * Created by 谭小二 on 2019/10/19.
 */

public class Book {
    public Book(String title, int coverResoourseId) {
        this.setTitle(title);
        this.setCoverResourceId(coverResoourseId);
    }

    private String title;
    private int coverResourceId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCoverResourceId() {
        return coverResourceId;
    }

    public void setCoverResourceId(int coverResourceId) {
        this.coverResourceId = coverResourceId;
    }
}
