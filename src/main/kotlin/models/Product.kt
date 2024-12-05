package ie.setu.models
/**
 * Represents a product in the system.
 *
 * @property productID Unique identifier for the product.
 * @property productName Name of the product.
 * @property memorySize The memory size of the product in gigabytes (GB).
 * @property price The price of the product in the relevant currency.
 * @property orderID The ID of the order associated with the product. Defaults to -1 if the product is not part of any order.
 *
 * @constructor Creates an instance of a product with the specified details.
 */
data class Product(
    var productID: Int,
    var productName: String,
    var memorySize: Int,
    var price :Double,
   var  orderID:Int )

