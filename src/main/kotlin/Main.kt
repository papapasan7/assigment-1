import ie.setu.controllers.OrderAPI
import ie.setu.controllers.ProductAPI
import ie.setu.models.Order
import ie.setu.models.Product
import java.io.File
import java.lang.System.exit
import java.util.Scanner
import utils.*

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
         > |                                |
         > ----------------------------------
         > | Online shop MENU               |
         > |   1) Add product               |
         > |   2)List product
         >               
         >    6) Add order                  |
         > |   7)List orders                |
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

    val count = readInt("How many product do u need to add: ")


    if (count <= 0) {
        println("Number of products must be greater than 0.")
        return
    }
    else {



        val productName = readString("Enter product name: ")

        val memorySize = readInt("Memory size in (gb): ")

        val price = readDouble("Enter price: ")

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


fun listAllProduct(){
    println(ProductAPI.showProduct())
}





fun addOrder(){

    val customerName = readString("Enter customerName: ")
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