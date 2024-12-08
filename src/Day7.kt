import java.math.BigDecimal
import kotlin.math.abs

fun main() {
    fun tryOperation(possibleOperations: List<(Long, Long) -> Long>, toVerify: Long, sum: Long, rest: List<Long>): Boolean {
        if (sum == toVerify) {
            return true
        } else if (sum > toVerify) {
            return false
        } else if (rest.isEmpty()) {
            return false
        } else {
            return possibleOperations.any { operation -> tryOperation(possibleOperations, toVerify, operation.invoke(sum, rest[0]), rest.drop(1) ) }
        }
    }
    fun part1(input: List<String>): Long {
        val addition: (Long, Long) -> Long = { x, y -> x + y }
        val multiplication: (Long, Long) -> Long = { x, y -> x * y }
        val possibleOperations = listOf(
            addition, multiplication
        )

        val calibrations = input.map { it.split(": ") }
            .map { it[0].toLong() to it[1].split(" ").map { it.toLong() } }
            .filter {
                tryOperation(possibleOperations, it.first, it.second.first(), it.second.drop(1))
            }.sumOf { it.first }
        return calibrations
    }

    fun part2(input: List<String>): Int {
      return 0
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")

//    println("Part 1 with testInput: ${part1(testInput)}")

    check(part1(testInput) == 3749L)
//    check(part2(testInput ) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    "Part 1 ".println()
    part1(input).println()
    "Part 2 ".println()
    part2(input, ).println()

}
