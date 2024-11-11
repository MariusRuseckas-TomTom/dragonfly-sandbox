package com.tomtom.search.dragonfly

import java.io.File

class Utils {
    companion object {
        @JvmStatic
        fun readFileAsLinesUsingReadLines(fileName: String): List<String> {
            val resource = Utils::class.java.getResource(fileName) ?: throw IllegalArgumentException("file not found")
            return File(resource.toURI()).readLines()
        }
    }
}
