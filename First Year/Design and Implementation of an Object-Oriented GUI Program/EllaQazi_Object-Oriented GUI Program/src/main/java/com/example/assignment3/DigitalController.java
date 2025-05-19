package com.example.assignment3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.StringTokenizer;

public class DigitalController {
    // Fields
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField durationField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField narratorField;
    @FXML
    private TextField journalistField;

    // Buttons
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;

    // Table
    @FXML
    private TableView<Item> tableview;
    @FXML
    private TableColumn<Item, String> typeColumn;
    @FXML
    private TableColumn<Item, String> titleColumn;
    @FXML
    private TableColumn<Item, String> authorColumn;
    @FXML
    private TableColumn<Item, String> yearColumn;
    @FXML
    private TableColumn<Item, String> formatColumn;
    @FXML
    private TableColumn<Item, String> exColumn;
    private int selectedIndex = -1;

    // TextArea
    @FXML
    private TextArea textArea;

    // ComboBox
    @FXML
    private ComboBox<String> comboBox;

    // ObservableList
    ObservableList<String> options = FXCollections.observableArrayList();

    // ArrayList
    ArrayList<Item> items = new ArrayList<Item>();
    ArrayList<Item> searchedItems;

    private Utility utility;

    public void initialize() {
        options.add("eBook");
        options.add("Audio Book");
        options.add("Research Paper");
        comboBox.setItems(options);
        utility = new Utility();
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        formatColumn.setCellValueFactory(new PropertyValueFactory<>("format"));
        exColumn.setCellValueFactory(new PropertyValueFactory<>("extra"));
        tableview.setOnMouseClicked(event -> {
            Item selected = tableview.getSelectionModel().getSelectedItem();
            selectedIndex = tableview.getSelectionModel().getSelectedIndex();
            String selectedType = comboBox.getSelectionModel().getSelectedItem();
            if (selected != null) {
                titleField.setText(selected.getTitle());
                authorField.setText(selected.getAuthor());
                yearField.setText(selected.getYear());
                durationField.setText(selected.getFormat());

                switch (selectedType) {
                    case "eBook":
                        genreField.setText(selected.getGenre());
                        break;
                        case "Audio Book":
                            narratorField.setText(selected.getNarrator());
                            break;
                            case "Research Paper":
                                journalistField.setText(selected.getJournalist());
                                break;
                                default:
                                    break;
                }
            }
        });
    }

    // Read from file
    public void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("library.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, "*");
                String type = st.nextToken().trim();
                String title = st.nextToken().trim();
                String author = st.nextToken().trim();
                String year = st.nextToken().trim();
                String duration = st.nextToken().trim();
                String extra = st.nextToken().trim();
                Item item;
                switch(type) {
                    case "eBook":
                        item = new EBook(type, title, author, year, duration, extra);
                        break;
                    case "Audio Book":
                        item = new AudioBook(type, title, author, year, duration, extra);
                        break;
                    case "Research Paper":
                        item = new ResearchPaper(type, title, author, year, duration, extra);
                        break;
                    default:
                        System.out.println("Error!!!");
                        continue;
                }
                switch (type) {
                    case "eBook":
                        item.setType("eBook");
                        break;
                    case "Audio Book":
                        item.setType("Audio Book");
                        break;
                    case "Research Paper":
                        item.setType("Research Paper");
                        break;
                }
                items.add(item);
            }
            tableview.getItems().addAll(items);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Write to file
    public void writeFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("library.txt"))) {
            for (Item item : tableview.getItems()) {
                String line = item.getType() + "*" + item.getTitle() + "*" + item.getAuthor() + "*" + item.getYear() + "*" + item.getFormat() + "*" + item.getExtra() + "\n";
                bw.write(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add Item
    public void onAddButtonClick() {
        String selectedType = comboBox.getSelectionModel().getSelectedItem();
        if (selectedType != null && !selectedType.isEmpty()) {
            switch (selectedType) {
                case "eBook":
                    utility.addEBook(selectedType, titleField.getText(), authorField.getText(), yearField.getText(), durationField.getText(), genreField.getText(), tableview, items);
                    break;
                    case "Audio Book":
                        utility.addAudioBook(selectedType, titleField.getText(), authorField.getText(), yearField.getText(), durationField.getText(), narratorField.getText(), tableview, items);
                        break;
                        case "Research Paper":
                            utility.addResearchPaper(selectedType, titleField.getText(), authorField.getText(), yearField.getText(), durationField.getText(), journalistField.getText(), tableview, items);
                            break;
                            default:
                                //System.out.println("Error!!!");
                                break;
            }
        }
    }

    // Update Item
    public void onUpdateButtonClick() {
        String selectedType = comboBox.getSelectionModel().getSelectedItem();
        utility.updateItem(selectedIndex, tableview, selectedType, titleField.getText(), authorField.getText(), yearField.getText(), durationField.getText(), items, genreField.getText(), narratorField.getText(), journalistField.getText());
        tableview.getItems().clear();
        tableview.getItems().addAll(items);
    }

    // Delete Item
    public void onDeleteButtonClick() {
        utility.deleteItem(selectedIndex, items);
        tableview.getItems().clear();
        tableview.getItems().addAll(items);
    }

    // Save Items (Button)
    public void onSaveButtonClick() {
        writeFile();
    }

    // Save Items (Menu)
    public void saveItems() {
        writeFile();
    }

    // Load Items
    public void loadItems() {
        tableview.getItems().clear();
        readFile();

    }

    // Exit
    public void exitProgram() {
       utility.exitProgram();
    }

    // Search by Title
    public void searchByTitle() {
        tableview.getItems().clear();
        String selectedType = comboBox.getSelectionModel().getSelectedItem();
        ObservableList<Item> itemsSortedTitle = utility.bubbleSortByTitle(items);
        Item searchResults = utility.binarySearchByTitle(items, titleField.getText());
        if (searchResults  != null) {
            ObservableList<Item> singleItems = FXCollections.observableArrayList(searchResults);
            tableview.getItems().addAll(singleItems);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No items found");
            alert.showAndWait();
        }
    }

    // Search by Year and Type
    public void searchByYearType() {
        tableview.getItems().clear();
        String selectedType = comboBox.getSelectionModel().getSelectedItem();
        List<Item> searchResults = utility.binarySearchByYearType(items, yearField.getText(), selectedType);
        if (searchResults != null) {
            ObservableList<Item> singleItems = FXCollections.observableArrayList(searchResults);
            tableview.getItems().addAll(singleItems);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No items found");
            alert.showAndWait();
        }
    }

    // List all Items
    public void listAll() {
       textArea.setText(utility.listAll(items));
    }

    // List Items with Specific Year
    public void listByYear() {
        textArea.setText(utility.listByYear(items, yearField.getText()));
    }

    // Get Access Cost
    public void getAccessCost() {
        textArea.clear();
        String selectedType = comboBox.getSelectionModel().getSelectedItem();
        String resultStr = String.valueOf(utility.getAccessCost(selectedType, durationField.getText()));
        textArea.setText(resultStr);
    }

    // Get Summary
    public void getSummary() {
        textArea.clear();
        String selectedType = comboBox.getSelectionModel().getSelectedItem();
        double cost = utility.getAccessCost(selectedType, durationField.getText());
        String result = utility.getSummary(selectedType, titleField.getText(), authorField.getText(), yearField.getText(), durationField.getText(), cost);
        textArea.setText(result);
    }

    // Is License Expired
    public void isLiscenseExpired() {
        textArea.clear();
            String selectedType = comboBox.getSelectionModel().getSelectedItem();
            double cost = utility.getAccessCost(selectedType, durationField.getText());
        String unit;
        if (selectedType.equals("eBook")) {
            unit = "pages";
        }
        else if (selectedType.equals("Audio Book")) {
            unit = "mins";
        }
        else {
            unit = "pages";
        }
        if (utility.isLicenseExpired(selectedType, yearField.getText())) {
                textArea.setText("[" + selectedType + "] " + titleField.getText() + " by " + authorField.getText() + " (" + durationField.getText() + " " + unit + ") - $" + cost + " [EXPIRED]");
            }
        else {
            textArea.setText("[" + selectedType + "] " + titleField.getText() + " by " + authorField.getText() + " (" + durationField.getText() + " " + unit + ") - $" + cost);
        }
    }

}
