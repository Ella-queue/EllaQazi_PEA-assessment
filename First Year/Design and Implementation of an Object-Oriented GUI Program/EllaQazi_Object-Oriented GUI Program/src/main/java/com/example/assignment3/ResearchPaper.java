package com.example.assignment3;

public class ResearchPaper extends Item {
    private String journalist;
    public ResearchPaper(String type, String title, String author, String year, String format, String journalist) {
        super(type, title, author, year, format, journalist);
        this.journalist = journalist;
    }
    public String getJournalist() {
        return journalist;
    }
    public void setJournalist(String journalist) {
        this.journalist = journalist;
    }
    @Override
    public String getExtra() {
        return this.journalist;
    }
    @Override
    public void setExtra(String extra) {
        this.journalist = extra;
    }

}
