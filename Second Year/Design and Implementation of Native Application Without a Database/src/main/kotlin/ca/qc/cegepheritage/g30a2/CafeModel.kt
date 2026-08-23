package ca.qc.cegepheritage.g30a2
import javafx.collections.FXCollections.observableList
import javafx.collections.ObservableList
import java.util.LinkedList

class CafeModel(queuedOrders: List<Order> = emptyList()) {
    val orderQueue = LinkedList<Order>()

    init {
        queuedOrders.forEach{addOrder(it.customerName, it.food, it.drink, it.id)}
    }

    fun addOrder(customerName: String, foodOrdered: Food?, drinkOrdered: Drink?, id: Int = orderQueue.size + 1): Order? {
        if(customerName.trim() == "" || (foodOrdered == null && drinkOrdered == null)) {
            return null
        }
        else {
            var order: Order = Order(id, customerName, drinkOrdered, foodOrdered)
            orderQueue.addLast(order)
            return order
        }
    }

    fun serveOrder(orders: LinkedList<Order>): Order? {
        if(orderQueue.isEmpty()) {
            return null
        }
        else if(orderQueue.size == 1) {
            orderQueue.clear()
            return null
        }
        else {
            orderQueue.removeFirst()
            return orders.first()
        }
    }

    fun cancelOrder(orderID: Int?): LinkedList<Order>? {
        if(orderID == null) {
            return null
        }
        else {
            val cancelledOrder = orderQueue.find {it.id == orderID}
            orderQueue.remove(cancelledOrder)
            return orderQueue
        }
    }

    fun priorOrder(orderID: Int?): LinkedList<Order>? {
        if(orderID == null) {
            return null
        }
        else {
            val priorOrder = orderQueue.find {it.id == orderID}
            orderQueue.remove(priorOrder)
            if (priorOrder !== null) {
                orderQueue.addFirst(priorOrder)
            }
            return orderQueue
        }
    }

    fun closeRegister(): LinkedList<Order> {
        orderQueue.clear()
        return orderQueue
    }

    fun ordersWaiting(): Int {
        return orderQueue.size
    }

    fun<T> getEnumData(items: List<T>): ObservableList<T> {
        return observableList(items)
    }
}