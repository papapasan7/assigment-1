@file:Suppress("KDocUnresolvedReference")

package controllers
import models.Order
import persistence.Serializer

/**
 * API for managing orders.
 *
 * @property productAPI Reference to the Product API for managing associated products.
 * @property serializer Serializer object for reading and writing data.
 * @constructor Initializes OrderAPI with the specified ProductAPI and Serializer.
 */
class OrderAPI(private val productAPI: ProductAPI, serializerType: Serializer) {

    /**
     * List of all orders.
     */
    private var orders = mutableListOf<Order>()
    private var serializer: Serializer = serializerType

    /**
     * Generator for unique order IDs.
     */
    private var idGen: Int = 0

    /**
     * Generates a new unique ID for an order.
     *
     * @return The next unique ID.
     */
    private fun idCreate() = idGen++

    /**
     * Loads orders from the storage.
     *
     * @throws Exception If an error occurs during deserialization.
     */

    @Throws(Exception::class)
    fun load() {
        orders = serializer.read() as ArrayList<Order>
    }

    /**
     * Saves orders to the storage.
     *
     * @throws Exception If an error occurs during serialization.
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(orders)
    }

    /**
     * Adds a new order to the list.
     *
     * @param order [Order] The order to be added.
     * @return `true` if the order was successfully added, otherwise `false`.
     */

    fun addOrder(order: Order): Boolean {
        order.orderID = idCreate()
        return orders.add(order)
    }

    /**
     * Displays all orders.
     *
     * @return A string representation of the orders or a message if no orders exist.
     */

    fun showOrder() =
        if (orders.isEmpty()) {
            "No Orders stored"
        } else {
            formatListString(orders)
        }

    /**
     * Displays all active orders.
     *
     * @return A string representation of active orders or a message if no active orders exist.
     */
    fun showOrderActive() =
        if (numberOfActiveOrder() <= 0) {
            "No Active Orders stored"
        } else {
            formatListString(orders.filter { order -> order.isActive })
        }

    /**
     * Displays all inactive orders.
     *
     * @return A string representation of inactive orders or a message if no inactive orders exist.
     */
    fun showOrderInactive() =
        if (numberOfInactiveOrder() <= 0) {
            "No Inactive Orders stored"
        } else {
            formatListString(orders.filter { order -> !order.isActive })
        }

    /**
     * Deletes an order based on its ID.
     *
     * @param findOrderID The ID of the order to be deleted.
     * @return `true` if the order was successfully deleted, otherwise `false`.
     */

    fun deleteOrder(findOrderID: Int): Boolean =

        if (orders.removeIf { order -> order.orderID == findOrderID }) {
            productAPI.deleteAllProductsFromOrder(findOrderID)
            true
        } else {
            false
        }

    /**
     * Updates the customer name of an order by its ID.
     *
     * @param searchOrderID The ID of the order to be updated.
     * @param customerNameUpd The new customer name.
     * @return `true` if the order was updated successfully, otherwise `false`.
     */

    @Suppress("KDocUnresolvedReference")
    fun updateOrder(seacrhOrderID: Int, customerNameUpd: String): Boolean {
        var isUpd = false
        orders.forEach { order ->
            if (seacrhOrderID == order.orderID) {
                order.customerName = customerNameUpd

                isUpd = true
            }
        }
        return isUpd
    }

    /**
     * Gets the total number of orders.
     *
     * @return The number of orders.
     */

    fun numberOfOrder(): Int = orders.size

    /**
     * Gets the total number of active orders.
     *
     * @return The number of active orders.
     */
    fun numberOfActiveOrder(): Int = orders.count { order -> order.isActive }

    /**
     * Gets the total number of inactive orders.
     *
     * @return The number of inactive orders.
     */
    fun numberOfInactiveOrder(): Int = orders.count { order -> !order.isActive }

    /**
     * Checks if there are any active orders stored.
     *
     * @return `true` if active orders exist, otherwise `false`.
     */

    fun checkOfNumberActiveOrder(): Boolean {
        if (numberOfActiveOrder() <= 0) {
            println("No active order stored")
            return false
        } else {
            return true
        }
    }

    /**
     * Checks if there are any inactive orders stored.
     *
     * @return `true` if inactive orders exist, otherwise `false`.
     */

    fun checkOfNumberInactiveOrder(): Boolean {
        if (numberOfInactiveOrder() <= 0) {
            println("No Inactive order stored")
            return false
        } else {
            return true
        }
    }

    /**
     * Checks if there are any orders stored.
     *
     * @return `true` if orders exist, otherwise `false`.
     */
    fun checkAreNumberOrder(): Boolean {
        if (numberOfOrder() <= 0) {
            println("No order stored")
            return false
        } else {
            return true
        }
    }

    /**
     * Validates if a given order ID exists.
     *
     * @param searchOrderID The ID to validate.
     * @return `true` if the ID exists, otherwise `false`.
     */
    fun isValidID(searchOrderID: Int): Boolean =
        orders.any { order -> order.orderID == searchOrderID }

    /**
     * Validates if a given order ID corresponds to an active order.
     *
     * @param searchOrderID The ID to validate.
     * @return `true` if the ID corresponds to an active order, otherwise `false`.
     */
    fun isValidActiveID(searchOrderID: Int): Boolean =
        orders.any { order -> order.orderID == searchOrderID && order.isActive }

    /**
     * Validates if a given order ID corresponds to an inactive order.
     *
     * @param searchOrderID The ID to validate.
     * @return `true` if the ID corresponds to an inactive order, otherwise `false`.
     */
    fun isValidInactiveID(searchOrderID: Int): Boolean =
        orders.any { order -> order.orderID == searchOrderID && !order.isActive }

    /**
     * Checks if the given ID corresponds to a valid active order.
     *
     * @param searchOrderID The ID of the order to check.
     * @return `true` if the ID corresponds to a valid active order, otherwise `false`.
     */
    fun checkIsValidActiveID(searchOrderID: Int): Boolean {
        if (!isValidActiveID(searchOrderID)) {
            println("Its Valid Active Order ID $searchOrderID")
            return false
        } else {
            return true
        }
    }

    /**
     * Checks if the given ID corresponds to a valid inactive order.
     *
     * @param searchOrderID The ID of the order to check.
     * @return `true` if the ID corresponds to a valid inactive order, otherwise `false`.
     */
    fun checkIsValidInactiveID(searchOrderID: Int): Boolean {
        if (!isValidInactiveID(searchOrderID)) {
            println("Its Valid Inactive Order ID $searchOrderID")
            return false
        } else {
            return true
        }
    }

    /**
     * Checks if the given ID corresponds to a valid order.
     *
     * @param searchOrderID The ID of the order to check.
     * @return `true` if the ID corresponds to a valid order, otherwise `false`.
     */
    fun checkIsOrderValidID(searchOrderID: Int): Boolean {
        if (!isValidID(searchOrderID)) {
            println("Its Valid Order ID $searchOrderID")
            return false
        } else {
            return true
        }
    }

    /**
     * Sorts the orders by the specified category.
     *
     * @param category The category to sort by. Supported categories: "id", "name".
     * Defaults to sorting by "id" if the category is unsupported.
     */
    fun sortOrderByCategory(category: String) =

        when (category) {
            "id" -> orders.sortBy { order -> order.orderID }
            "name" -> orders.sortBy { order -> order.customerName }
            else -> orders.sortBy { order -> order.orderID }
        }

    /**
     * Searches for orders based on a specified criterion and search element.
     *
     * @param criterion The criterion to search by (e.g., "id", "name").
     * @param searchElement The value to match against the criterion.
     * @return A list of orders that match the given criterion and value.
     */

    fun searchByCriteria(criterion: String, searchElement: Any): List<Order> =
        orders.filter { order ->
            when (criterion.lowercase()) {
                "id" -> (searchElement as? Int)?.let { order.orderID == searchElement } ?: false // search by id
                "name" -> (searchElement as? String)?.let { order.customerName.lowercase() == searchElement.lowercase() } ?: false
                else -> false
            }
        }

    /**
     * Displays orders that match a specific criterion and value.
     *
     * @param criterion The criterion to search by (e.g., "id", "name").
     * @param searchElement The value to match against the criterion.
     * @return A string representation of the matching orders or a message if none are found.
     */
    fun showByCriteria(criterion: String, searchElement: Any): String =

        if (searchByCriteria(criterion, searchElement).isEmpty()) {
            "no order with $searchElement element in $criterion"
        } else {
            formatListString(searchByCriteria(criterion, searchElement))
        }

    /**
     * Formats a list of orders into a string for display.
     *
     * @param ordersToFormat The list of orders to format.
     * @return A formatted string representing the list of orders and their associated products.
     */
    private fun formatListString(orderToFormat: List<Order>): String =
        orderToFormat.joinToString(separator = "\n\n") { order ->
            val productFind = productAPI.products.filter { product -> product.orderID == order.orderID }
            if (productFind.isEmpty()) {
                orderToFormat.indexOf(order).toString() + ": " + order.toString() + "\n No products in this order"
            } else {
                orderToFormat.indexOf(order).toString() + ": " + order.toString() + "\n product of ${order.customerName} \n" +
                    productFind.joinToString(separator = "\n") { product ->
                        "    " + productFind.indexOf(product).toString() + ": " + product.toString()
                    }
            }
        }

    /**
     * Toggles the active status of an order by its ID.
     *
     * @param searchOrderID The ID of the order to update.
     * @return `true` if the status was successfully toggled, otherwise `false`.
     */
    fun switchActiveStatus(searchOrderID: Int): Boolean {
        var isUpd = false
        orders.forEach { order ->
            if (searchOrderID == order.orderID) {
                order.isActive = !order.isActive

                isUpd = true
            }
        }
        return isUpd
    }
}
