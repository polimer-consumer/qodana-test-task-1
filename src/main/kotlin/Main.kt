package com.polimerconsumer

import java.util.*

fun main() {
    val n = Integer.parseInt(readlnOrNull())
    val names = mutableListOf<String>()
    val graph = Array(26) { IntArray(26) { 0 } }

    for (i in 0 until n) {
        readlnOrNull()?.let { names.add(it) }
    }

    val visited = IntArray(27)
    val result = LinkedList<Char>()

    if (!buildGraph(names, graph) || !topologicalSort(graph, visited, result)) {
        println("Impossible")
    } else {
        result.forEach { print(it) }
    }
}

fun buildGraph(names: List<String>, graph: Array<IntArray>): Boolean {
    for (i in 1 until names.size) {
        val firstName = names[i - 1]
        val secondName = names[i]
        var isDifferent = false
        val shorterLength = minOf(firstName.length, secondName.length)

        for (j in 0 until shorterLength) {
            val from = firstName[j]
            val to = secondName[j]
            if (from != to) {
                graph[from - 'a'][to - 'a'] = 1
                isDifferent = true
                break
            }
        }

        if (!isDifferent && firstName.length > secondName.length) {
            return false
        }
    }

    return true
}

fun topologicalSort(graph: Array<IntArray>, visited: IntArray, result: LinkedList<Char>): Boolean {
    fun dfs(u: Int): Boolean {
        visited[u] = 1

        for (v in 0 until 26) {
            if (graph[u][v] == 1) {
                when (visited[v]) {
                    1 -> return false
                    0 -> if (!dfs(v)) return false
                }
            }
        }
        visited[u] = 2
        result.addFirst(u.toChar() + 'a'.code)
        return true
    }

    for (c in 0 until 26) {
        if (visited[c] == 0 && !dfs(c)) {
            return false
        }
    }

    return true
}
