import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val columns = input.map { line -> line.split("   ")}
        val sortedColumn1 = columns.map { it[0].toInt() }.sorted()
        val sortedColumn2 = columns.map { it[1].toInt() }.sorted()

        val sumOfDistances = sortedColumn1
            .mapIndexed { index, el -> el to sortedColumn2[index] }
            .sumOf { abs(it.first - it.second) }
        return sumOfDistances
    }

    fun part2(input: List<String>): Int {
        val columns = input.map { line -> line.split("   ")}
        val column1 = columns.map { it[0].toInt() }
        val column2 = columns.map { it[1].toInt() }

        return column1.map { left -> left to column2.count { left == it } }.sumOf { it.first * it.second }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
