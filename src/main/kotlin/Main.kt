import ie.setu.controllers.OrderAPI
import ie.setu.controllers.ProductAPI
import ie.setu.models.Order
import ie.setu.models.Product
import java.io.File
import java.lang.System.exit
import java.util.Scanner

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
         > |   2) add a order               |
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
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)


}

fun exitApp() {
    println("Exiting..bye")
    exit(0)
}