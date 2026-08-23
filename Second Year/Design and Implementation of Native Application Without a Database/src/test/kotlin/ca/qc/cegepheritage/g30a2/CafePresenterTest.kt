package ca.qc.cegepheritage.g30a2

import javafx.collections.ObservableList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.LinkedList

class CafePresenterTest {
    val cafeDisplay = FakeDisplay()
    val presenter = CafePresenter(CafeModel(), cafeDisplay)
    @Test
    fun testStartRendersOrderQueueAsEmpty() {
        presenter.start()
        assertEquals(0, cafeDisplay.ordersWaiting)
        assertEquals(emptyList<Order>(), cafeDisplay.queuedOrders)
    }

    @Test
    fun testOnAddOrderAddsAndUpdatesDisplay() {
        presenter.onAddOrder("Ella", Food.CATNIP_COOKIE, Drink.CATPUCCINO)
        assertEquals(listOf(Order(1, "Ella", Drink.CATPUCCINO, Food.CATNIP_COOKIE)), cafeDisplay.queuedOrders)
        assertEquals(1, cafeDisplay.ordersWaiting)
        assertEquals(Order(1, "Ella", Drink.CATPUCCINO, Food.CATNIP_COOKIE), cafeDisplay.newestOrder?.first)
        assertEquals(true, cafeDisplay.newestOrder?.second)
    }

    @Test
    fun testOnServeOrderWithOneOrderRemovesOrderAndUpdatesDisplay() {
        presenter.onAddOrder("Ella", Food.CATNIP_COOKIE, Drink.CATPUCCINO)
        assertEquals(listOf(Order(1, "Ella", Drink.CATPUCCINO, Food.CATNIP_COOKIE)), cafeDisplay.queuedOrders)
        assertEquals(1, cafeDisplay.ordersWaiting)
        presenter.onServeOrder()
        assertEquals(emptyList<Order>(), cafeDisplay.queuedOrders)
        assertEquals(0, cafeDisplay.ordersWaiting)
    }

    @Test
    fun testOnServeOrderWithMultipleOrdersRemovesFirstOrderAndUpdatesDisplay() {
        presenter.onAddOrder("Bana", Food.CATNIP_COOKIE, Drink.CATPUCCINO)
        presenter.onAddOrder("Ella", null, Drink.CATPUCCINO)
        presenter.onAddOrder("Paula", Food.SALMON_BAGEL, Drink.MEOWCHA)
        assertEquals(listOf(Order(1, "Bana", Drink.CATPUCCINO, Food.CATNIP_COOKIE), Order(2, "Ella", Drink.CATPUCCINO, null), Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL)), cafeDisplay.queuedOrders)
        assertEquals(3, cafeDisplay.ordersWaiting)
        presenter.onServeOrder()
        assertEquals(listOf(Order(2, "Ella", Drink.CATPUCCINO, null), Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL)), cafeDisplay.queuedOrders)
        assertEquals(2, cafeDisplay.ordersWaiting)
    }

    @Test
    fun testOnCancelOrderWithOneOrderRemovesOrderAndUpdatesDisplay() {
        presenter.onAddOrder("Ella", Food.CATNIP_COOKIE, Drink.CATPUCCINO)
        assertEquals(listOf(Order(1, "Ella", Drink.CATPUCCINO, Food.CATNIP_COOKIE)), cafeDisplay.queuedOrders)
        assertEquals(1, cafeDisplay.ordersWaiting)
        presenter.onCancelOrder(1)
        assertEquals(emptyList<Order>(), cafeDisplay.queuedOrders)
        assertEquals(0, cafeDisplay.ordersWaiting)
    }

    @Test
    fun testOnCancelOrderWithMultipleOrdersRemovesFirstOrderAndUpdatesDisplay() {
        presenter.onAddOrder("Bana", Food.CATNIP_COOKIE, Drink.CATPUCCINO)
        presenter.onAddOrder("Ella", null, Drink.CATPUCCINO)
        presenter.onAddOrder("Paula", Food.SALMON_BAGEL, Drink.MEOWCHA)
        assertEquals(listOf(Order(1, "Bana", Drink.CATPUCCINO, Food.CATNIP_COOKIE), Order(2, "Ella", Drink.CATPUCCINO, null), Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL)), cafeDisplay.queuedOrders)
        assertEquals(3, cafeDisplay.ordersWaiting)
        presenter.onCancelOrder(1)
        assertEquals(listOf(Order(2, "Ella", Drink.CATPUCCINO, null), Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL)), cafeDisplay.queuedOrders)
        assertEquals(2, cafeDisplay.ordersWaiting)
    }

    @Test
    fun testOnPrioritizeOrderWithOneOrderDisplayRemainsTheSame() {
        presenter.onAddOrder("Ella", Food.CATNIP_COOKIE, Drink.CATPUCCINO)
        assertEquals(listOf(Order(1, "Ella", Drink.CATPUCCINO, Food.CATNIP_COOKIE)), cafeDisplay.queuedOrders)
        assertEquals(1, cafeDisplay.ordersWaiting)
        presenter.onPrioritizeOrder(1)
        assertEquals(listOf(Order(1, "Ella", Drink.CATPUCCINO, Food.CATNIP_COOKIE)), cafeDisplay.queuedOrders)
        assertEquals(1, cafeDisplay.ordersWaiting)
    }

    @Test
    fun testOnPrioritizeOrderWithMultipleOrdersBringsOrderToFrontAndUpdatesDisplay() {
        presenter.onAddOrder("Bana", Food.CATNIP_COOKIE, Drink.CATPUCCINO)
        presenter.onAddOrder("Ella", null, Drink.CATPUCCINO)
        presenter.onAddOrder("Paula", Food.SALMON_BAGEL, Drink.MEOWCHA)
        assertEquals(listOf(Order(1, "Bana", Drink.CATPUCCINO, Food.CATNIP_COOKIE), Order(2, "Ella", Drink.CATPUCCINO, null), Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL)), cafeDisplay.queuedOrders)
        assertEquals(3, cafeDisplay.ordersWaiting)
        presenter.onPrioritizeOrder(2)
        assertEquals(listOf(Order(2, "Ella", Drink.CATPUCCINO, null), Order(1, "Bana", Drink.CATPUCCINO, Food.CATNIP_COOKIE), Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL)), cafeDisplay.queuedOrders)
        assertEquals(3, cafeDisplay.ordersWaiting)
    }

    @Test
    fun testOnCloseRegisterWithMultipleOrdersUpdatesDisplay() {
        presenter.onAddOrder("Bana", Food.CATNIP_COOKIE, Drink.CATPUCCINO)
        presenter.onAddOrder("Ella", null, Drink.CATPUCCINO)
        presenter.onAddOrder("Paula", Food.SALMON_BAGEL, Drink.MEOWCHA)
        assertEquals(listOf(Order(1, "Bana", Drink.CATPUCCINO, Food.CATNIP_COOKIE), Order(2, "Ella", Drink.CATPUCCINO, null), Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL)), cafeDisplay.queuedOrders)
        assertEquals(3, cafeDisplay.ordersWaiting)
        presenter.onCloseRegister()
        assertEquals(emptyList<Order>(), cafeDisplay.queuedOrders)
        assertEquals(0, cafeDisplay.ordersWaiting)
    }

    @Test
    fun testOnCloseRegisterWithNoOrdersDisplayRemainsTheSame() {
        assertEquals(emptyList<Order>(), cafeDisplay.queuedOrders)
        assertEquals(0, cafeDisplay.ordersWaiting)
        presenter.onCloseRegister()
        assertEquals(emptyList<Order>(), cafeDisplay.queuedOrders)
        assertEquals(0, cafeDisplay.ordersWaiting)
    }
}

class FakeDisplay: CafeDisplay {
    var queuedOrders: LinkedList<Order> = LinkedList<Order>(emptyList())
    var ordersWaiting: Int = 0
    var newestOrder: Pair<Order, Boolean>? = null
    var newestServed: Order? = null
    var newestCancelled: Order? = null
    var newestPrior: Order? = null
    var infoText: String = ""

    override fun showOrderQueue(orderQueue: LinkedList<Order>) {
       queuedOrders = orderQueue
    }

    override fun updateOrdersWaiting(ordersWaiting: Int) {
        this.ordersWaiting = ordersWaiting
    }

    override fun updateNextOrder(orderQueue: LinkedList<Order>) {
        queuedOrders = orderQueue
    }

    override fun showNewOrder(order: Order, wasAdded: Boolean) {
        newestOrder = order to wasAdded
        showInfo(newestOrder.toString())
    }

    override fun showServedOrder(order: Order?) {
        newestServed = order
        showInfo(newestServed.toString())
    }

    override fun showCancelledOrder(order: Order?) {
        newestCancelled = order
        showInfo(newestCancelled.toString())
    }

    override fun showPriorOrder(order: Order?) {
        newestPrior = order
        showInfo(newestPrior.toString())
    }

    override fun showInfo(msg: String) {
        infoText = msg
    }

    override fun populateComboBoxes(drinkData: ObservableList<Drink>, foodData: ObservableList<Food>) {
    }

}