import ie.setu.controllers.OrderAPI
import ie.setu.controllers.ProductAPI
import ie.setu.models.Order
import ie.setu.models.Product
import java.io.File
import java.lang.System.exit
import persistence.XMLSerializer
import utils.*

import java.lang.System.exit

private val ProductAPI = ProductAPI(XMLSerializer(File("product.xml")))
private val OrderAPI = OrderAPI(ProductAPI,XMLSerializer(File("orders.xml")))

/**
 * Entry point of the Storage Control Application.
 * Initializes APIs and starts the application menu.
 */
fun main() {
    runMenu()
}
/**
 * Displays the main menu of the application and returns the user's choice.
 *
 * @return The option selected by the user.
 */

fun mainMenu(): Int {
    print(
        """
       > ╔══════════════════════════════════╗
        >║                                  ║
       > ║     Storage control app MENU     ║
       > ║                                  ║
       > ╟──────────────────────────────────╢
       > ║   1)Add product                  ║
       > ║   2)List product                 ║
       > ║   3)Delete product               ║
       > ║   4)upd product                  ║
       > ║   5)sort product                 ║
       > ║   6)list product by criterion    ║
       > ║   7)Check is product in stock    ║
       > ║    (no order product)by name     ║
       > ║                                  ║
       > ║   8)Add order                    ║
       > ║   9)list Orders                  ║
       > ║   10)Delete order                ║
       > ║   11)Update order                ║
       > ║   12)Sort order                  ║
       > ║   13)Change Order status         ║
       > ║   14)List orders by criterion    ║
       > ║                                  ║
       > ║   15)add product to order        ║
       > ║   (only for active orders)       ║
       > ║   16)list product by order       ║
       > ║   17)Delete product from order   ║
       > ║   (only for active orders)       ║
       > ║   18)Show sum of product price   ║
       > ║      by order                    ║
       > ╟──────────────────────────────────╢
       > ║   Save and load aria             ║
       > ║   19) load orders                ║
       > ║   20) load products              ║
       > ║   21) save orders                ║
       > ║   22) save products              ║
       > ╚══════════════════════════════════╝
        """.trimIndent()
    )
    return readInt("\nInput your choice: ")
}
/**
 * Runs the application menu loop.
 * Executes actions based on user input.
 */
fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> addProduct()
            2 -> listProduct()
            3 -> deleteProduct()
            4 -> UpdProdct()
            5 ->sortProduct()
            6 ->listProductByCriteria()
            7 ->stockChek()

            8 -> addOrder()
            9 -> listOrders()
            10-> deleteOrder()
            11 -> UpdOrder()
            12 ->sortOrder()
            13 ->changeActiveStatus()
            14->listOrderByCriteria()

            15 ->addProductToOrder()
            16 ->listProductByOrder()
            17 ->delateProductFromOrder()
            18 ->showProductPriceSumByOrder()
            19 ->loadOrder()
            20 ->loadProduct()
            21 ->saveOrder()
            22 ->saveProduct()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)


}
/**
 * Saves all orders to a file.
 *
 * @throws IOException If there is an error writing to the file.
 * @see loadOrder
 */
fun saveOrder() {
    try {
        OrderAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}
/**
 * Loads all orders from a file.
 *
 * @throws IOException If there is an error reading the file.
 * @see saveOrder
 */
fun loadOrder() {
    try {
        OrderAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}
/**
 * Saves all products to a file.
 *
 * @throws IOException If there is an error writing to the file.
 * @see loadProduct
 */
fun saveProduct() {
    try {
        ProductAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}
/**
 * Loads all products from a file.
 *
 * @throws IOException If there is an error reading the file.
 * @see saveProduct
 */
fun loadProduct() {
    try {
        ProductAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

/**
 * Adds multiple products to the system.
 * Prompts the user to specify product details and repeats the process for the required count.
 *
 * @throws IllegalArgumentException If the count of products is less than or equal to zero.
 * @param productName The name of the product to be added.
 * @param memorySize The size of the product's memory in GB.
 * @param price The price of the product.
 * @see ProductAPI.addProduct
 */
fun addProduct(){

    val count = readInt("How many product do u need to add: ")


    if (count <= 0) {
        println("Number of products must be greater than 0.")
        return
    }
    else {

        val productName = readString("Enter product name: ")
        val memorySize = readValidSize("Memory size in (gb) from select choose ${categories}: ")
        val price = readValidPrice("Enter price: ")
            repeat(count)  {

                val isAdd = ProductAPI.addProduct(Product(0, productName, memorySize, price, -1))
                if (isAdd) {
                    println("Product added successfully!")
                } else {
                    println("Product add failed!")
                }
            }
    }
}


/**
 * Lists products based on the user's selection.
 * Options include viewing all products, not ordered products, or ordered products.
 *
 * @throws IllegalArgumentException If the user enters an invalid option.
 * @see listAllProduct
 * @see listNotOrderedProduct
 * @see listOrderedProduct
 */
fun listProduct() {
    if (ProductAPI.checkOfNumberAllProduct()) {
        val option = readInt(
            """
                  >╔═════════════════════════════════════╗
                  >║    1) View ALL products             ║
                  >║    2) View NOT stored products      ║
                  >║    3) View  Ordered products        ║
                  >╚═════════════════════════════════════╝
         >Choose what list do u want: """.trimMargin(">"))

        when (option) {
            1 -> listAllProduct();
            2 -> listNotOrderedProduct();
            3 -> listOrderedProduct();
            else -> println("Invalid option entered: $option");
        }
    }
}
/**
 * Displays all products in the system.
 *
 * @see ProductAPI.showProduct
 */
fun listAllProduct(){
    println("List of all products: \n"+ProductAPI.showProduct())
}
/**
 * Displays all products that are not part of any order.
 *
 * @see ProductAPI.showNotOrderdProduct
 */
fun listNotOrderedProduct(){
    println("List of not ordered products: \n"+ProductAPI.showNotOrderdProduct())
}
/**
 * Displays all products that are part of an order.
 *
 * @see ProductAPI.showOrderdProduct
 */
fun listOrderedProduct(){
    println("List of ordered products: \n"+ProductAPI.showOrderdProduct())
}

/**
 * Deletes a product by its ID.
 * Validates the ID before attempting deletion.
 *
 * @throws IllegalArgumentException If the provided product ID is invalid.
 * @param seacrhID The ID of the product to delete.
 * @see ProductAPI.delatePtoduct
 * @see listAllProduct
 */

fun deleteProduct()
{
    if (ProductAPI.checkOfNumberAllProduct())
    {
        listAllProduct()
        val seacrhID = readInt("Enter product ID to delete: ")
        if (ProductAPI.checkIsValidProductID(seacrhID))
        {
            if (ProductAPI.delatePtoduct(seacrhID))
            {
                println("Product deleted successfully.")
            }
            else{
                println("Product deleted failed.")
            }
        }

    }

}

/**
 * Updates a product's details based on its ID.
 * Prompts the user for updated product details.
 *
 * @param seacrhID The ID of the product to update.
 * @param productName The new name of the product.
 * @param memorySize The updated memory size of the product in GB.
 * @param price The updated price of the product.
 * @see ProductAPI.updateProduct
 * @see listAllProduct
 */
fun UpdProdct(){
   if (ProductAPI.checkOfNumberAllProduct()) {

       listAllProduct()
       val seacrhID = readInt("Enter product ID that u want to update: ")
       if (ProductAPI.checkIsValidProductID(seacrhID)) {

           val productName = readString("Enter product name: ")
           val memorySize = readValidSize("Memory size in (gb) from select choose ${categories}: ")
           val price = readValidPrice("Enter price: ")
           val isAdd = ProductAPI.updateProduct(seacrhID, productName, memorySize, price)
           if (isAdd) {
               println("Product update successfully!")
           } else {
               println("Product update failed!")
           }
       }

   }

}
/**
 * Sorts products based on a user-selected criterion.
 * Available criteria include "id", "name", "memory", and "price".
 *
 * @param category The criterion to sort the products by (e.g., "id", "name").
 * @throws IllegalArgumentException If the selected category is invalid.
 * @see ProductAPI.sortProductByCategory
 * @see listAllProduct
 */
 fun sortProduct(){
     if (ProductAPI.checkOfNumberAllProduct())
     {
         val category = readValidCategoryForProduct("Enter (String input) product criterion for sorting from list ${criterionOfProduct}: ")
         ProductAPI.sortProductByCategory(category);
         println("Soreted Product list: ")
         listAllProduct()
     }

 }
/**
 * Lists products based on a specific search criterion and value.
 * Prompts the user for input and displays matching products.
 *
 * @param category The criterion to filter the products by (e.g., "id", "memory").
 * @param searchElement The value to match against the selected criterion.
 * @throws IllegalArgumentException If the input criterion or value is invalid.
 * @see ProductAPI.showByCriteria
 */
 fun listProductByCriteria(){
     if (ProductAPI.checkOfNumberAllProduct()){
         val category = readValidCriterionForProductSearch("Enter (String input) product criterion for search from list ${criterionOfProductSearch}: ")
         var serchElement:Any = 0
         when(category)
         {
             "id","memory","orderid" -> serchElement=readInt("Input INT element to search: ")
             "name"-> serchElement=readString("Input String element to search: ")
             "price" ->serchElement= readDouble("Input Double element to search: ")
         }
         println(ProductAPI.showByCriteria(category,serchElement))
     }

 }
/**
 * Checks the stock of a specific product by its name.
 * Displays stock availability and details of products in stock.
 *
 * @param searchProduct The name of the product to check in stock.
 * @see ProductAPI.countIsInStockByName
 * @see ProductAPI.checkIsStock
 */
fun stockChek(){
    if (ProductAPI.checkOfNumberNotStoredProduct()){
        val searchProduct = readString("Enter product name for stock check: ")
        if (ProductAPI.countIsInStockByName(searchProduct)>0)
        {
            println("Its ${ProductAPI.countIsInStockByName(searchProduct)} prduct with name $searchProduct in stock\n"  +
                            "List of this product:\n "+ProductAPI.checkIsStock(searchProduct))
        }
        else{
            println("Product with name $searchProduct is not in stock")
        }
    }
}

/**
 * Adds a new order to the system.
 *
 * @param customerName The name of the customer placing the order.
 * @see OrderAPI.addOrder
 */
fun addOrder(){

    val customerName = readString("Enter customer Name : ")
    val isAdd = OrderAPI.addOrder(Order(0,customerName,true))
    if (isAdd) {
        println("Order added successfully!")
    }
    else
    {
        println("Order add failed!")
    }
}
/**
 * Lists orders based on user selection.
 * Options include viewing all orders, active orders, or inactive orders.
 *
 * @throws IllegalArgumentException If the user enters an invalid option.
 * @see listAllOrder
 * @see listActiveOrder
 * @see listInactiveOrder
 */
fun listOrders() {
    if (OrderAPI.checkAreNumberOrder()) {
        val option = readInt(
            """
                  >╔═════════════════════════════════════╗
                  >║    1) View ALL order                ║
                  >║    2) View ACTIVE orders            ║
                  >║    3) View INACTIVE orders          ║
                  >╚═════════════════════════════════════╝
         > Choose what list do u want: """.trimMargin(">"))

        when (option) {
            1 -> listAllOrder();
            2 -> listActiveOrder();
            3 ->  listInactiveOrder();
            else -> println("Invalid option entered: $option");
        }
    }
}

/**
 * Displays all orders in the system.
 *
 * @see OrderAPI.showOrder
 */

fun listAllOrder(){
    println("List of all orders: \n"+OrderAPI.showOrder())
}
/**
 * Displays all active orders in the system.
 *
 * @see OrderAPI.showOrderActive
 */
fun listActiveOrder(){
    println("List of active orders: \n"+OrderAPI.showOrderActive())
}

/**
 * Displays all inactive orders in the system.
 *
 * @see OrderAPI.showOrderINactive
 */
fun listInactiveOrder(){
    println("List of inactive orders: \n"+OrderAPI.showOrderINactive())
}
/**
 * Deletes an order by its ID.
 * Also updates products associated with the deleted order.
 *
 * @param seacrhID The ID of the order to delete.
 * @throws IllegalArgumentException If the order ID is invalid.
 * @see OrderAPI.delateOrder
 */
fun deleteOrder() {

    if (OrderAPI.checkAreNumberOrder()) {
        listAllOrder()
        val seacrhID = readInt("Enter Order ID to delete: ")
        if(OrderAPI.checkIsOrderValidID(seacrhID))
        {
            if (OrderAPI.delateOrder(seacrhID))
            {
                println("Order deleted successfully. Products associated with this order have been updated.")
            }
            else{
                println("Order deleted failed.")
            }
        }


    }
}


/**
 * Updates an order's customer name by its ID.
 *
 * @param seacrhID The ID of the order to update.
 * @param customerName The new customer name.
 */
fun UpdOrder(){
    if (OrderAPI.checkAreNumberOrder()) {


        listAllOrder()
        val seacrhID = readInt("Enter Order ID that u want to update: ")

        if(OrderAPI.checkIsOrderValidID(seacrhID))
        {
            val customerName = readString("Enter customerName to update: ")
            val isAdd = OrderAPI.updateOrder(seacrhID, customerName)
            if (isAdd)
            {
                println("Order updated successfully!")
            }
            else
            {
                println("Order updated failed!")
            }
        }

    }
}
/**
 * Toggles the active status of an order.
 * Prompts the user to choose between activating or deactivating an order.
 */
    fun changeActiveStatus()
    {
        if (OrderAPI.checkAreNumberOrder()) {
            val option = readInt(
                """
                  >╔═════════════════════════════════════╗
                  >║    1) Change order to Incative      ║
                  >║    2) Change order to active        ║
                  >╚═════════════════════════════════════╝
         > Choose option: """.trimMargin(">"))

            when (option) {
                1 -> MakeOrderInavtive()
                2 -> MakeOrderActive()
                else -> println("Invalid option entered: $option");
            }

        }
    }

/**
 * Marks an active order as inactive by its ID.
 *
 * @param seacrhActiveOrderID The ID of the active order to deactivate.
 */
fun MakeOrderInavtive()
{
    if (OrderAPI.checkOfNumberActiveOrder())
    {
        listActiveOrder()
        val seacrhActiveOrderID = readInt("enter the order ID to make INACTIVE: ")
        if (OrderAPI.checkIsValidActiveID(seacrhActiveOrderID))
        {
        if (OrderAPI.swithcActiveStatus(seacrhActiveOrderID))
        {
            println("Changed to INACTIVE successfully")
        }
        else
        {
            println("Changed to INACTIVE failed!")
        }
     }
    }
}
/**
 * Marks an inactive order as active by its ID.
 *
 * @param seacrhInactiveOrderID The ID of the inactive order to activate.
 */
fun MakeOrderActive()
{
    if (OrderAPI.checkOfNumberInactiveOrder())
    {

        listInactiveOrder()
        val seacrhActiveOrderID = readInt("enter the order ID to make ACTIVE: ")

        if (OrderAPI.checkIsValidInactiveID(seacrhActiveOrderID))
        {

        if (OrderAPI.swithcActiveStatus(seacrhActiveOrderID))
        {
            println("Changed to ACTIVE successfully")
        }
        else
        {
            println("Changed to ACTIVE failed!")
        }
        }
    }
}
/**
 * Sorts orders based on a user-specified criterion.
 * Prompts the user for the sorting category and displays the sorted list.
 *
 * @param category The criterion for sorting the orders.
 * @see OrderAPI.sortOrderByCategory
 */
fun sortOrder(){
    if (OrderAPI.checkAreNumberOrder())
    {
        val category = readValidCategoryForOrder("Enter (String input) order criterion for sorting from list ${criterionOfOrder}: ")
        OrderAPI.sortOrderByCategory(category);
        println("Soreted order list: ")
        listAllOrder()
    }

}
/**
 * Lists orders based on a specific search criterion and value.
 * Prompts the user for input and displays matching orders.
 *
 * @param category The criterion to filter the orders (e.g., "id", "name").
 * @param searchElement The value to match against the criterion.
 * @see OrderAPI.showByCriteria
 */
fun listOrderByCriteria(){
    if (OrderAPI.checkAreNumberOrder()){
        val category = readValidCriterionForOrderSearch("Enter (String input) order criterion for search from list ${criterionOfOrder}: ")
        var serchElement:Any = 0
        when(category)
        {
            "id" -> serchElement=readInt("Input INT element to search: ")
            "name"-> serchElement=readString("Input String element to search: ")
        }
        println(OrderAPI.showByCriteria(category,serchElement))
    }

}

/**
 * Adds a product to an active order.
 * Prompts the user for the product ID and order ID and associates the product with the order.
 *
 * @param searchProductID The ID of the product to add.
 * @param searchOrderID The ID of the active order to associate the product with.
 * @see ProductAPI.addProductToOrder
 */

fun addProductToOrder()
{
    if (OrderAPI.checkOfNumberActiveOrder() && ProductAPI.checkOfNumberNotStoredProduct())
    {


        listNotOrderedProduct()
        val seacrhProductID = readInt("Enter the product ID you want to add to your order: ")

        if (ProductAPI.checkIsValidNotStoredProductID(seacrhProductID))
        {

            listActiveOrder()
            val seacrhOrderID = readInt("enter the order ID in which you want to place the product: ")

            if (OrderAPI.isValidActiveID(seacrhOrderID))
            {

                if (ProductAPI.addProductToOrder(seacrhProductID, seacrhOrderID))
                {
                    println("Product added to order  successfully!")
                }
                else
                {
                    println("Product add to order failed!")
                }
            }
        }
    }
}
/**
 * Lists products associated with a specific order.
 * Prompts the user for the order ID and displays its products.
 *
 * @param searchOrderID The ID of the order to display products for.
 * @see ProductAPI.showProductByOrder
 */
fun listProductByOrder()
{
    if (OrderAPI.checkAreNumberOrder()) {
            listAllOrder()
            val seacrhOrderID = readInt("enter the order ID in which you want to check product: ")
            if (OrderAPI.checkIsOrderValidID(seacrhOrderID))
            {

                println("Product by order with id $seacrhOrderID \n +${ProductAPI.showProductByOrder(seacrhOrderID)}")
            }
        }
}
/**
 * Displays the total price of products in a specific order.
 * Prompts the user for the order ID and calculates the total price.
 *
 * @param searchOrderID The ID of the order to calculate the product prices for.
 * @see ProductAPI.countProductSumPriceInOrder
 */
fun showProductPriceSumByOrder(){
    if (OrderAPI.checkAreNumberOrder()&& ProductAPI.checkOfNumberStoredProduct())
    {
        listAllOrder()
        val seacrhOrderID = readInt("enter the order ID in which you want to check product: ")
        if (OrderAPI.checkIsOrderValidID(seacrhOrderID))
        {
            if (ProductAPI.checkIsOrderHasProducts(seacrhOrderID))
            {
                println(ProductAPI.countProductSumPriceInOrder(seacrhOrderID))

            }
        }

    }
}

/**
 * Deletes a product from a specific active order.
 * Prompts the user for the order ID and product ID, and removes the product from the order.
 *
 * @param searchOrderID The ID of the active order.
 * @param searchProductID The ID of the product to delete.
 * @see ProductAPI.delateChosedProductsFromOrder
 */

fun delateProductFromOrder()
{
    if (OrderAPI.checkOfNumberActiveOrder()&& ProductAPI.checkOfNumberStoredProduct())
    {

        listActiveOrder()
        val seacrhOrderID = readInt("enter the order ID in which you want to delete the product: ")

        if (OrderAPI.checkIsValidActiveID(seacrhOrderID))
        {
            if (ProductAPI.checkIsOrderHasProducts(seacrhOrderID))
            {

                println("Product by order with id $seacrhOrderID \n +${ProductAPI.showProductByOrder(seacrhOrderID)}")

                val seacrhProductID = readInt("Enter the product id you want to delete from your order: ")

                if (ProductAPI.checkIsValidProductID(seacrhProductID))
                {

                    if (ProductAPI.delateChosedProductsFromOrder(seacrhOrderID, seacrhProductID))
                    {
                        println("Product delete successfully!")
                    }
                    else
                    {
                        println("Product delete field")
                    }
                }
            }
        }
    }

}
fun exitApp() {
    println("Exiting..bye")
    exit(0)
}