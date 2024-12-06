package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import ie.setu.models.Product
import models.Order
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception
import kotlin.Throws
/**
 * A serializer implementation for reading and writing objects in XML format.
 *
 * @property file The file to read from or write to.
 * @constructor Creates an instance of `XMLSerializer` with the specified file.
 */
class XMLSerializer(private val file: File) : Serializer {
    /**
     * Reads objects from the specified XML file.
     * Deserializes the contents of the file into Kotlin objects.
     *
     * @return The deserialized object.
     * @throws Exception If there is an error during the deserialization process.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Order::class.java, Product::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Writes objects to the specified XML file.
     * Serializes the provided object into XML format and writes it to the file.
     *
     * @param obj The object to serialize and write to the file.
     * @throws Exception If there is an error during the serialization process.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
