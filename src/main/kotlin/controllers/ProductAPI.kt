package ie.setu.controllers
import ie.setu.models.Order
import ie.setu.models.Product
import persistence.Serializer
import javax.naming.directory.SearchResult

class ProductAPI (serializerType: Serializer)
{
    private var serializer: Serializer = serializerType
    internal var products = mutableListOf<Product>()
    private var idGen: Int = 0;

    fun idCreate() = idGen++


    @Throws(Exception::class)
    fun load()
    {
        products = serializer.read() as ArrayList<Product>
    }

    @Throws(Exception::class)
    fun store()
    {
        serializer.write(products)
    }


    fun addProduct(product: Product): Boolean
    {
        product.productID = idCreate()
        return products.add(product)
    }

    fun showProduct() =
            if (products.isEmpty())
                "No product stored"
            else formatListString(products)

    fun showNotOrderdProduct() =
            if (numberOfNotOrderedProduct() <= 0)
                "No Ordered product"
            else formatListString(products.filter { product -> product.orderID == -1 })


    fun showOrderdProduct() =
            if (numberOfOrderedProduct() <= 0)
                "No Ordered product"
            else formatListString(products.filter { product -> product.orderID != -1 })

    fun delatePtoduct(findID: Int): Boolean =

            if (products.removeIf { product -> product.productID == findID })
                true
            else false


    fun updateProduct(seacrhID: Int, productNameUpd: String, memorySizeUpd: Int, priceUpd: Double): Boolean
    {
        var isUpd = false
        products.forEach { product ->
            if (seacrhID == product.productID)
            {
                product.productName = productNameUpd
                product.memorySize = memorySizeUpd
                product.price = priceUpd
                isUpd = true
            }
        }
        return isUpd
    }


    public fun checkOfNumberNotStoredProduct(): Boolean
    {

        if (numberOfNotOrderedProduct() <= 0)
        {
            println("no not ordered product")
            return false
        }
        else return true
    }

    public fun checkOfNumberStoredProduct(): Boolean
    {

        if (numberOfOrderedProduct() <= 0)
        {
            println("no  ordered product")
            return false
        }
        else return true
    }

    public fun checkOfNumberAllProduct(): Boolean
    {

        if (numberOfProduct() <= 0)
        {
            println("No product stored")
            return false
        }
        else return true
    }

    fun numberOfProduct(): Int = products.size
    fun numberOfNotOrderedProduct(): Int = products.count { product -> product.orderID == -1 }
    fun numberOfOrderedProduct(): Int = products.count { product -> product.orderID != -1 }


    fun isValidID(seacrhID: Int): Boolean =
            products.any { product -> product.productID == seacrhID }

    fun isValidNotStoredProductID(seacrhID: Int): Boolean =
            products.any { product -> product.productID == seacrhID && product.orderID == -1 }


    public fun checkIsValidProductID(searchProductID: Int): Boolean
    {

        if (!isValidID(searchProductID))
        {
            println("Its not Valid  Product ID $searchProductID")
            return false
        }
        else return true
    }

    public fun checkIsValidNotStoredProductID(searchProductID: Int): Boolean
    {

        if (!isValidNotStoredProductID(searchProductID))
        {
            println("Its not Valid not ordered  Product ID $searchProductID")
            return false
        }
        else return true
    }

    fun delateAllProductsFromOrder(orderIDSearch: Int): Boolean
    {
        var isDelate = false
        products.forEach { product ->
            if (orderIDSearch == product.orderID)
            {
                product.orderID = -1
                isDelate = true
            }
        }
        return isDelate
    }


    fun addProductToOrder(productSeacrhID: Int, orderIDaddInProduct: Int): Boolean
    {
        val productIsADD = products.find { product -> product.productID == productSeacrhID }
        return if (productIsADD!!.orderID != -1)
        {
            println("This product already exists")
            false
        }
        else
        {
            productIsADD.orderID = orderIDaddInProduct
            true
        }


    }

    fun showProductByOrder(seacrhOrderID: Int): String
    {
        val isValidOrder = products.filter { product -> product.orderID == seacrhOrderID }
        return if (isValidOrder.isEmpty())
        {
            " No stored product in order with id $seacrhOrderID"
        }
        else
        {
            isValidOrder.joinToString(separator = "\n") { product ->
                "  " + products.indexOf(product).toString() + ": " + product.toString()
            }
        }
    }


    fun countProductSumPriceInOrder(seacrhOrderID: Int): String
    {
        var sum =0.0
       SerchByCriteria("orderid", seacrhOrderID) .forEach{ product ->
           sum+= product.price
       }
        return "Sum of product price by order with id $seacrhOrderID = $sum"
    }

    fun checkIsOrderHasProducts(searchOrderID: Int): Boolean{
        val result = products.any { product -> product.orderID == searchOrderID }
        if (!result){
            println("Its not Ordered  Product in order with ID $searchOrderID")
            return false
        }
        else{
            return true
        }
    }

    fun delateChosedProductsFromOrder(orderIDSearch: Int, productSeacrhID: Int): Boolean
    {
        val productIsADD = products.find { product -> product.productID == productSeacrhID }
        return if (productIsADD!!.orderID != -1 && productIsADD.orderID == orderIDSearch)
        {

            productIsADD.orderID = -1
            true
        }
        else
        {
            println("This product not in this order with id $orderIDSearch")
            false
        }

    }

    fun sortProductByCategory(category: String) =
            when (category)
            {
                "id" -> products.sortBy { product -> product.productID }
                "name" -> products.sortBy { product -> product.productName }
                "memory" -> products.sortBy { product -> product.memorySize }
                "price" -> products.sortBy { product -> product.price }
                else -> println("invalid category")
            }

    fun SerchByCriteria(criterion: String, SeacrchElement: Any): List<Product> =
            products.filter { product ->

                when (criterion.lowercase())
                {
                    "id" -> (SeacrchElement as? Int)?.let { product.productID == SeacrchElement }
                        ?: false  //serach by id
                    "name" -> (SeacrchElement as? String)?.let { product.productName.lowercase() == SeacrchElement.lowercase() } ?: false
                    "memory" -> (SeacrchElement as? Int)?.let { product.memorySize == SeacrchElement } ?: false
                    "price" -> (SeacrchElement as? Double)?.let { product.price == SeacrchElement } ?: false
                    "orderid" -> (SeacrchElement as? Int)?.let { product.orderID == SeacrchElement } ?: false
                    else -> false
                }
            }

    fun showByCriteria(criterion: String, SeacrchElement: Any): String =


            if (SerchByCriteria(criterion, SeacrchElement).isEmpty())

                "no product whith $SeacrchElement element in $criterion"
            else
            {
                formatListString(SerchByCriteria(criterion, SeacrchElement))
            }

    fun countIsInStockByName(SeacrchName: String): Int =

            SerchByCriteria("name", SeacrchName).stream()
                .filter() { product -> product.orderID == -1 }
                .count()
                .toInt()

    fun checkIsStock(SeacrchName: String): String
    {
      return  if (countIsInStockByName(SeacrchName) > 0)
        {
            formatListString(SerchByCriteria("name", SeacrchName).filter { product -> product.orderID == -1 })
        }
        else{
            "No product in stock with name $SeacrchName"
        }

}





    private fun formatListString(productToFormat : List<Product>) : String =
            productToFormat
                .joinToString (separator = "\n\n") { product ->
                    products.indexOf(product).toString() + ": " + product.toString() }

}


