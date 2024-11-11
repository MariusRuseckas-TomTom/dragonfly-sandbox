package com.tomtom.search.dragonfly.algorithms

import kotlin.random.Random

const val EPSILON = 0.0001

class KMeans(private val points: List<Point>, val distance: (Point, Point) -> Double) {

    fun clusters(k: Int): Sequence<Map<Point, List<Point>>> = sequence {
        var centroids = kmeansPlusPlus(k).associateWith { emptyList<Point>() }

        do {
            yield(centroids)

            val newAssignments = points.groupBy { position ->
                centroids.minByOrNull { (centroid, _) -> distance(centroid, position) }!!.key
            }

            val newCentroids = newAssignments.mapValues { (_, positions) -> positions.averagePosition() }

            val converged = centroids.all { (oldCentroid, _) ->
                newCentroids[oldCentroid]?.let { newCentroid -> distance(oldCentroid, newCentroid) < EPSILON } ?: false
            }

            centroids = newCentroids.map { it.value to newAssignments[it.key]!! }.toMap()
        } while (!converged)
    }

    private fun kmeansPlusPlus(k: Int): List<Point> {
        val centroids = mutableListOf<Point>()

        // Choose the first centroid at random, let it be the first element
        centroids.add(points.first())

        while (centroids.size < k) {
            // Compute D(x) for each point x in data set
            val distances = points.map { point ->
                centroids.minByOrNull { centroid -> distance(centroid, point) }
                    ?.let { centroid -> distance(point, centroid) } ?: Double.POSITIVE_INFINITY
            }

            // Compute the probability of choosing each point as the next centroid
            val totalDistance = distances.sum()
            val probabilities = distances.map { it * it / totalDistance }

            // Choose the next centroid with probability proportional to D(x)^2
            val rand = Random.nextDouble()
            var cumProb = 0.0

            for ((index, prob) in probabilities.withIndex()) {
                cumProb += prob
                if (rand < cumProb) {
                    centroids.add(points[index])
                    break
                }
            }
        }
        return centroids
    }

    private fun List<Point>.averagePosition(): Point {
        return Point(
            this.map { it.lat }.average(),
            this.map { it.lon }.average()
        )
    }
}
