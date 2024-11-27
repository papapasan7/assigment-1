package ie.setu.controllers
import ie.setu.models.Order


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
        else formatListString(orders)


    fun showOrderActive()=
            if (numberOfActiveOrder()<=0)
                "No Actice Orders stored"
            else formatListString(orders.filter { order -> order.isActive})

    fun showOrderINactive()=
            if (numberOfINactiveOrder()<=0)
                "No Active Orders stored"
            else formatListString(orders.filter { order -> !order.isActive})



    fun delateOrder(findOrderID: Int): Boolean=

          if   (orders.removeIf{order -> order.orderID == findOrderID}){
              productAPI.delateAllProductsFromOrder(findOrderID)
               true
          }
          else {

               false
          }



    fun updateOrder(seacrhOrderID: Int ,customerNameUpd: String): Boolean{
        var isUpd = false
        orders.forEach{order ->
            if (seacrhOrderID == order.orderID) {
                order.customerName = customerNameUpd

                isUpd = true
            }
        }
        return isUpd


    }


    fun numberOfOrder(): Int = orders.size
    fun numberOfActiveOrder(): Int = orders.count{order -> order.isActive }
    fun numberOfINactiveOrder(): Int = orders.count{order -> !order.isActive }


    fun isValidID(searchOrderID: Int): Boolean=
        orders.any { order -> order.orderID == searchOrderID }


    fun isValidActiveID(searchOrderID: Int): Boolean=
            orders.any { order -> order.orderID == searchOrderID && order.isActive }

    fun isValidInactiveID(searchOrderID: Int): Boolean=
            orders.any { order -> order.orderID == searchOrderID && !order.isActive }


    private fun formatListString(OrderToFormat : List<Order>) : String=
         OrderToFormat.joinToString(separator = "\n"){order ->
            val productFind = productAPI.products.filter { product ->  product.orderID == order.orderID }
            if (productFind.isEmpty()){
                OrderToFormat.indexOf(order).toString() +": "+order.toString()+"\n No products in this order"
            }else{
                OrderToFormat.indexOf(order).toString() +": "+order.toString()+"\n product of ${order.customerName} \n"+
                        productFind.joinToString(separator = "\n"){product ->
                            "    "+productFind.indexOf(product).toString() +": "+product.toString() }
            }

        }

    fun swithcActiveStatus(searchOrderID: Int):Boolean{
        var isUpd = false
        orders.forEach{order ->
            if (searchOrderID == order.orderID) {
                order.isActive= !order.isActive

                isUpd = true
            }
        }
        return isUpd
    }





}

