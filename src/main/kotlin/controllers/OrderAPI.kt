package ie.setu.controllers
import ie.setu.models.Order

class OrderAPI {
    private val orders = mutableListOf<Order>()
    private var idGen :Int = 0;

    fun idCreate() = idGen+1

    fun addOrder(order: Order) {
        order.orderID = idCreate()
        orders.add(order)
    }

    fun showOrder()=
        if (orders.isEmpty())
            "No notes stored"
        else
            orders.joinToString(separator = "\n"){order ->
                orders.indexOf(order).toString() +": "+order.toString() }

}

