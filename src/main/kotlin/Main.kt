import ie.setu.controllers.OrderAPI
import ie.setu.controllers.ProductAPI
import ie.setu.models.Order
import ie.setu.models.Product
import java.io.File
import java.lang.System.exit
import java.util.Scanner


import java.lang.System.exit


private val OrderAPI = OrderAPI()
private val ProductAPI = ProductAPI(OrderAPI)
val scanner = Scanner(System.`in`)
fun main() {
    runMenu()
}


fun mainMenu(): Int {
    print(""" 
         > ----------------------------------
         > |            |
         > ----------------------------------
         > | NOTE MENU                      |
         > |   1) Add product               |
         > |   2)List product
         >               
         >    6) Add order               |
         > |   7)List orders     |
         > |   
         > ----------------------------------
         > |   0) Exit                      |
         > ---------------------------------- 
         >""".trimMargin(">"))
    return scanner.nextInt()
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> addProduct()
            2 -> listAllProduct()

            6 -> addOrder()
            7 -> listAllOrder()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)


}

fun addProduct(){
    println("Enter product name: ")
    scanner.nextLine()
    val productName = scanner.nextLine() // Correctly reads the product name
    println("Enter memory size: ")
    val memorySize = scanner.nextInt()
    scanner.nextLine() // Clear the newline character after entering the integer
    println("Enter price: ")
    val price = scanner.nextDouble()
    scanner.nextLine() // Clear the newline character after entering the double

    val isAdd = ProductAPI.addProduct(Product(0, productName, memorySize, price, -1))
    if (isAdd) {
        println("Product added successfully!")
    }
    else
    {
        println("Product add failed!")
    }
}


fun listAllProduct(){
    println(ProductAPI.showProduct())
}





fun addOrder(){
    println("Enter customerName: ")
    scanner.nextLine()
    val customerName = scanner.nextLine() // Correctly reads the product name
    val isAdd = OrderAPI.addOrder(Order(0,customerName,true))
    if (isAdd) {
        println("Order added successfully!")
    }
    else
    {
        println("Order add failed!")
    }
}


fun listAllOrder(){
    println(OrderAPI.showOrder())
}


fun exitApp() {
    println("Exiting..bye")
    exit(0)
}