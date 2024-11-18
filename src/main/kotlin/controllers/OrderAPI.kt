package ie.setu.controllers
import Order

class OrderAPI {
    private val orders = mutableListOf<Order>()
    private var idGen :Int = 0;

    fun idCreate() = idGen+1

    fun addOrder(order: Order) {
        order.orderID = idCreate()
        orders.add(order)
    }
}
