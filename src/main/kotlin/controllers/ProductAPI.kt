package ie.setu.controllers
import ie.setu.models.Product
class ProductAPI (private val OrderAPI: OrderAPI){
    private val products = mutableListOf<Product>()
    private var idGen :Int = 0;

    fun idCreate() = idGen++

    fun addProduct(product: Product) {
        product.productID = idCreate()
        products.add(product)
    }

    fun showProduct()=
        if (products.isEmpty())
            "No notes stored"
        else
            products.joinToString(separator = "\n"){product ->
                products.indexOf(product).toString() +": "+product.toString() }

}
