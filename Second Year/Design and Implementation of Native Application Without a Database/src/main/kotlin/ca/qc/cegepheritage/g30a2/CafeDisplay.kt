package ca.qc.cegepheritage.g30a2

import javafx.collections.ObservableList
import javafx.scene.control.ComboBox
import java.util.LinkedList
import java.util.Observable

interface CafeDisplay {
    fun showOrderQueue(orderQueue: LinkedList<Order>)
    fun updateOrdersWaiting(ordersWaiting: Int)
    fun updateNextOrder(orderQueue: LinkedList<Order>)
    fun showNewOrder(order: Order, wasAdded: Boolean)
    fun showServedOrder(order: Order?)
    fun showCancelledOrder(order: Order?)
    fun showPriorOrder(order: Order?)
    fun showInfo(msg: String)
    fun populateComboBoxes(drinkData: ObservableList<Drink>, foodData: ObservableList<Food>)
}