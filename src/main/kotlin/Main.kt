import ie.setu.controllers.OrderAPI
import ie.setu.controllers.ProductAPI
import ie.setu.models.Order
import ie.setu.models.Product
import java.io.File
import java.lang.System.exit

import utils.*

import java.lang.System.exit

private val ProductAPI = ProductAPI()
private val OrderAPI = OrderAPI(ProductAPI)


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
         >     3)delate product
         >      4)upd product  
         >    6) Add order                  |
         > |   7)List orders 
         > |    8)delate order
         >     9)upd product  
         > |   
         > ----------------------------------
         > |   0) Exit                      |
         > ---------------------------------- 
         >""".trimMargin(">"))
    return readInt("")
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> addProduct()
            2 -> listAllProduct()
            3 -> deleteProduct()
            4 -> UpdProdct()

            6 -> addOrder()
            7 -> listAllOrder()
            8-> deleteOrder()
            9 -> UpdOrder()
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






fun deleteProduct() {
if (ProductAPI.numberOfProduct()>0) {
    listAllProduct()
    val productId = readInt("Enter Ptoduct ID to delete: ")
    if (ProductAPI.delatePtoduct(productId)) {
        println("Ptoduct deleted successfully.")
    }
}else
{
    println("No product stored")
}
}


fun UpdProdct(){
   if (ProductAPI.numberOfProduct()<=0) {
       println("No product stored")
   }
   else{
       listAllProduct()
       val seacrhID = readInt("Which product u want to update: ")
       val productName = readString("Enter product name: ")
       val memorySize = readInt("Memory size in (gb): ")
       val price = readDouble("Enter price: ")
       val isAdd = ProductAPI.updateProduct(seacrhID, productName, memorySize, price)
       if (isAdd) {
           println("Product update successfully!")
       } else {
           println("Product update failed!")
       }
   }

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


fun deleteOrder() {

    if (OrderAPI.numberOfOrder()>0) {
        listAllOrder()
        val orderId = readInt("Enter Order ID to delete: ")
        if (OrderAPI.delateOrder(orderId)) {
            println("Order deleted successfully. Products associated with this order have been updated.")
        }
    }
    else{
        println("No order stored")
    }
}



fun UpdOrder(){
    if (OrderAPI.numberOfOrder()<=0) {
        println("No order stored")
    }
    else {
        val seacrhID = readInt("Which order u want to update: ")

        val customerName = readString("Enter customerName to update: ")
        val isAdd = OrderAPI.updateOrder(seacrhID, customerName)
        if (isAdd) {
            println("Order updated successfully!")
        } else {
            println("Order updated failed!")
        }
    }
}

fun exitApp() {
    println("Exiting..bye")
    exit(0)
}