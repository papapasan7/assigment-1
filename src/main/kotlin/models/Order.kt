package models
/**
 * Represents an order in the system.
 *
 * @property orderID Unique identifier for the order.
 * @property customerName Name of the customer associated with the order.
 * @property isActive Indicates whether the order is active.
 *
 * @constructor Creates an instance of an order with the specified details.
 */
data class Order(
    var orderID: Int,
    var customerName: String,
    var isActive: Boolean
)
