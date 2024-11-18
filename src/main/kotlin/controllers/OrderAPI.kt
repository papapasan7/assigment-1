package ie.setu.controllers
import ie.setu.models.Order

class OrderAPI {
    private val orders = mutableListOf<Order>()
    private var idGen :Int = 0;

    fun idCreate() = idGen++

    fun addOrder(order: Order) :Boolean{
        order.orderID = idCreate()
       return orders.add(order)
    }

    fun showOrder()=
        if (orders.isEmpty())
            "No notes stored"
        else
            orders.joinToString(separator = "\n"){order ->
                orders.indexOf(order).toString() +": "+order.toString() }

}

