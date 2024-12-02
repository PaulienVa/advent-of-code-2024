import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")

    println(part1(testInput))
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    "Part 1 ".println()
    part1(input).println()
    "Part 2 ".println()
    part2(input).println()
}
