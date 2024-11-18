package ie.setu.controllers
import ie.setu.models.Product
class ProductAPI (private val OrderAPI: OrderAPI){
    private val products = mutableListOf<Product>()
    private var idGen :Int = 0;

    fun idCreate() = idGen+1

    fun addOrder(product: Product) {
        product.productID = idCreate()
        products.add(product)
    }
}
