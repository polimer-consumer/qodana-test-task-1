import com.polimerconsumer.buildGraph
import com.polimerconsumer.topologicalSort
import com.sun.tools.javac.Main
import java.io.File
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OrderTests {
    @Test
    fun testValidOrder() {
        val names = listOf("apple", "banana", "cherry", "date", "elderberry")
        val graph = Array(27) { IntArray(27) }
        val visited = IntArray(27)
        val result = LinkedList<Char>()
        assertTrue(buildGraph(names, graph), "Graph should be built successfully")
        assertTrue(topologicalSort(graph, visited, result), "Cycle should be detected")
    }

    @Test
    fun testCycle() {
        val names = listOf("apple", "banana", "cranberry", "date", "elderberry", "ananas")
        val graph = Array(27) { IntArray(27) }
        val visited = IntArray(27)
        val result = LinkedList<Char>()
        assertTrue(buildGraph(names, graph), "Graph should be built")
        assertFalse(topologicalSort(graph, visited, result), "Should not be an ordering detected")
    }

    @Test
    fun testPrefix() {
        val names = listOf("apple", "app")
        val graph = Array(27) { IntArray(27) }
        assertFalse(buildGraph(names, graph), "Graph should not be built")
    }

    @Test
    fun testFullAlphabet() {
        val classLoader = Main::class.java.classLoader
        val file = File(classLoader.getResource("allwords.txt")!!.file)
        val names = file.readLines().toList()

        val graph = Array(27) { IntArray(27) }
        val visited = IntArray(27)
        val result = LinkedList<Char>()

        assertTrue(buildGraph(names, graph), "Graph should be built")
        assertTrue(topologicalSort(graph, visited, result), "Should be able to topologically sort the naturally ordered words.")
        val expectedOrder = ('a'..'z').toList()
        val resultOrder = result.toList()
        assertEquals(expectedOrder, resultOrder, "The result should be equal english alphabetical order.")
    }
}