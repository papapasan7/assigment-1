package ie.setu.controllers
import ie.setu.models.Order
import ie.setu.models.Product
import persistence.Serializer


class OrderAPI (private val productAPI: ProductAPI,serializerType: Serializer) {
    private var orders = mutableListOf<Order>()
    private var serializer: Serializer = serializerType
    private var idGen :Int = 0;

    fun idCreate() = idGen++





    @Throws(Exception::class)
    fun load() {
        orders = serializer.read() as ArrayList<Order>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(orders)
    }


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
                "No Inactive Orders stored"
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


    public fun checkOfNumberActiveOrder() : Boolean{

        if (numberOfActiveOrder()<=0) {
            println("No active order stored")
            return false
        }
        else return true
    }

    public fun checkOfNumberInactiveOrder() : Boolean{

        if (numberOfINactiveOrder()<=0) {
            println("No Inactive order stored")
            return false
        }
        else return true
    }
    public fun checkAreNumberOrder() : Boolean{

        if (numberOfOrder()<=0) {
            println("No order stored")
            return false
        }
        else return true
    }


    fun isValidID(searchOrderID: Int): Boolean=
        orders.any { order -> order.orderID == searchOrderID }


    fun isValidActiveID(searchOrderID: Int): Boolean=
            orders.any { order -> order.orderID == searchOrderID && order.isActive }

    fun isValidInactiveID(searchOrderID: Int): Boolean=
            orders.any { order -> order.orderID == searchOrderID && !order.isActive }

    public fun checkIsValidActiveID(searchOrderID: Int) : Boolean{

        if (!isValidActiveID(searchOrderID)) {
            println("Its Valid Active Order ID $searchOrderID")
            return false
        }
        else return true
    }

    public fun checkIsValidInactiveID(searchOrderID: Int) : Boolean{

        if (!isValidInactiveID(searchOrderID)) {
            println("Its Valid Inactive Order ID $searchOrderID")
            return false
        }
        else return true
    }

    public fun checkIsOrderValidID(searchOrderID: Int) : Boolean{

        if (!isValidID(searchOrderID)) {
            println("Its Valid Order ID $searchOrderID")
            return false
        }
        else return true
    }

    fun sortOrderByCategory(category: String)=
            when(category){
                "id" -> orders.sortBy { order -> order.orderID }
                "name" -> orders.sortBy { order -> order.customerName }

                else -> println("invalid category")
            }



    fun SerchByCriteria(criterion: String,SeacrchElement: Any):List<Order> =
            orders.filter { order ->
                when (criterion.lowercase()){
                    "id"-> (SeacrchElement as? Int)?.let { order.orderID == SeacrchElement} ?:false  //serach by id
                    "name"-> (SeacrchElement as? String)?.let { order.customerName == SeacrchElement} ?:false
                    else -> false
                }
            }

    fun showByCriteria(criterion: String,SeacrchElement: Any):String=


            if (SerchByCriteria(criterion,SeacrchElement).isEmpty())

                "no order whith $SeacrchElement element in $criterion"

            else{
                formatListString(SerchByCriteria(criterion,SeacrchElement))
            }


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

