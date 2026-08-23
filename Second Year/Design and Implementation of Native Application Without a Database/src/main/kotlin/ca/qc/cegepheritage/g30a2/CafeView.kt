package ca.qc.cegepheritage.g30a2

import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.util.LinkedList
import java.util.Observable

class CafeView: CafeDisplay {
    lateinit var presenter: CafePresenter
    val root: BorderPane
    var addOrderBar: HBox
    var displayOrderQueue: HBox
    var orderQueue: VBox
    var optionBar: HBox
    var textSection: HBox
    var infoSection: VBox
    var serveSection: VBox
    var cancelSection: VBox
    var priorSection: VBox
    var bottomSection: VBox
    var centerSection: VBox
    var scrollSection: ScrollPane
    // Labels
    val titleLabel = Label("Cat Cafe")
    val nextLabel = Label("Next Order: ?")
    val waitingLabel = Label("Orders Waiting: 0")
    val infoLabel = Label("")
    val queueLabel = Label("Queue")
    // Text Fields
    val nameField = TextField()
    val cancelField = TextField()
    val priorField = TextField()
    // Combo Boxes
    val foodBox = ComboBox<Food>()
    val drinkBox = ComboBox<Drink>()
    // Colours
    val cPurple: String = "C7A3CC"
    val cDarkerPurple: String = "B58DBA"
    val cPink: String = "F2C2DB"
    val cWhite: String = "F7F5F7"
    val cBlue: String = "BEDEEB"
    val cBlack: String = "222222"


    init {
        val addOrderButton = Button("Add Order").apply {
            setOnAction {
                // Get values from Text Fields
                // Change initialization
                val customerName: String = nameField.getText()
                val foodOrdered: Food? = foodBox.getValue()
                val drinkOrdered: Drink? = drinkBox.getValue()
                presenter.onAddOrder(customerName, foodOrdered, drinkOrdered)
            }
            style = "-fx-background-color: #$cWhite; -fx-text-fill: #$cBlack; -fx-border-color: #$cWhite; -fx-border-width: 2px; -fx-border-radius: 10px;"
        }
        val serveButton = Button("Serve").apply {
            setOnAction {
                presenter.onServeOrder()
            }
            style = "-fx-background-color: #$cBlue; -fx-text-fill: #$cBlack; -fx-border-color: #$cBlue; -fx-border-width: 2px; -fx-border-radius: 10px;"
        }
        val cancelButton = Button("Cancel").apply {
            setOnAction {
                val orderID: Int = Integer.parseInt(cancelField.getText())
                presenter.onCancelOrder(orderID)
            }
            style = "-fx-background-color: #$cPink; -fx-text-fill: #$cBlack; -fx-border-color: #$cPink; -fx-border-width: 2px; -fx-border-radius: 10px;"
        }
        val prioritizeButton = Button("Prioritize").apply {
            setOnAction {
                val orderID: Int = Integer.parseInt(priorField.getText())
                presenter.onPrioritizeOrder(orderID)
            }
            style = "-fx-background-color: #$cWhite; -fx-text-fill: #$cBlack; -fx-border-color: #$cWhite; -fx-border-width: 2px; -fx-border-radius: 10px;"
        }
        val closeRegisterButton = Button("Close Register").apply {
            setOnAction {
                presenter.onCloseRegister()
            }
            style = "-fx-background-color: #$cBlack; -fx-text-fill: #$cWhite; -fx-border-color: #$cBlack; -fx-border-width: 2px; -fx-border-radius: 10px;"
        }

        nameField.setPromptText("Customer Name")
        cancelField.setPromptText("Order ID to Cancel")
        priorField.setPromptText("Order ID to Prioritize")

        addOrderBar = HBox(nameField, drinkBox, foodBox, addOrderButton, closeRegisterButton).apply {
            alignment = Pos.CENTER
        }

        queueLabel.apply {
            alignment = Pos.CENTER_LEFT
        }

        displayOrderQueue = HBox().apply {
            style = "-fx-background-color: #$cWhite;  -fx-border-color: #$cWhite; -fx-border-width: 10px; -fx-pref-height: 25px;"
            spacing = 5.0
            alignment = Pos.CENTER
        }

        scrollSection = ScrollPane(displayOrderQueue).apply {
            setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER)
        }

        orderQueue = VBox(queueLabel, scrollSection).apply {
            spacing = 2.5
        }

        textSection = HBox(nextLabel, waitingLabel).apply {
            style = "-fx-background-color: #$cDarkerPurple;  -fx-border-color: #$cDarkerPurple; -fx-border-width: 2px;"
            prefWidth = 500.0
            spacing = 5.0
            alignment = Pos.CENTER
        }

        infoSection = VBox(infoLabel).apply {
            alignment = Pos.CENTER
            spacing = 5.0
        }

        serveSection = VBox(serveButton).apply {
            style = "-fx-background-color: #$cDarkerPurple; -fx-padding: 5px;"
        }

        cancelSection = VBox(cancelField, cancelButton).apply {
            style = "-fx-background-color: #$cDarkerPurple; -fx-padding: 5px;"
            spacing = 10.0
        }

        priorSection = VBox(priorField, prioritizeButton).apply {
            style = "-fx-background-color: #$cDarkerPurple; -fx-padding: 5px;"
            spacing = 10.0
        }

        //  -fx-border-color: #$cPurple; -fx-border-width: 5px; -fx-border-radius: 10px;

        optionBar = HBox(serveSection, cancelSection, priorSection).apply {
            style = "-fx-background-color: #$cDarkerPurple; -fx-padding: 5px;"
            alignment = Pos.CENTER
            spacing = 10.0
        }

        bottomSection = VBox(textSection, optionBar).apply {
            style = "-fx-background-color: #$cDarkerPurple; -fx-padding: 5px;"
            alignment = Pos.CENTER
            spacing = 10.0
        }

        titleLabel.apply {
            style = "-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #$cWhite; -fx-padding: 10px;"
            alignment = Pos.TOP_CENTER
        }

        centerSection = VBox(addOrderBar, infoSection, orderQueue, bottomSection).apply {
            style = "-fx-max-width: 650px;"
            spacing = 10.0
        }

        root = BorderPane().apply {
            BorderPane.setAlignment(titleLabel, Pos.TOP_CENTER)
            setTop(titleLabel)
            setCenter(centerSection)
            style = "-fx-background-color: #$cPurple; -fx-padding: 10px;"
        }

//        root = BorderPane(titleLabel, centerSection).apply {
//            style = "-fx-background-color: #$cPurple;"
//        }
    }

    override fun showOrderQueue(orderQueue: LinkedList<Order>) {
        displayOrderQueue.children.clear()
        if(orderQueue.isEmpty()) {
            nextLabel.setText("Next Order: ?")
            waitingLabel.setText("Orders Waiting: 0")
            infoLabel.setText("No orders")
        }
        else {
            orderQueue.forEach { order ->
                // Fix drink and food
                displayOrderQueue.children.add(Label("#${order.id} • ${order.customerName} • ${order.drink} + ${order.food}").apply {
                    style = "-fx-background-color: #$cDarkerPurple; -fx-text-fill: #$cBlack; -fx-border-color: #$cBlack;"
                })
            }
        }
    }

    override fun updateOrdersWaiting(ordersWaiting: Int) {
        waitingLabel.setText("Orders Waiting: $ordersWaiting")
    }

    override fun updateNextOrder(orderQueue: LinkedList<Order>) {
        val order = orderQueue.first()
        nextLabel.setText("Next Order: #${order.id} • ${order.customerName} • ${order.drink} + ${order.food}")
    }

    override fun showNewOrder(order: Order, wasAdded: Boolean) {
        var message: String = ""
        message = if(!wasAdded) {
            "Please fill in all fields."
        } else {
            "Added #${order.id} • ${order.customerName} • ${order.drink} + ${order.food}"
        }
        showInfo(message)
    }

    override fun showServedOrder(order: Order?) {
        var message: String = ""
        message = "Served #${order?.id} • ${order?.customerName} • ${order?.drink} + ${order?.food}"
        showInfo(message)
    }

    override fun showCancelledOrder(order: Order?) {
        var message: String = ""
        val orderID = order?.id
        message = if(orderID == null) {
            "Please input a valid orderID"
        } else {
            "Cancelled #${orderID}"
        }
        showInfo(message)
    }

    override fun showPriorOrder(order: Order?) {
        var message: String = ""
        message = if(order?.id == null) {
            "Please input a valid order ID"
        } else {
            "Prioritized to head #${order.id}"
        }
        showInfo(message)
    }

    override fun showInfo(msg: String) {
        infoLabel.setText(msg)
    }

    override fun populateComboBoxes(drinkData: ObservableList<Drink>, foodData: ObservableList<Food>) {
        drinkBox.items.addFirst(null)
        for(drink in drinkData) {
            drinkBox.items.add(drink)
        }
        foodBox.items.addFirst(null)
        for(food in foodData) {
            foodBox.items.add(food)
        }
    }
}