package ie.setu.controllers
import ie.setu.models.Product
class ProductAPI (){
    private val products = mutableListOf<Product>()
    private var idGen :Int = 0;

    fun idCreate() = idGen++

    fun addProduct(product: Product): Boolean  {
        product.productID = idCreate()
        return products.add(product)
    }

    fun showProduct()=
        if (products.isEmpty())
            "No notes stored"
        else
            products.joinToString(separator = "\n"){product ->
                products.indexOf(product).toString() +": "+product.toString() }



    fun delateProductsFromOrder(orderIDSearch: Int) {
        if (products.isEmpty()) {
            return
        }
        products.forEach({product ->
            if (orderIDSearch == product.orderID) {
                product.orderID = -1
            }
        })
    }


    fun delatePtoduct(findID: Int): Boolean{
        if (products.isEmpty()){
            println("No orders stored")
            return false
        }

        if  (products.removeIf{product -> product.productID == findID}){
            return true
        }
        else {
            println("Product with ID $findID not found.")
            return false
        }
    }

}
