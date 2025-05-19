package com.example.assignment3;

public class EBook extends Item{
    private String genre;
    public EBook(String type, String title, String author, String year, String format, String genre) {
        super(type, title, author, year, format, genre);
        this.genre = genre;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    @Override
    public String getExtra() {
        return this.genre;
    }
    @Override
    public void setExtra(String extra) {
        this.genre = extra;
    }
}
