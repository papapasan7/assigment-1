package ie.setu.controllers
import ie.setu.models.Order
import ie.setu.models.Product
import persistence.Serializer

class ProductAPI (serializerType: Serializer){
    private var serializer: Serializer = serializerType
    internal var products = mutableListOf<Product>()
    private var idGen :Int = 0;

    fun idCreate() = idGen++



    @Throws(Exception::class)
    fun load() {
        products = serializer.read() as ArrayList<Product>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(products)
    }




    fun addProduct(product: Product): Boolean  {
        product.productID = idCreate()
        return products.add(product)
    }

    fun showProduct()=
        if (products.isEmpty())
            "No product stored"
        else formatListString(products)

    fun showNotOrderdProduct()=
            if (numberOfNotOrderedProduct()<=0)
                "No Orderedd product"
            else formatListString(products.filter { product -> product.orderID== -1  })


    fun showOrderdProduct()=
            if (numberOfOrderedProduct()<=0)
                "No Orderedd product"
            else formatListString(products.filter { product -> product.orderID!= -1  })

    fun delatePtoduct(findID: Int): Boolean=

        if  (products.removeIf{product -> product.productID == findID})
             true

        else false




    fun updateProduct(seacrhID: Int ,productNameUpd: String,memorySizeUpd : Int,priceUpd : Double): Boolean{
       var isUpd = false
           products.forEach{product ->
            if (seacrhID == product.productID) {
                product.productName = productNameUpd
                product.memorySize = memorySizeUpd
                product.price = priceUpd
                isUpd = true
            }
        }
       return isUpd
    }



    public fun checkOfNumberNotStoredProduct() : Boolean{

        if (numberOfNotOrderedProduct()<=0) {
            println("no not orderd product")
            return false
        }
        else return true
    }
    public fun checkOfNumberStoredProduct() : Boolean{

        if (numberOfOrderedProduct()<=0) {
            println("no  orderd product")
            return false
        }
        else return true
    }

    public fun checkOfNumberAllProduct() : Boolean{

        if (numberOfProduct()<=0) {
            println("No product stored")
            return false
        }
        else return true
    }

    fun numberOfProduct(): Int = products.size
    fun numberOfNotOrderedProduct(): Int = products.count { product -> product.orderID == -1 }
    fun numberOfOrderedProduct(): Int = products.count { product -> product.orderID != -1 }






    fun isValidID(seacrhID: Int): Boolean=
         products.any  { product -> product.productID == seacrhID }

    fun isValidNotStoredProductID(seacrhID: Int): Boolean=
            products.any  { product -> product.productID == seacrhID && product.orderID == -1}


    public fun checkIsValidProductID(searchProductID: Int) : Boolean{

        if (!isValidID(searchProductID)) {
            println("Its Valid  Product ID $searchProductID")
            return false
        }
        else return true
    }

    public fun checkIsValidNotStoredProductID(searchProductID: Int) : Boolean{

        if (!isValidNotStoredProductID(searchProductID)) {
            println("Its Valid no orderd  Product ID $searchProductID")
            return false
        }
        else return true
    }

    fun delateAllProductsFromOrder(orderIDSearch: Int):Boolean {
        var isDelate =false
        products.forEach({product ->
                             if (orderIDSearch == product.orderID) {
                                 product.orderID = -1
                                 isDelate =true
                             }
                         })
        return isDelate
    }


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

    fun showProductByOrder(seacrhOrderID:Int):String
    {
        val isValidOrder=products.filter { product -> product.orderID == seacrhOrderID }
        return if(isValidOrder.isEmpty())
        {
            " No stored product in order with id $seacrhOrderID"
        }
        else
        {
            isValidOrder.joinToString(separator = "\n"){product ->
                "  "+products.indexOf(product).toString() +": "+product.toString() }
        }
    }

    fun delateChosedProductsFromOrder(orderIDSearch: Int,productSeacrhID: Int):Boolean {
        val productIsADD = products.find { product -> product.productID == productSeacrhID }
        return if (productIsADD!!.orderID != -1 &&  productIsADD.orderID == orderIDSearch) {

            productIsADD.orderID= -1
            true
        }else{
            println("This product not in this order with id $orderIDSearch")
            false
        }

    }


    private fun formatListString(productToFormat : List<Product>) : String =
            productToFormat
                .joinToString (separator = "\n") { product ->
                    products.indexOf(product).toString() + ": " + product.toString() }

}


