@file:Suppress("KDocUnresolvedReference")

package controllers
import ie.setu.models.Product
import persistence.Serializer
/**
 * API for managing products.
 *
 * @property serializer Serializer object for reading and writing product data.
 * @constructor Initializes ProductAPI with the specified serializer.
 */
class ProductAPI(serializerType: Serializer) {
    /**
     * Serializer object for data persistence.
     */
    private var serializer: Serializer = serializerType

    /**
     * List of all products.
     */
    internal var products = mutableListOf<Product>()
    private var idGen: Int = 0

    /**
     * Generates a new unique ID for a product.
     *
     * @return The next unique ID.
     */
    private fun idCreate() = idGen++

    /**
     * Change idGen if product were loaded
     *
     *
     */
    private fun updateIdGEn() {
        val maxID = products.maxOfOrNull { product -> product.productID } ?: -1
        idGen = maxID + 1
    }

    /**
     * Checks whether the product was created or not
     *
     *@return `true` if the product was  created, otherwise `false`.
     */
    fun validIdGen(): Boolean =
        idGen == 0

    /**
     * Loads products from the storage.
     *
     * @throws Exception If an error occurs during deserialization.
     */
    @Throws(Exception::class)
    fun load() {
        products = serializer.read() as ArrayList<Product>
        updateIdGEn()
    }

    /**
     * Saves products to the storage.
     *
     * @throws Exception If an error occurs during serialization.
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(products)
    }

    /**
     * Adds a new product to the list.
     *
     * @param product The product to add.
     * @return `true` if the product was added successfully, otherwise `false`.
     */
    fun addProduct(product: Product): Boolean {
        product.productID = idCreate()
        return products.add(product)
    }

    /**
     * Displays all products.
     *
     * @return A string representation of all stored products or a message if none exist.
     */
    fun showProduct() =
        if (products.isEmpty()) {
            "No product stored"
        } else {
            formatListString(products)
        }

    /**
     * Displays products that are not part of any order.
     *
     * @return A string representation of unassociated products or a message if none exist.
     */
    fun showNotOrderedProduct() =
        if (numberOfNotOrderedProduct() <= 0) {
            "No Inordered product"
        } else {
            formatListString(products.filter { product -> product.orderID == -1 })
        }

    /**
     * Displays products that are part of an order.
     *
     * @return A string representation of associated products or a message if none exist.
     */
    fun showOrderedProduct() =
        if (numberOfOrderedProduct() <= 0) {
            "No Ordered product"
        } else {
            formatListString(products.filter { product -> product.orderID != -1 })
        }

    /**
     * Deletes a product by its ID.
     *
     * @param findID The ID of the product to delete.
     * @return `true` if the product was deleted successfully, otherwise `false`.
     */
    fun deleteProduct(findID: Int): Boolean =

        products.removeIf { product -> product.productID == findID }

    /**
     * Updates a product's details by its ID.
     *
     * @param searchID The ID of the product to update.
     * @param productNameUpd The updated product name.
     * @param memorySizeUpd The updated memory size.
     * @param priceUpd The updated price.
     * @return `true` if the product was updated successfully, otherwise `false`.
     */
    fun updateProduct(seacrhID: Int, productNameUpd: String, memorySizeUpd: Int, priceUpd: Double): Boolean {
        var isUpd = false
        products.forEach { product ->
            if (seacrhID == product.productID) {
                product.productName = productNameUpd
                product.memorySize = memorySizeUpd
                product.price = priceUpd
                isUpd = true
            }
        }
        return isUpd
    }

    /**
     * Checks if there are any products that are not part of any order.
     *
     * @return `true` if unordered products exist, otherwise `false`.
     */

    fun checkOfNumberNotStoredProduct(): Boolean {
        if (numberOfNotOrderedProduct() <= 0) {
            println("no not ordered product")
            return false
        } else {
            return true
        }
    }

    /**
     * Checks if there are any products that are part of an order.
     *
     * @return `true` if ordered products exist, otherwise `false`.
     */
    fun checkOfNumberStoredProduct(): Boolean {
        if (numberOfOrderedProduct() <= 0) {
            println("no  ordered product")
            return false
        } else {
            return true
        }
    }

    /**
     * Checks if there are any products stored.
     *
     * @return `true` if products exist, otherwise `false`.
     */
    fun checkOfNumberAllProduct(): Boolean {
        if (numberOfProduct() <= 0) {
            println("No product stored")
            return false
        } else {
            return true
        }
    }

    /**
     * Counts the total number of products.
     *
     * @return The total number of products.
     */
    fun numberOfProduct(): Int = products.size

    /**
     * Counts the number of products that are not part of any order.
     *
     * @return The total number of unordered products.
     */
    fun numberOfNotOrderedProduct(): Int = products.count { product -> product.orderID == -1 }

    /**
     * Counts the number of products that are part of an order.
     *
     * @return The total number of ordered products.
     */
    fun numberOfOrderedProduct(): Int = products.count { product -> product.orderID != -1 }

    /**
     * Validates whether a given product ID exists.
     *
     * @param seacrhID The ID to validate.
     * @return `true` if the ID exists, otherwise `false`.
     */
    fun isValidID(seacrhID: Int): Boolean =
        products.any { product -> product.productID == seacrhID }

    /**
     * Validates whether a given product ID corresponds to an unordered product.
     *
     * @param seacrhID The ID to validate.
     * @return `true` if the ID corresponds to an unordered product, otherwise `false`.
     */
    fun isValidNotStoredProductID(seacrhID: Int): Boolean =
        products.any { product -> product.productID == seacrhID && product.orderID == -1 }

    /**
     * Validates whether a given product ID is valid.
     *
     * @param searchProductID The ID of the product to validate.
     * @return `true` if the product ID is valid, otherwise `false`.
     */
    fun checkIsValidProductID(searchProductID: Int): Boolean {
        if (!isValidID(searchProductID)) {
            println("Its not Valid  Product ID $searchProductID")
            return false
        } else {
            return true
        }
    }

    /**
     * Validates whether a given product ID corresponds to an unordered product.
     *
     * @param searchProductID The ID of the product to validate.
     * @return `true` if the product ID corresponds to an unordered product, otherwise `false`.
     */
    fun checkIsValidNotStoredProductID(searchProductID: Int): Boolean {
        if (!isValidNotStoredProductID(searchProductID)) {
            println("Its not Valid not ordered  Product ID $searchProductID")
            return false
        } else {
            return true
        }
    }

    /**
     * Deletes all products associated with a given order ID.
     *
     * @param orderIDSearch The ID of the order whose products are to be deleted.
     * @return `true` if any products were disassociated from the order, otherwise `false`.
     */
    fun deleteAllProductsFromOrder(orderIDSearch: Int): Boolean {
        var isDelete = false
        products.forEach { product ->
            if (orderIDSearch == product.orderID) {
                product.orderID = -1
                isDelete = true
            }
        }
        return isDelete
    }

    /**
     * Adds a product to an order by updating its order ID.
     *
     * @param productSeacrhID The ID of the product to add.
     * @param orderIDAddInProduct The ID of the order to associate the product with.
     * @return `true` if the product was successfully added, otherwise `false`.
     */
    fun addProductToOrder(productSeacrhID: Int, orderIDAddInProduct: Int): Boolean {
        val productIsADD = products.find { product -> product.productID == productSeacrhID }
        return if (productIsADD!!.orderID != -1) {
            println("This product already exists")
            false
        } else {
            productIsADD.orderID = orderIDAddInProduct
            true
        }
    }

    /**
     * Displays all products associated with a given order ID.
     *
     * @param seacrhOrderID The ID of the order.
     * @return A string representation of products in the specified order or a message if none exist.
     */
    fun showProductByOrder(seacrhOrderID: Int): String {
        val isValidOrder = products.filter { product -> product.orderID == seacrhOrderID }
        return if (isValidOrder.isEmpty()) {
            "No stored product in order with id $seacrhOrderID"
        } else {
            isValidOrder.joinToString(separator = "\n") { product ->
                "  " + products.indexOf(product).toString() + ": " + product.toString()
            }
        }
    }

    /**
     * Calculates the total sum of product prices in a specific order.
     *
     * @param seacrhOrderID The ID of the order.
     * @return A string representation of the total sum of product prices in the order.
     */
    fun countProductSumPriceInOrder(seacrhOrderID: Int): String {
        var sum = 0.0
        searchByCriteria("orderid", seacrhOrderID).forEach { product ->
            sum += product.price
        }
        return "Sum of product price by order with id $seacrhOrderID = $sum"
    }

    /**
     * Checks if an order contains any products.
     *
     * @param searchOrderID The ID of the order to check.
     * @return `true` if the order contains products, otherwise `false`.
     */
    fun checkIsOrderHasProducts(searchOrderID: Int): Boolean {
        val result = products.any { product -> product.orderID == searchOrderID }
        if (!result) {
            println("Its not Ordered  Product in order with ID $searchOrderID")
            return false
        } else {
            return true
        }
    }

    /**
     * Removes a specific product from a specific order.
     *
     * @param orderIDSearch The ID of the order.
     * @param productSeacrhID The ID of the product to remove.
     * @return `true` if the product was successfully removed, otherwise `false`.
     */
    fun deleteChosenProductsFromOrder(orderIDSearch: Int, productSeacrhID: Int): Boolean {
        val productIsADD = products.find { product -> product.productID == productSeacrhID }
        return if (productIsADD!!.orderID != -1 && productIsADD.orderID == orderIDSearch) {
            productIsADD.orderID = -1
            true
        } else {
            println("This product not in this order with id $orderIDSearch")
            false
        }
    }

    /**
     * Sorts products by the specified category.
     *
     * @param category The category to sort by. Supported categories: "id", "name", "memory", "price".
     * Defaults to sorting by "id" if the category is unsupported.
     */
    fun sortProductByCategory(category: String) =
        when (category) {
            "id" -> products.sortBy { product -> product.productID }
            "name" -> products.sortBy { product -> product.productName }
            "memory" -> products.sortBy { product -> product.memorySize }
            "price" -> products.sortBy { product -> product.price }
            else -> products.sortBy { product -> product.productID }
        }

    /**
     * Searches for products based on a specified criterion and search element.
     *
     * @param criterion The criterion to search by (e.g., "id", "name", "memory", "price", "orderid").
     * @param searchElement The value to match against the criterion.
     * @return A list of products that match the specified criterion and value.
     */
    fun searchByCriteria(criterion: String, searchElement: Any): List<Product> =
        products.filter { product ->

            when (criterion) {
                "id" -> (searchElement as? Int)?.let { product.productID == searchElement } ?: false // search by id
                "name" -> (searchElement as? String)?.let { product.productName.lowercase() == searchElement.lowercase() } ?: false
                "memory" -> (searchElement as? Int)?.let { product.memorySize == searchElement } ?: false
                "price" -> (searchElement as? Double)?.let { product.price == searchElement } ?: false
                "orderid" -> (searchElement as? Int)?.let { product.orderID == searchElement } ?: false
                else -> false
            }
        }

    /**
     * Displays products that match a specific criterion and search element.
     *
     * @param criterion The criterion to search by (e.g., "id", "name", "memory", "price", "orderid").
     * @param searchElement The value to match against the criterion.
     * @return A string representation of the matching products or a message if none are found.
     */
    fun showByCriteria(criterion: String, searchElement: Any): String =

        if (searchByCriteria(criterion, searchElement).isEmpty()) {
            "no product with $searchElement element in $criterion"
        } else {
            formatListString(searchByCriteria(criterion, searchElement))
        }

    /**
     * Counts the number of products in stock with the specified name.
     *
     * @param searchName The name of the product to count.
     * @return The number of products in stock with the specified name.
     */
    fun countIsInStockByName(searchName: String): Int =

        searchByCriteria("name", searchName).stream()
            .filter { product -> product.orderID == -1 }
            .count()
            .toInt()

    /**
     * Checks if products with the specified name are in stock.
     *
     * @param searchName The name of the product to check.
     * @return A string representation of the products in stock or a message if none exist.
     */
    fun checkIsStock(searchName: String): String {
        return if (countIsInStockByName(searchName) > 0) {
            formatListString(searchByCriteria("name", searchName).filter { product -> product.orderID == -1 })
        } else {
            "No product in stock with name $searchName"
        }
    }

    /**
     * Formats a list of products into a string representation.
     *
     * @param productToFormat The list of products to format.
     * @return A formatted string representation of the products.
     */

    private fun formatListString(productToFormat: List<Product>): String =
        productToFormat
            .joinToString(separator = "\n\n") { product ->
                "Product ID = ${product.productID} " + "Info: " + product.toString()
            }
}
