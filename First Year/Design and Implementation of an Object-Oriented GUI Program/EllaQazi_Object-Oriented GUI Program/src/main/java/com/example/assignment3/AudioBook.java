package com.example.assignment3;

public class AudioBook extends Item {
    private String narrator;
    public AudioBook(String type, String title, String author, String year, String format, String narrator) {
        super(type, title, author, year, format, narrator);
        this.narrator = narrator;
    }
    public String getNarrator() {
        return narrator;
    }
    public void setNarrator(String narrator) {
        this.narrator = narrator;
    }
    @Override
    public String getExtra() {
        return this.narrator;
    }
    @Override
    public void setExtra(String extra) {
        this.narrator = extra;
    }
}
