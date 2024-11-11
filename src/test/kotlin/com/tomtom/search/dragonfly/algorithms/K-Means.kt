package com.tomtom.search.dragonfly.algorithms

import com.tomtom.search.dragonfly.Utils
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

internal class KMeansTest {

    @Serializable
    data class Poi(val id: String, val position: Point)

    @Test
    fun simple() {
        val string = Utils.readFileAsLinesUsingReadLines("/algorithms/K-Means.json").joinToString("")
        val data = Json.decodeFromString<List<Poi>>(string)
        listOf(3, 5, 7, 10).forEach { k ->
            repeat(10) {
                KMeans(data.map { it.position }) { a, b ->
                    sqrt((a.lat - b.lat) * (a.lat - b.lat) + (a.lon - b.lon) * (a.lon - b.lon))
                }
                    .clusters(k).toList().also {
                        println("k = $k, converged after ${it.size} iterations")
                        it.last().entries.map { e -> e.key to e.value.sortedBy { v -> v.lat } }.forEach { (centroid, _) ->
                            println("Centroid: $centroid")
                        }
                        println()
                    }
            }
        }
    }
}
