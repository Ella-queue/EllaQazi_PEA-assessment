package com.example.assignment3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

import java.io.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    // Bubble Sort by Title
    public ObservableList<Item> bubbleSortByTitle(ArrayList<Item> items) {
        ObservableList<Item> itemList = FXCollections.observableArrayList();
        itemList.addAll(items);
        int size = items.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (items.get(j).getTitle().compareToIgnoreCase(items.get(j + 1).getTitle()) > 0) {
                    Item temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                }
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("sortedLibrary.txt"))) {
            for (Item item : itemList) {
                String line = item.getType() + "*" + item.getTitle() + "*" + item.getAuthor() + "*" + item.getYear() + "*" + item.getFormat() + "*" + item.getExtra() + "\n";
                bw.write(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    // Bubble Sort by Year and Type
    public ObservableList<Item> bubbleSortByYearType(ArrayList<Item> items) {
        ObservableList<Item> itemList = FXCollections.observableArrayList();
        itemList.addAll(items);
        int size = items.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (items.get(j).getYear().compareToIgnoreCase(items.get(j + 1).getYear()) > 0) {
                    Item temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                }
            }
        }
        return itemList;
    }

    // Binary Search by Title
    public Item binarySearchByTitle(ArrayList<Item> items, String title) {
        int left = 0;
        int right = items.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (items.get(mid).getTitle().compareToIgnoreCase(title) == 0) {
                return items.get(mid);
            }
            if (items.get(mid).getTitle().compareToIgnoreCase(title) < 0) {
                left = mid + 1;
            }
            else if (items.get(mid).getTitle().compareToIgnoreCase(title) > 0){
                right = mid - 1;
            }
        }
        return null;
    }

    // Binary Search by Year and Type
    public List<Item> binarySearchByYearType(ArrayList<Item> items, String year, String type) {
        int left = 0;
        int right = items.size() - 1;
        int yearInt = Integer.parseInt(year);
        List<Item> itemList = new ArrayList<>();
        while (left <= right) {
            int mid = (left + right) / 2;
            Item middleItem = items.get(mid);
            int compYear = Integer.compare(Integer.parseInt(middleItem.getYear()), yearInt);
            int compTitle = middleItem.getType().compareToIgnoreCase(type);
            if (compYear == 0 && compTitle == 0) {
                itemList.add(middleItem);
            }
            if (compYear < 0 || (compYear == 0 && compTitle < 0)) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return itemList;
    }

    // Add EBook
    public void addEBook(String type, String title, String author, String year, String duration, String genre, TableView<Item> tableview, ArrayList<Item> items) {
        int currentYear = Year.now().getValue();
        if (title.isEmpty() || title.equals(" ")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the title");
            alert.showAndWait();
        }
        else if (author.isEmpty() || author.matches("[a-zA-Z]{2,}") == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the author");
            alert.showAndWait();
        }
        else if (year.isEmpty() || Integer.parseInt(year) < 1900 || Integer.parseInt(year) > currentYear) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid year");
            alert.showAndWait();
        }
        else if (duration.isEmpty() || Integer.parseInt(duration) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid duration");
            alert.showAndWait();
        }
        else if (genre.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid genre");
            alert.showAndWait();
        }
        else {
            Item eBook = new EBook(type, title, author, year, duration, genre);
            items.add(eBook);
            tableview.getItems().add(eBook);
        }
    }

    // Add Audio Book
    public void addAudioBook(String type, String title, String author, String year, String duration, String narrator, TableView<Item> tableview, ArrayList<Item> items) {
        int currentYear = Year.now().getValue();
        if (title.isEmpty() || title.equals(" ")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the title");
            alert.showAndWait();
        }
        else if (author.isEmpty() || author.matches("[a-zA-Z]{2,}") == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the author");
            alert.showAndWait();
        }
        else if (Integer.parseInt(year) < 1900 || Integer.parseInt(year) > currentYear) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid year");
            alert.showAndWait();
        }
        else if (duration.isEmpty() || Integer.parseInt(duration) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid duration");
            alert.showAndWait();
        }
        else if (narrator.isEmpty() || narrator.matches(".*[a-zA-Z][ ][a-zA-Z].*") == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid narrator");
            alert.showAndWait();
        }
        else {
            Item audioBook = new AudioBook(type, title, author, year, duration, narrator);
            items.add(audioBook);
            tableview.getItems().add(audioBook);
        }
    }

    // Add Research Paper
    public void addResearchPaper(String type, String title, String author, String year, String duration, String journalist, TableView<Item> tableview, ArrayList<Item> items) {
        int currentYear = Year.now().getValue();
        if (title.isEmpty() || title.equals(" ")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the title");
            alert.showAndWait();
        }
        else if (author.isEmpty() || author.matches("[a-zA-Z]{2,}") == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the author");
            alert.showAndWait();
        }
        else if (Integer.parseInt(year) < 1900 || Integer.parseInt(year) > currentYear) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid year");
            alert.showAndWait();
        }
        else if (duration.isEmpty() || Integer.parseInt(duration) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid duration");
            alert.showAndWait();
        }
        else if (journalist.isEmpty() || !(journalist.matches("^(?!\\d+$).+$"))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid journalist");
            alert.showAndWait();
        }
        else {
            Item researchPaper = new ResearchPaper(type, title, author, year, duration, journalist);
            items.add(researchPaper);
            tableview.getItems().add(researchPaper);
        }
    }

    // Update Items
    public void updateItem(int selectedIndex, TableView<Item> tableView, String type, String title, String author, String year, String duration, ArrayList<Item> items, String genre, String narrator, String journalist) {
        if (selectedIndex != -1) {
            items.get(selectedIndex).setType(type);
            items.get(selectedIndex).setTitle(title);
            items.get(selectedIndex).setAuthor(author);
            items.get(selectedIndex).setYear(year);
            items.get(selectedIndex).setFormat(duration);
            if (type.equals("eBook")) {
                items.get(selectedIndex).setGenre(genre);
            }
            else if (type.equals("Audio Book")) {
                items.get(selectedIndex).setNarrator(narrator);
            }
            else {
                items.get(selectedIndex).setJournalist(journalist);
            }
            //tableView.refresh();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a person from the table");
            alert.showAndWait();
        }
    }

    // Delete Items
    public void deleteItem(int selectedIndex, ArrayList<Item> items) {
        if (selectedIndex >= 0 && selectedIndex < items.size()) {
            items.remove(selectedIndex);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a contact from the table");
            alert.showAndWait();
        }
    }

    // Exit Program
    public void exitProgram() {
        System.exit(0);
    }

    // List All Items
    public String listAll(ArrayList<Item> items) {
        ObservableList<Item> itemsSort = bubbleSortByYearType(items);
        StringBuilder itemReport = new StringBuilder();
        for (Item item : itemsSort) {
                double cost = getAccessCost(item.getType(), item.getFormat());
            itemReport.append(getSummary(item.getType(), item.getTitle(), item.getAuthor(), item.getYear(), item.getFormat(), cost));
                if (isLicenseExpired(item.getType(), item.getYear())) {
                    itemReport.append(" [EXPIRED]");
                }
                itemReport.append("\n");
        }
        return itemReport.toString();
    }

    // List Items with Specific Year
    public String listByYear(ArrayList<Item> items, String year) {
        ObservableList<Item> itemsSort = bubbleSortByYearType(items);
        StringBuilder itemReport = new StringBuilder();
        boolean prevYear = false;
        for (Item item : itemsSort) {
            if (item.getYear().equals(year)) {
                if (prevYear == false) {
                    itemReport.append(year).append("\n");
                    prevYear = true;
                }
                double cost = getAccessCost(item.getType(), item.getFormat());
                itemReport.append(getSummary(item.getType(), item.getTitle(), item.getAuthor(), item.getYear(), item.getFormat(), cost));
                if (isLicenseExpired(item.getType(), item.getYear())) {
                    itemReport.append(" [EXPIRED]");
                }
                itemReport.append("\n");
            }
        }
        return itemReport.toString();
    }

    public double getAccessCost(String type, String duration) {
        if (type.equals("eBook")) {
            double result = Integer.parseInt(duration) * 0.02;
            return result;
        }
        else if (type.equals("Audio Book")) {
            double result = Integer.parseInt(duration) * 0.05;
            return result;
        }
        else {
            double result = Integer.parseInt(duration) * 0.10;
            return result;
        }
    }

    public String getSummary(String type, String title,  String author, String year, String duration, double cost) {
        String unit;
        if (type.equals("eBook")) {
            unit = "pages";
        }
        else if (type.equals("Audio Book")) {
            unit = "mins";
        }
        else {
            unit = "pages";
        }
        String result = ("[" + type + "] " + title + " by " + author + " (" + year + " " + duration + " " + unit + ") - $" + cost);
        return result;
    }

    public boolean isLicenseExpired(String type, String year) {
        int yearInt = Integer.parseInt(year);
        if (type.equals("eBook")) {
            if (yearInt < 2013) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (type.equals("Audio Book")) {
            if (yearInt < 2015) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if (yearInt < 2019) {
                return true;
            }
            else {
                return false;
            }
        }
    }
}
