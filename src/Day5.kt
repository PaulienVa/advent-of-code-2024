fun main() {

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }



    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")

    println("Part 1 with testInput: ${part1(testInput)}")
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    "Part 1 ".println()
    part1(input).println()
    "Part 2 ".println()
    part2(input).println()

}
