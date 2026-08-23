package ca.qc.cegepheritage.g30a2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.LinkedList

class CafeModelTest {
    val cafeModel = CafeModel(queuedOrders = listOf(
        Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN),
        Order(2, "Ella", Drink.CATPUCCINO, null),
        Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL),
        Order(4, "Spencer", null, Food.CHEESY_CHICKEN_WRAP),
        Order(5, "Julia", Drink.PURRFECT_LATTE, Food.CATNIP_COOKIE)
    ))

    // Instantiation

    @Test
    fun testInstantiateModelReturnsAllNeededAttributes() {
        val model = CafeModel()
        val newQueue: List<Order> = emptyList()
        assertEquals(newQueue, model.orderQueue)
    }

    // Add

    @Test
    fun testAddOrderWithNoOrdersAddsNewOrderToList() {
        val model = CafeModel(queuedOrders = emptyList())
        assertEquals(LinkedList<Order>(emptyList()), model.orderQueue)
        model.addOrder("Ella", Food.CHEESY_CHICKEN_WRAP, Drink.CATPUCCINO, 1)
        assertEquals(listOf(Order(1, "Ella", Drink.CATPUCCINO, Food.CHEESY_CHICKEN_WRAP)), model.orderQueue)
    }

    @Test
    fun testAddOrderWithOrdersInListAddsNewOrderToEndOfList() {
        cafeModel.addOrder("Ella", Food.CHEESY_CHICKEN_WRAP, Drink.CATPUCCINO, 6)
        val expectedList = listOf(
            Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN),
            Order(2, "Ella", Drink.CATPUCCINO, null),
            Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL),
            Order(4, "Spencer", null, Food.CHEESY_CHICKEN_WRAP),
            Order(5, "Julia", Drink.PURRFECT_LATTE, Food.CATNIP_COOKIE),
            Order(6, "Ella", Drink.CATPUCCINO, Food.CHEESY_CHICKEN_WRAP)
        )
        assertEquals(expectedList, cafeModel.orderQueue)
    }

    @Test
    fun testAddOrderToListWithOrdersReturnsOrder() {
        val newOrder = Order(6, "Ella", Drink.CATPUCCINO, Food.CHEESY_CHICKEN_WRAP)
        assertEquals(newOrder, cafeModel.addOrder("Ella", Food.CHEESY_CHICKEN_WRAP, Drink.CATPUCCINO, 6))
    }

    @Test
    fun testAddOrderWithNoFoodAndDrinkDoesNotAddToList() {
        cafeModel.addOrder("Ella", null, null, 6)
        val expectedList = listOf(
            Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN),
            Order(2, "Ella", Drink.CATPUCCINO, null),
            Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL),
            Order(4, "Spencer", null, Food.CHEESY_CHICKEN_WRAP),
            Order(5, "Julia", Drink.PURRFECT_LATTE, Food.CATNIP_COOKIE)
        )
        assertEquals(expectedList, cafeModel.orderQueue)
    }

    @Test
    fun testAddOrderWithNoFoodAndDrinkAndReturnsNull() {
        assertEquals(null, cafeModel.addOrder("Ella", null, null, 6))
    }

    @Test
    fun testAddOrderWithNoNameDoesNotAddToList() {
        cafeModel.addOrder("", Food.CHEESY_CHICKEN_WRAP, Drink.CATPUCCINO, 6)
        val expectedList = listOf(
            Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN),
            Order(2, "Ella", Drink.CATPUCCINO, null),
            Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL),
            Order(4, "Spencer", null, Food.CHEESY_CHICKEN_WRAP),
            Order(5, "Julia", Drink.PURRFECT_LATTE, Food.CATNIP_COOKIE)
        )
        assertEquals(expectedList, cafeModel.orderQueue)
    }

    @Test
    fun testAddOrderWithNoNameReturnsNull() {
        assertEquals(null, cafeModel.addOrder("", Food.CHEESY_CHICKEN_WRAP, Drink.CATPUCCINO, 6))
    }

    @Test
    fun testAddOrderWithNoDrinkAddsToList() {
        cafeModel.addOrder("Ella", Food.CHEESY_CHICKEN_WRAP, null, 6)
        val expectedList = listOf(
            Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN),
            Order(2, "Ella", Drink.CATPUCCINO, null),
            Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL),
            Order(4, "Spencer", null, Food.CHEESY_CHICKEN_WRAP),
            Order(5, "Julia", Drink.PURRFECT_LATTE, Food.CATNIP_COOKIE),
            Order(6, "Ella", null, Food.CHEESY_CHICKEN_WRAP)
        )
        assertEquals(expectedList, cafeModel.orderQueue)
    }

    @Test
    fun testAddOrderWithNoDrinkReturnsOrder() {
        val newOrder = Order(6, "Ella", null, Food.CHEESY_CHICKEN_WRAP)
        assertEquals(newOrder, cafeModel.addOrder("Ella", Food.CHEESY_CHICKEN_WRAP, null, 6))
    }

    @Test
    fun testAddOrderWithNoFoodAddsToList() {
        cafeModel.addOrder("Ella", null, Drink.CATPUCCINO, 6)
        val expectedList = listOf(
            Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN),
            Order(2, "Ella", Drink.CATPUCCINO, null),
            Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL),
            Order(4, "Spencer", null, Food.CHEESY_CHICKEN_WRAP),
            Order(5, "Julia", Drink.PURRFECT_LATTE, Food.CATNIP_COOKIE),
            Order(6, "Ella", Drink.CATPUCCINO, null)
        )
        assertEquals(expectedList, cafeModel.orderQueue)
    }

    @Test
    fun testAddOrderWithNoFoodReturnsOrder() {
        val newOrder = Order(6, "Ella", Drink.CATPUCCINO, null)
        assertEquals(newOrder, cafeModel.addOrder("Ella", null, Drink.CATPUCCINO, 6))
    }

    // Serve

    @Test
    fun testServeOrderWithOrdersRemovesFirstOrderFromList() {
        val newQueue: List<Order> = listOf(
            Order(2, "Ella", Drink.CATPUCCINO, null),
            Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL),
            Order(4, "Spencer", null, Food.CHEESY_CHICKEN_WRAP),
            Order(5, "Julia", Drink.PURRFECT_LATTE, Food.CATNIP_COOKIE)
        )
        cafeModel.serveOrder(cafeModel.orderQueue)
        assertEquals(newQueue, cafeModel.orderQueue)
    }

    @Test
    fun testServeOrderWithOneOrderRemovesOrderFromList() {
        val model = CafeModel(queuedOrders = listOf(Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN)))
        val newQueue: List<Order> = emptyList()
        model.serveOrder(model.orderQueue)
        assertEquals(newQueue, model.orderQueue)
    }

    @Test
    fun testServeOrderNoOrdersDoesNotUpdateList() {
        val model = CafeModel(queuedOrders = emptyList())
        assertEquals(LinkedList<Order>(emptyList()), model.orderQueue)
        val newQueue: List<Order> = emptyList()
        model.serveOrder(model.orderQueue)
        assertEquals(newQueue, model.orderQueue)
    }

    // Cancel

    @Test
    fun testCancelOrderWithOrdersRemovesOrderWithCorrespondingOrderID() {
        val newQueue: List<Order> = listOf(
            Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN),
            Order(2, "Ella", Drink.CATPUCCINO, null),
            Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL),
            Order(5, "Julia", Drink.PURRFECT_LATTE, Food.CATNIP_COOKIE)
        )
        cafeModel.cancelOrder(4)
        assertEquals(newQueue, cafeModel.orderQueue)
    }

    @Test
    fun testCancelOrderWithOrdersAndNoOrderIDDoesNotUpdateList() {
        val newQueue: List<Order> = listOf(
            Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN),
            Order(2, "Ella", Drink.CATPUCCINO, null),
            Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL),
            Order(4, "Spencer", null, Food.CHEESY_CHICKEN_WRAP),
            Order(5, "Julia", Drink.PURRFECT_LATTE, Food.CATNIP_COOKIE)
        )
        cafeModel.cancelOrder(null)
        assertEquals(newQueue, cafeModel.orderQueue)
    }

    @Test
    fun testCancelOrderWithOneOrderRemovesOrderFromList() {
        val model = CafeModel(queuedOrders = listOf(Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN)))
        val newQueue: List<Order> = emptyList()
        model.cancelOrder(1)
        assertEquals(newQueue, model.orderQueue)
    }

    @Test
    fun testCancelOrderWithOrdersAndNonExistentOrderIDDoesNotUpdateList() {
        val newQueue: List<Order> = listOf(
            Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN),
            Order(2, "Ella", Drink.CATPUCCINO, null),
            Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL),
            Order(4, "Spencer", null, Food.CHEESY_CHICKEN_WRAP),
            Order(5, "Julia", Drink.PURRFECT_LATTE, Food.CATNIP_COOKIE)
        )
        cafeModel.cancelOrder(8)
        assertEquals(newQueue, cafeModel.orderQueue)
    }

    @Test
    fun testCancelOrderWithNoOrdersDoesNotUpdateList() {
        val model = CafeModel(queuedOrders = emptyList())
        assertEquals(LinkedList<Order>(emptyList()), model.orderQueue)
        val newQueue: List<Order> = emptyList()
        model.cancelOrder(1)
        assertEquals(newQueue, model.orderQueue)
    }

    // prior order

    @Test
    fun testPrioritizeOrderWithOrdersInListBringsOrderWithCorrespondingOrderIDToFront() {
        val newQueue: List<Order> = listOf(
            Order(4, "Spencer", null, Food.CHEESY_CHICKEN_WRAP),
            Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN),
            Order(2, "Ella", Drink.CATPUCCINO, null),
            Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL),
            Order(5, "Julia", Drink.PURRFECT_LATTE, Food.CATNIP_COOKIE)
        )
        cafeModel.priorOrder(4)
        assertEquals(newQueue, cafeModel.orderQueue)
    }

    @Test
    fun testPrioritizeOrderWithOrdersInListAndNonExistentOrderIDDoesNotUpdateList() {
        val newQueue: List<Order> = listOf(
            Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN),
            Order(2, "Ella", Drink.CATPUCCINO, null),
            Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL),
            Order(4, "Spencer", null, Food.CHEESY_CHICKEN_WRAP),
            Order(5, "Julia", Drink.PURRFECT_LATTE, Food.CATNIP_COOKIE)
        )
        cafeModel.priorOrder(8)
        assertEquals(newQueue, cafeModel.orderQueue)
    }

    @Test
    fun testPrioritizeOrderWithOrdersInListAndNoOrderIDDoesNotUpdateList() {
        val newQueue: List<Order> = listOf(
            Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN),
            Order(2, "Ella", Drink.CATPUCCINO, null),
            Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL),
            Order(4, "Spencer", null, Food.CHEESY_CHICKEN_WRAP),
            Order(5, "Julia", Drink.PURRFECT_LATTE, Food.CATNIP_COOKIE)
        )
        cafeModel.priorOrder(null)
        assertEquals(newQueue, cafeModel.orderQueue)
    }

    @Test
    fun testPrioritizeOrderWithOrdersInListAndOrderIDOfOneDoesNotUpdateList() {
        val newQueue: List<Order> = listOf(
            Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN),
            Order(2, "Ella", Drink.CATPUCCINO, null),
            Order(3, "Paula", Drink.MEOWCHA, Food.SALMON_BAGEL),
            Order(4, "Spencer", null, Food.CHEESY_CHICKEN_WRAP),
            Order(5, "Julia", Drink.PURRFECT_LATTE, Food.CATNIP_COOKIE)
        )
        cafeModel.priorOrder(1)
        assertEquals(newQueue, cafeModel.orderQueue)
    }

    @Test
    fun testPrioritizeOrderWithOneOrderDoesNotChangeList() {
        val model = CafeModel(queuedOrders = listOf(Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN)))
        val newQueue: List<Order> = listOf(Order(1, "Bana", Drink.AMEOWICANO, Food.PUMPKIN_MUFFIN))
        model.priorOrder(1)
        assertEquals(newQueue, model.orderQueue)
    }

    @Test
    fun testPrioritizeOrderWithNoOrdersInListDoesNotUpdateList() {
        val model = CafeModel(queuedOrders = emptyList())
        assertEquals(LinkedList<Order>(emptyList()), model.orderQueue)
        val newQueue: List<Order> = emptyList()
        model.priorOrder(1)
        assertEquals(newQueue, model.orderQueue)
    }

    // Close Register

    @Test
    fun testCloseRegisterDeletesAllOrdersFromQueue() {
        val newQueue: List<Order> = emptyList()
        cafeModel.closeRegister()
        assertEquals(newQueue, cafeModel.orderQueue)
    }
}