package com.example.assignment3;

public abstract class Item {
    protected String type;
    protected String title;
    protected String author;
    protected String year;
    protected String duration;
    private String genre;
    private String narrator;
    private String journalist;
    public Item(String type, String title, String author, String year, String duration, String extra) {
        this.type = type;
        this.title = title;
        this.author = author;
        this.year = year;
        this.duration = duration;
    }
    // Setters
    public void setType(String type) {
        this.type = type;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public void setFormat(String duration) {
        this.duration = duration;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setNarrator(String narrator) {
        this.narrator = narrator;
    }
    public void setJournalist(String journalist) {
        this.journalist = journalist;
    }
    // Getters
    public String getType() {
        return type;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getYear() {
        return year;
    }
    public String getFormat() {
        return duration;
    }
    public String getGenre() {
        return genre;
    }
    public String getNarrator() {
        return narrator;
    }
    public String getJournalist() {
        return journalist;
    }
    public abstract String getExtra();
    public abstract void setExtra(String extra);
}
