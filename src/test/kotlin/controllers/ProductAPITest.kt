package controllers
import ie.setu.models.Product
import models.Order
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
class ProductAPITest {
    private var orderActive1: Order? = null
    private var orderActive2: Order? = null
    private var orderInactive1: Order? = null
    private var orderInactive2: Order? = null
    private var notStoredProduct1: Product? = null
    private var notStoredProduct2: Product? = null
    private var storedProduct1: Product? = null
    private var storedProduct2: Product? = null
    private var populateProduct: ProductAPI? = ProductAPI(XMLSerializer(File("Products.xml")))

    private var emptyProduct: ProductAPI? = ProductAPI(XMLSerializer(File("empty-Products.xml")))
    private var populatedOrders: OrderAPI? = OrderAPI(populateProduct!!, XMLSerializer(File("orders.xml")))

    @BeforeEach
    fun setup() {
        orderActive1 = Order(0, "Anton", true)
        orderActive2 = Order(1, "Dima", true)
        orderInactive1 = Order(2, "Sergey", false)
        orderInactive2 = Order(3, "John", false)

        // adding 5 Note to the notes api
        populatedOrders!!.addOrder(orderActive1!!)
        populatedOrders!!.addOrder(orderActive2!!)
        populatedOrders!!.addOrder(orderInactive1!!)
        populatedOrders!!.addOrder(orderInactive2!!)

        notStoredProduct1 = Product(0, "iphone", 64, 250.5, -1)
        notStoredProduct2 = Product(1, "iphone", 64, 250.5, -1)
        storedProduct1 = Product(2, "honor", 128, 255.5, 1)
        storedProduct2 = Product(3, "lenovo", 516, 250.5, 3)

        populateProduct!!.addProduct(notStoredProduct1!!)
        populateProduct!!.addProduct(notStoredProduct2!!)
        populateProduct!!.addProduct(storedProduct1!!)
        populateProduct!!.addProduct(storedProduct2!!)
    }

    @AfterEach
    fun tearDown() {
        orderActive1 = null
        orderActive2 = null
        orderInactive1 = null
        orderInactive2 = null

        populatedOrders = null
        emptyProduct = null

        notStoredProduct1 = null
        notStoredProduct2 = null
        storedProduct1 = null
        storedProduct2 = null
    }

    @Nested
    inner class AddProductTest {
        @Test
        fun `adding a Product to a populated list adds to ArrayList`() {
            val newProduct = Product(4, "Lenovo", 128, 1000.0, -1)
            assertEquals(4, populateProduct!!.numberOfProduct())
            assertTrue(populateProduct!!.addProduct(newProduct))
            assertEquals(5, populateProduct!!.numberOfProduct())
            val result = populateProduct!!.searchByCriteria("id", 4)
            assertEquals(result.first(), newProduct)
        }

        @Test
        fun `adding a Product to a empty list adds to ArrayList`() {
            val newProduct = Product(4, "Lenovo", 128, 1000.0, -1)
            assertEquals(0, emptyProduct!!.numberOfProduct())
            assertTrue(emptyProduct!!.addProduct(newProduct))
            assertEquals(1, emptyProduct!!.numberOfProduct())
            val result = emptyProduct!!.searchByCriteria("id", 0)
            assertEquals(result.first(), newProduct)
        }
    }

    @Nested

    inner class ShowProductTest {
        @Test
        fun `is return no product if empty list`() {
            assertEquals(0, emptyProduct!!.numberOfProduct())
            assertTrue(emptyProduct!!.showProduct().contains("No product stored"))
        }

        @Test
        fun `check is showProduct gave list of product`() {
            assertEquals(4, populateProduct!!.numberOfProduct())

            assertFalse(populateProduct!!.showProduct().contains("No Orders stored"))
            assertTrue(populateProduct!!.showProduct().contains("iphone"))
            assertTrue(populateProduct!!.showProduct().contains("lenovo"))
            assertTrue(populateProduct!!.showProduct().contains("honor"))
        }
    }

    @Nested
    inner class ShowNotStoredProductsTest {
        @Test
        fun `is return No Inordered product if empty list`() {
            assertEquals(0, emptyProduct!!.numberOfNotOrderedProduct())
            assertTrue(emptyProduct!!.showNotOrderedProduct().contains("No Inordered product"))
        }

        @Test
        fun `check is showNotOrderedProduct gave list of not ordered product`() {
            assertEquals(2, populateProduct!!.numberOfNotOrderedProduct())

            assertTrue(populateProduct!!.showNotOrderedProduct().contains("-1"))
            assertFalse(populateProduct!!.showNotOrderedProduct().contains("3"))
        }
    }

    @Nested
    inner class ShowOrderedProduct {
        @Test
        fun `is return No Ordered product if empty list`() {
            assertEquals(0, emptyProduct!!.numberOfOrderedProduct())
            assertTrue(emptyProduct!!.showOrderedProduct().contains("No Ordered product"))
        }

        @Test
        fun `check is showOrderedProduct gave list of ordered product`() {
            assertEquals(2, populateProduct!!.numberOfOrderedProduct())

            assertTrue(populateProduct!!.showOrderedProduct().contains("1"))
            assertTrue(populateProduct!!.showOrderedProduct().contains("3"))
            assertFalse(populateProduct!!.showOrderedProduct().contains("-1"))
        }
    }

    @Nested
    inner class DeleteProductTest {
        @Test
        fun `is return false when u delete empty list`() {
            assertEquals(0, emptyProduct!!.numberOfProduct())
            assertFalse(emptyProduct!!.deleteProduct(1))
        }

        @Test
        fun `check is deleteProduct Work`() {
            assertEquals(4, populateProduct!!.numberOfProduct())
            val result = populateProduct!!.deleteProduct(3)

            assertTrue(result)
            assertEquals(3, populateProduct!!.numberOfProduct())
            val result2 = populateProduct!!.searchByCriteria("id", 3)
            assertTrue(result2.isEmpty())
        }

        @Test
        fun `is return false when u  delete  product with wrong id `() {
            assertEquals(4, populateProduct!!.numberOfProduct())
            val result2 = populateProduct!!.searchByCriteria("id", 4)
            assertTrue(result2.isEmpty())
            val result = populateProduct!!.deleteProduct(4)
            assertFalse(result)
        }
    }

    @Nested
    inner class UpdateTest {
        @Test
        fun `is return false when u update empty list`() {
            assertEquals(0, emptyProduct!!.numberOfProduct())
            assertFalse(emptyProduct!!.updateProduct(1, "poko", 128, 233.6))
        }

        @Test
        fun `is return false when u  update  product with wrong id`() {
            val result1 = populateProduct!!.showByCriteria("id", 4)
            assertTrue(result1.contains("no product with 4 element in id"))
            assertFalse(populateProduct!!.updateProduct(4, "poko", 128, 233.6))
            populateProduct!!.updateProduct(4, "poko", 128, 233.6)
            val result2 = populateProduct!!.showByCriteria("id", 4)

            assertTrue(result2.contains("no product with 4 element in id"))
        }

        @Test
        fun `check is update order Work`() {
            val result1 = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result1.contains("lenovo"))
            assertTrue(populateProduct!!.updateProduct(3, "poko", 128, 233.6))
            populateProduct!!.updateProduct(3, "poko", 128, 233.6)
            val result2 = populateProduct!!.showByCriteria("id", 3)

            assertTrue(result2.contains("poko"))
        }
    }

    @Nested
    inner class CheckOfNumberTest {
        @Test
        fun `is return false when u checkOfNumber of  empty list`() {
            assertFalse(emptyProduct!!.checkOfNumberAllProduct())
            assertEquals(0, emptyProduct!!.numberOfProduct())
        }

        @Test
        fun `check is checkOfNumber order  Work`() {
            assertTrue(populateProduct!!.checkOfNumberAllProduct())
            assertEquals(4, populateProduct!!.numberOfProduct())
        }
    }

    @Nested
    inner class CheckOfNumberNotStoredProduct {
        @Test
        fun `is return false when u run checkOfNumberNotStoredProduct of  empty list`() {
            assertFalse(emptyProduct!!.checkOfNumberNotStoredProduct())
            assertEquals(0, emptyProduct!!.numberOfNotOrderedProduct())
        }

        @Test
        fun `check is checkOfActiveNumber order  Work`() {
            assertTrue(populateProduct!!.checkOfNumberNotStoredProduct())
            assertEquals(2, populateProduct!!.numberOfNotOrderedProduct())
        }
    }

    @Nested
    inner class CheckOfNumberStoredProductTest {
        @Test
        fun `is return false when u run checkOfNumberStoredProductTest of  empty list`() {
            assertFalse(emptyProduct!!.checkOfNumberStoredProduct())
            assertEquals(0, emptyProduct!!.numberOfProduct())
        }

        @Test
        fun `check is checkOfNumberStoredProductTest order  Work`() {
            assertTrue(populateProduct!!.checkOfNumberStoredProduct())
            assertEquals(2, populateProduct!!.numberOfOrderedProduct())
        }
    }

    @Nested
    inner class IsValidIdAllTypeTest {
        @Test
        fun `is return false when u check Of IsValId of  empty list`() {
            assertFalse(emptyProduct!!.isValidID(1))
        }

        @Test
        fun `check  if checkOfIsValId product  Work`() {
            assertTrue(populateProduct!!.isValidID(1))
            val result1 = populateProduct!!.showByCriteria("id", 1)
            assertTrue(result1.contains("1"))
        }

        @Test
        fun `check if checkOfIsValId order  give false if u input wrong  id`() {
            assertFalse(populateProduct!!.isValidID(5))
            val result1 = populateProduct!!.searchByCriteria("id", 5)
            assertTrue(result1.isEmpty())
        }

        @Test
        fun `is return false when u check isValidNotStoredProductID of  empty list`() {
            assertFalse(emptyProduct!!.isValidNotStoredProductID(1))
        }

        @Test
        fun `check is check Of isValidNotStoredProductID order  Work`() {
            assertTrue(populateProduct!!.isValidNotStoredProductID(1))
            val result1 = populateProduct!!.showByCriteria("id", 1)
            assertTrue(result1.contains("-1"))
        }

        @Test
        fun `check if isValidNotStoredProductID order  give false if u input wrong active id`() {
            assertFalse(populateProduct!!.isValidNotStoredProductID(3))
            val result1 = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result1.contains("orderID=3"))
        }
    }

    @Nested
    inner class CheckIsValidIdAllTypeTest {
        @Test
        fun `is return false when u checkOfIsValId of  empty list`() {
            assertFalse(emptyProduct!!.checkIsValidProductID(1))
        }

        @Test
        fun `check is checkOfIsValId of product  Work`() {
            assertTrue(populateProduct!!.checkIsValidProductID(3))
            val result1 = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result1.contains("3"))
        }

        @Test
        fun `check if checkOfIsValId of order give false when u input  wrong id`() {
            assertFalse(populateProduct!!.checkIsValidProductID(5))
            val result1 = populateProduct!!.searchByCriteria("id", 5)
            assertTrue(result1.isEmpty())
        }

        @Test
        fun `is return false when u checkIsValidNotStoredProductID of  empty list`() {
            assertFalse(emptyProduct!!.checkIsValidNotStoredProductID(1))
        }

        @Test
        fun `check if  checkIsValidNotStoredProductID of not stored product  Work`() {
            assertTrue(populateProduct!!.checkIsValidNotStoredProductID(1))
            val result1 = populateProduct!!.showByCriteria("id", 1)
            assertTrue(result1.contains("-1"))
        }

        @Test
        fun `check if checkIsValidNotStoredProductID of order give false when u input  wrong  not stored product id`() {
            assertFalse(populateProduct!!.checkIsValidNotStoredProductID(3))
            val result1 = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result1.contains("orderID=3"))
        }
    }

    @Nested
    inner class AddProductToOrderTest {
        @Test
        fun `check is addProductToOrder work`() {
            assertFalse(populateProduct!!.checkIsOrderHasProducts(0))
            populateProduct!!.addProductToOrder(0, 0)
            assertTrue(populateProduct!!.checkIsOrderHasProducts(0))
            val result1 = populateProduct!!.showByCriteria("id", 0)
            assertTrue(result1.contains("orderID=0"))
        }

        @Test
        fun `check is addProductToOrder give false if product already exist `() {
            val result1 = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result1.contains("orderID=3"))
            assertFalse(populateProduct!!.addProductToOrder(3, 0))
        }
    }

    @Nested
    inner class ShowProductByOrderTest {
        @Test
        fun `check is showProductByOrderTest work`() {
            assertTrue(populateProduct!!.checkIsOrderHasProducts(3))
            val result = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result.contains("orderID=3"))
            assertTrue(result.contains("lenovo"))
            val result2 = populateProduct!!.showProductByOrder(3)
            assertTrue(result2.contains("lenovo"))
        }

        @Test
        fun `check is showProductByOrderTest give No stored product in order with id $seacrhOrderID if orderId wrong`() {
            assertFalse(populatedOrders!!.isValidID(5))

            val result2 = populateProduct!!.showProductByOrder(5)
            assertTrue(result2.contains("No stored product in order with id 5"))
        }
    }

    @Nested
    inner class CountProductSumPriceInOrderTest {
        @Test
        fun `check is countProductSumPriceInOrder work`() {
            populateProduct!!.addProductToOrder(0, 3)
            assertTrue(populateProduct!!.checkIsOrderHasProducts(3))
            val result = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result.contains("orderID=3"))
            assertTrue(result.contains("lenovo"))
            assertTrue(result.contains("250.5"))
            val result3 = populateProduct!!.showByCriteria("id", 0)
            assertTrue(result3.contains("orderID=3"))
            assertTrue(result3.contains("iphone"))
            assertTrue(result.contains("250.5"))
            val result2 = populateProduct!!.showProductByOrder(3)
            assertTrue(result2.contains("lenovo"))
            assertTrue(result3.contains("iphone"))
            val result5 = populateProduct!!.countProductSumPriceInOrder(3)
            assertTrue(result5.contains("Sum of product price by order with id 3 = 501"))
        }
    }

    @Nested
    inner class DeleteChoseProductsFromOrderTest {
        @Test
        fun `check is deleteChosedProductsFromOrder work`() {
            assertTrue(populateProduct!!.checkIsOrderHasProducts(3))
            val result = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result.contains("orderID=3"))
            assertTrue(result.contains("lenovo"))
            assertTrue(result.contains("250.5"))
            assertTrue(populateProduct!!.deleteChosenProductsFromOrder(3, 3))
            assertFalse(populateProduct!!.checkIsOrderHasProducts(3))
            val result2 = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result2.contains("orderID=-1"))
        }

        @Test
        fun `check is delateChosedProductsFromOrder give false if order no have product `() {
            assertFalse(emptyProduct!!.checkIsOrderHasProducts(0))
            val result = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result.contains("orderID=3"))
            assertTrue(result.contains("lenovo"))
            assertTrue(result.contains("250.5"))
            assertFalse(populateProduct!!.deleteChosenProductsFromOrder(0, 3))
            assertFalse(populateProduct!!.checkIsOrderHasProducts(0))
            val result2 = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result2.contains("orderID=3"))
        }
    }

    @Nested
    inner class SortProductByCategoryTest {
        @Test
        fun `check is sortProductByCategory work`() {
            val result = populateProduct!!.showProduct()
            populateProduct!!.sortProductByCategory("id")
            val result2 = populateProduct!!.showProduct()
            assertEquals(result, result2)
            populateProduct!!.sortProductByCategory("name")
            val result3 = populateProduct!!.showProduct()
            assertNotEquals(result, result3)
            populateProduct!!.sortProductByCategory("memory")
            val result4 = populateProduct!!.showProduct()
            assertEquals(result4, result)
            populateProduct!!.sortProductByCategory("price")
            val result5 = populateProduct!!.showProduct()
            assertNotEquals(result5, result)
            populateProduct!!.sortProductByCategory("zxcz")
            val result6 = populateProduct!!.showProduct()
            assertEquals(result6, result)
        }
    }

    @Nested
    inner class SearchByCriteriaTest {
        @Test
        fun `check is run SearchByCriteria for empty list`() {
            assertEquals(0, emptyProduct!!.numberOfProduct())
            val result = emptyProduct!!.searchByCriteria("id", 1)
            assertTrue(result.isEmpty())
        }

        @Test
        fun `check is run SearchByCriteria for  populatedProduct work correct`() {
            val result1 = populateProduct!!.searchByCriteria("id", 0)
            assertTrue(result1.isNotEmpty())
            val result12 = populateProduct!!.showByCriteria("id", 0)
            assertTrue(result12.contains("productID=0"))
            val result2 = populateProduct!!.searchByCriteria("name", "iphone")
            assertTrue(result2.isNotEmpty())
            val result22 = populateProduct!!.showByCriteria("name", "iphone")
            assertTrue(result22.contains("productName=iphone"))
            val result3 = populateProduct!!.searchByCriteria("memory", 64)
            assertTrue(result3.isNotEmpty())
            val result33 = populateProduct!!.showByCriteria("memory", 64)
            assertTrue(result33.contains("memorySize=64"))
            val result4 = populateProduct!!.searchByCriteria("price", 255.5)
            assertTrue(result4.isNotEmpty())
            val result44 = populateProduct!!.showByCriteria("price", 255.5)
            assertTrue(result44.contains("price=255.5"))
            assertTrue(result44.contains("productID=2"))
        }

        @Test
        fun `check is run SearchByCriteria   with wrong searchElement give empty`() {
            val result = populateProduct!!.searchByCriteria("id", "1")
            assertTrue(result.isEmpty())
            val result2 = populateProduct!!.showByCriteria("id", "1")
            assertTrue(result2.contains("no product with 1 element in id"))
        }

        @Test
        fun `check is run SearchByCriteria   with wrong criteria give empty`() {
            val result = populateProduct!!.searchByCriteria("isadasd", 1)
            assertTrue(result.isEmpty())
            val result2 = populateProduct!!.showByCriteria("isadasd", 1)
            assertTrue(result2.contains("no product with 1 element in isadasd"))
        }
    }

    @Nested
    inner class ShowByCriteriaTest {
        @Test
        fun `check is run showByCriteriaTest for empty list`() {
            assertEquals(0, emptyProduct!!.numberOfProduct())
            val result = emptyProduct!!.showByCriteria("id", 1)
            assertTrue(result.contains("no product with 1 element in id"))
        }

        @Test
        fun `check is run showByCriteriaTest for  populatedProduct work correct`() {
            val result2 = populateProduct!!.searchByCriteria("name", "iphone")
            assertTrue(result2.isNotEmpty())
            val result22 = populateProduct!!.showByCriteria("name", "iphone")
            assertTrue(result22.contains("productName=iphone"))
        }

        @Test
        fun `check is run showByCriteriaTest   with wrong searchElement give empty`() {
            val result2 = populateProduct!!.showByCriteria("id", "1")
            assertTrue(result2.contains("no product with 1 element in id"))
        }

        @Test
        fun `check is run showByCriteriaTest   with wrong criteria give empty`() {
            val result2 = populateProduct!!.showByCriteria("isadasd", 1)
            assertTrue(result2.contains("no product with 1 element in isadasd"))
        }
    }

    @Nested
    inner class CountIsInStockByNameTest {
        @Test
        fun `check if countIsInStockByName for empty list give 0`() {
            assertEquals(0, emptyProduct!!.numberOfProduct())
            assertEquals(0, emptyProduct!!.countIsInStockByName("iphone"))
            val result1 = emptyProduct!!.searchByCriteria("name", "iphone")
            assertTrue(result1.isEmpty())
        }

        @Test
        fun `check if countIsInStockByName for populateProduct  give count of not orderd product by name iphone`() {
            assertEquals(2, populateProduct!!.numberOfNotOrderedProduct())
            assertEquals(2, populateProduct!!.countIsInStockByName("iphone"))
            val result1 = populateProduct!!.searchByCriteria("name", "iphone")
            assertTrue(result1.isNotEmpty())
            val result2 = populateProduct!!.showByCriteria("name", "iphone")
            assertTrue(result2.contains("productName=iphone"))
            assertTrue(result2.contains("orderID=-1"))
        }

        @Test
        fun `check if countIsInStockByName for populateProduct  give count=0 of not orderd product by name honor`() {
            assertEquals(0, populateProduct!!.countIsInStockByName("honor"))
            val result1 = populateProduct!!.searchByCriteria("name", "honor")
            assertTrue(result1.isNotEmpty())
            val result2 = populateProduct!!.showByCriteria("name", "honor")
            assertTrue(result2.contains("productName=honor"))
            assertTrue(result2.contains("orderID=1"))
        }
    }

    @Nested
    inner class CheckIsStockTest {
        @Test
        fun `check if checkIsStockTest for empty list give No product in stock with name SeacrchName`() {
            assertEquals(0, emptyProduct!!.numberOfProduct())
            val result = emptyProduct!!.checkIsStock("iphone")
            assertTrue(result.contains("No product in stock with name iphone"))
        }

        @Test
        fun `check if checkIsStockTest for populateProduct list gave list of product in stock by name`() {
            assertEquals(2, populateProduct!!.numberOfNotOrderedProduct())
            assertEquals(2, populateProduct!!.countIsInStockByName("iphone"))
            val result1 = populateProduct!!.searchByCriteria("name", "iphone")
            assertTrue(result1.isNotEmpty())
            val result2 = populateProduct!!.showByCriteria("name", "iphone")
            assertTrue(result2.contains("productName=iphone"))
            assertTrue(result2.contains("orderID=-1"))
            val result3 = populateProduct!!.checkIsStock("iphone")
            assertTrue(result3.contains("productName=iphone"))
            assertTrue(result3.contains("orderID=-1"))
        }

        @Test
        fun `check if countIsInStockByName for populateProduct  give No product in stock with name SeacrchName  by name honor`() {
            assertEquals(0, populateProduct!!.countIsInStockByName("honor"))
            val result1 = populateProduct!!.searchByCriteria("name", "honor")
            assertTrue(result1.isNotEmpty())
            val result2 = populateProduct!!.showByCriteria("name", "honor")
            assertTrue(result2.contains("productName=honor"))
            assertTrue(result2.contains("orderID=1"))
            val result = populateProduct!!.checkIsStock("honor")
            assertTrue(result.contains("No product in stock with name honor"))
        }

        @Nested
        inner class PersistenceTests {

            @Test
            fun `saving and loading an empty collection in XML doesn't crash app`() {
                // Saving an empty notes.XML file.
                val storingProduct = ProductAPI(XMLSerializer(File("products.xml")))
                storingProduct.store()

                // Loading the empty notes.xml file into a new object
                val loadedProduct = ProductAPI(XMLSerializer(File("products.xml")))
                loadedProduct.load()

                // Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
                assertEquals(0, storingProduct.numberOfProduct())
                assertEquals(0, loadedProduct.numberOfProduct())
                assertEquals(storingProduct.numberOfProduct(), loadedProduct.numberOfProduct())
            }
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't loose data`() {
            // Storing 3 notes to the notes.XML file.
            val storingProduct = ProductAPI(XMLSerializer(File("products.xml")))
            storingProduct.addProduct(notStoredProduct1!!)
            storingProduct.addProduct(storedProduct1!!)
            storingProduct.store()

            // Loading notes.xml into a different collection
            val loadedProduct = ProductAPI(XMLSerializer(File("products.xml")))
            loadedProduct.load()

            // Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(2, storingProduct.numberOfProduct())
            assertEquals(2, loadedProduct.numberOfProduct())
            assertEquals(storingProduct.numberOfProduct(), loadedProduct.numberOfProduct())
            assertEquals(storingProduct.searchByCriteria("id", 0), loadedProduct.searchByCriteria("id", 0))
            assertEquals(storingProduct.searchByCriteria("id", 2), loadedProduct.searchByCriteria("id", 2))
        }
    }
}
