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


fun main() {
    runMenu()
}


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
        >║                                  ║
       > ║   6)Add order                    ║
       > ║   7)list Orders                  ║
       > ║   8)Delete order                 ║
       > ║   9)Update product               ║
       > ║   10)Sort order                  ║
       > ║   11)Change Order status         ║
       > ║                                  ║
       > ║   12)add product to order        ║
       > ║   13)list product by order       ║
       > ║   14)Delete product from order   ║
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

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> addProduct()
            2 -> listProduct()
            3 -> deleteProduct()
            4 -> UpdProdct()
            5 ->sortProduct()

            6 -> addOrder()
            7 -> listOrders()
            8-> deleteOrder()
            9 -> UpdOrder()
            10 ->sortOrder()
            11 ->changeActiveStatus()

            12 ->addProductToOrder()
            13 ->listProductByOrder()

            14 ->delateProductFromOrder()
            19 ->loadOrder()
            20 ->loadProduct()
            21 ->saveOrder()
            22 ->saveProduct()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)


}
fun saveOrder() {
    try {
        OrderAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun loadOrder() {
    try {
        OrderAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun saveProduct() {
    try {
        ProductAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun loadProduct() {
    try {
        ProductAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}


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



fun listProduct() {
    if (ProductAPI.checkOfNumberAllProduct()) {
        val option = readInt(
            """
                  >╔═════════════════════════════════════╗
                  >║    1) View ALL products             ║
                  >║    2) View NOT stored products      ║
                  >║    2) View  Ordered products        ║
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

fun listAllProduct(){
    println(ProductAPI.showProduct())
}
fun listNotOrderedProduct(){
    println(ProductAPI.showNotOrderdProduct())
}

fun listOrderedProduct(){
    println(ProductAPI.showOrderdProduct())
}



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
                println("Ptoduct deleted successfully.")
            }
            else{
                println("Ptoduct deleted failed.")
            }
        }

    }

}


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

 fun sortProduct(){
     if (ProductAPI.checkOfNumberAllProduct())
     {
         val category = readValidCategoryForProduct("Enter (String input) product criterion for sorting from list ${criterionOfProduct}: ")
         ProductAPI.sortProductByCategory(category);
         println("Soreted Product list: ")
         listAllProduct()
     }

 }

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



fun listAllOrder(){
    println(OrderAPI.showOrder())
}
fun listActiveOrder(){
    println(OrderAPI.showOrderActive())
}
fun listInactiveOrder(){
    println(OrderAPI.showOrderINactive())
}


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

fun sortOrder(){
    if (OrderAPI.checkAreNumberOrder())
    {
        val category = readValidCategoryForOrder("Enter (String input) order criterion for sorting from list ${criterionOfOrder}: ")
        OrderAPI.sortOrderByCategory(category);
        println("Soreted order list: ")
        listAllOrder()
    }

}

fun addProductToOrder()
{
    if (OrderAPI.checkOfNumberActiveOrder() && ProductAPI.checkOfNumberNotStoredProduct())
    {


        listNotOrderedProduct()
        val seacrhProductID = readInt("Enter the product id you want to add to your order: ")

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

fun listProductByOrder()
{
    if (OrderAPI.checkAreNumberOrder()) {
            listActiveOrder()
            val seacrhOrderID = readInt("enter the order ID in which you want to check product: ")
            if (OrderAPI.checkIsOrderValidID(seacrhOrderID))
            {

                println(ProductAPI.showProductByOrder(seacrhOrderID))
            }
        }
}



fun delateProductFromOrder()
{
    if (OrderAPI.checkOfNumberActiveOrder()&& ProductAPI.checkOfNumberStoredProduct())
    {

        listActiveOrder()
        val seacrhOrderID = readInt("enter the order ID in which you want to delete the product: ")

        if (OrderAPI.checkIsValidActiveID(seacrhOrderID))
        {


            println(ProductAPI.showProductByOrder(seacrhOrderID))

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
fun exitApp() {
    println("Exiting..bye")
    exit(0)
}