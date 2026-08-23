package ca.qc.cegepheritage.g30a2

class CafePresenter(val model: CafeModel, val view: CafeDisplay) {
    fun start() {
        view.showOrderQueue(model.orderQueue)
        view.updateOrdersWaiting(model.ordersWaiting())
        populateComboBoxes()
    }

    fun onAddOrder(customerName: String, foodOrdered: Food?, drinkOrdered: Drink?) {
        if(model.addOrder(customerName, foodOrdered, drinkOrdered) == null) {
            view.showNewOrder(model.orderQueue.first(), false)
        }
        else {
            view.showNewOrder(model.orderQueue.first(), true)
            view.showOrderQueue(model.orderQueue)
            view.updateOrdersWaiting(model.ordersWaiting())
            view.updateNextOrder(model.orderQueue)
        }

    }

    fun onServeOrder() {
        if(!model.orderQueue.isEmpty()) {
            view.showServedOrder(model.orderQueue.first())
            model.serveOrder(model.orderQueue)
            view.showOrderQueue(model.orderQueue)
            view.updateOrdersWaiting(model.ordersWaiting())
        }
        else {
            view.showInfo("No orders to serve")
        }
    }

    fun onCancelOrder(orderID: Int) {
        if(model.cancelOrder(orderID) == null) {
            view.showInfo("Please input a valid order ID")
        }
        else {
            model.cancelOrder(orderID)
        }
        view.showOrderQueue(model.orderQueue)
        view.updateOrdersWaiting(model.ordersWaiting())
        view.showCancelledOrder(model.orderQueue.find {it.id == orderID})
    }

    fun onPrioritizeOrder(orderID: Int) {
        if(model.priorOrder(orderID) == null) {
            view.showInfo("Please input a valid order ID")
        }
        else {
            model.priorOrder(orderID)
        }
        view.showOrderQueue(model.orderQueue)
        view.updateOrdersWaiting(model.ordersWaiting())
        view.showPriorOrder(model.orderQueue.find {it.id == orderID})
    }

    fun onCloseRegister() {
        model.closeRegister()
        view.showOrderQueue(model.orderQueue)
        view.updateOrdersWaiting(model.ordersWaiting())
        view.showInfo("Register closed. Session reset.")
    }

    fun populateComboBoxes() {
        view.populateComboBoxes(model.getEnumData(Drink.entries.toList()), model.getEnumData(Food.entries.toList()))
    }
}