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

        products.forEach({product ->
            if (orderIDSearch == product.orderID) {
                product.orderID = -1
            }
        })
    }


    fun delatePtoduct(findID: Int): Boolean=

        if  (products.removeIf{product -> product.productID == findID})
             true

        else false




    fun updateProduct(seacrhID: Int ,productNameUpd: String,memorySizeUpd : Int,priceUpd : Double): Boolean{
       var isUpd = false
           products.forEach({product ->
            if (seacrhID == product.productID) {
                product.productName = productNameUpd
                product.memorySize = memorySizeUpd
                product.price = priceUpd
                isUpd = true
            }
        })
       return isUpd
    }



    fun numberOfProduct(): Int {
        return products.size
    }

    fun isValidID(seacrhID: Int): Boolean=
         products.any  { product -> product.productID == seacrhID }


    fun addProductToOrder(productSeacrhID: Int, orderIDaddInProduct: Int) :Boolean{
       val productIsADD = products.find { product -> product.productID == productSeacrhID }
        return if (productIsADD!!.orderID != -1) {
            println("This product already exists")
            false
        }else{
            productIsADD.orderID=orderIDaddInProduct
            true
        }


    }




}
