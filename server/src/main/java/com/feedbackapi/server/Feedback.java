package com.feedbackapi.server;

public class Feedback {
    private final long id;
    private final String title;
    private final String description;
    private final int rating;

    public Feedback(long id, String title, String description, int rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
    } 

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }
}
