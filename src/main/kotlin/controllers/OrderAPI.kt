package ie.setu.controllers
import ie.setu.models.Order
import ie.setu.models.Product
import ie.setu.controllers.ProductAPI

class OrderAPI (private val productAPI: ProductAPI) {
    private val orders = mutableListOf<Order>()
    private var idGen :Int = 0;

    fun idCreate() = idGen++

    fun addOrder(order: Order) :Boolean{
        order.orderID = idCreate()
       return orders.add(order)
    }

    fun showOrder()=
        if (orders.isEmpty())
            "No Orders stored"
        else
            orders.joinToString(separator = "\n"){order ->
                orders.indexOf(order).toString() +": "+order.toString() }








    fun delateOrder(findID: Int): Boolean{

          if   (orders.removeIf{order -> order.orderID == findID}){
              productAPI.delateProductsFromOrder(findID)
              return true
          }
          else {
              println("Order with ID $findID not found.")
              return false
          }
    }


    fun updateOrder(seacrhID: Int ,customerNameUpd: String): Boolean{
        var isUpd = false
        orders.forEach({order ->
            if (seacrhID == order.orderID) {
                order.customerName = customerNameUpd

                isUpd = true
            }
        })
        return isUpd


    }


    fun numberOfOrder(): Int {
        return orders.size
    }

}

