import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)


class Matrix(input:List<String>) {
    val matrix = input.map { it.toCharArray().toMutableList().map { char -> char.toString() }.toMutableList() }

    fun getValue(coordinates: Pair<Int, Int>): String = matrix[coordinates.second][coordinates.first]

    fun setValue(coordinates: Pair<Int, Int>, value: String) {
        matrix[coordinates.second][coordinates.first] = value
    }

    fun allPositions(): List<Pair<Int, Int>> {
        val positions = mutableListOf<Pair<Int, Int>>()
        for ( y in matrix.indices) {
            for (x in matrix[y].indices) {
                positions.add(x to y)
            }
        }
        return positions
    }

    fun horizontalLine(verticalIndex: Int) = matrix[verticalIndex]

    fun findCoordinatesOfValue(value: String): List<Pair<Int,Int>> {
        return matrix.mapIndexed { verticalIndex, strings ->
            verticalIndex to strings.mapIndexedNotNull { horizontalIndex, matrixValue -> horizontalIndex.takeIf { matrixValue == value } }
        }.map {
            (verticalIndex, listOfHorizontalIndex) -> listOfHorizontalIndex.map { it to verticalIndex }
        }.flatten()
    }
}
