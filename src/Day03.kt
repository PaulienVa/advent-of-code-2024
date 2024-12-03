import kotlin.math.abs

fun main() {

    fun findInstructions(input: List<String>): List<Int> {
        val regex = "mul\\(([0-9]{1,3}),([0-9]{1,3})\\)".toRegex()
        val sumOfInstructions = input.map {
            val groupValues = regex.findAll(it)
            groupValues!!.map { it.destructured }.map { it.component1().toInt() to it.component2().toInt() }
                .map { it.first * it.second }.sum()
        }
        return sumOfInstructions
    }

    fun part1(input: List<String>): Int {
        val sumOfInstructions = findInstructions(input)
        return sumOfInstructions.sum()
    }
    fun part2(input: List<String>): Int {
        val toRemoveRegex = "don't\\(\\)(.*?)do\\(\\)|".toRegex()

        val cleanedInput = toRemoveRegex.replace(input.joinToString(), "")
        val sumOfInstructions = findInstructions(listOf(cleanedInput))
        sumOfInstructions.println()
        return sumOfInstructions.sum()
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")

    println(part1(testInput))
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    "Part 1 ".println()
    part1(input).println()
    "Part 2 ".println()
    part2(input).println()
}
