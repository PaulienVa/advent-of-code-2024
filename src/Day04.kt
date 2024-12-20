fun main() {

    fun countOccurrencesOfXmas(charIndex: Int, lineIndex: Int, input: List<List<String>>): Int {
        val line = input[lineIndex]
        var count = 0
        if (charIndex >= 1 && lineIndex >= 1 && charIndex < line.size - 1 && lineIndex < input.size - 1) {
            if (
                input[lineIndex - 1][charIndex - 1] == "M" && input[lineIndex - 1][charIndex + 1] == "S" &&
                input[lineIndex + 1][charIndex - 1] == "M" && input[lineIndex + 1][charIndex + 1] == "S"
            ) {
                count++
            }
            if (
                input[lineIndex - 1][charIndex - 1] == "S" && input[lineIndex - 1][charIndex + 1] == "M" &&
                input[lineIndex + 1][charIndex - 1] == "S" && input[lineIndex + 1][charIndex + 1] == "M"
            ) {
                count++
            }
            if (
                input[lineIndex - 1][charIndex - 1] == "M" && input[lineIndex - 1][charIndex + 1] == "M" &&
                input[lineIndex + 1][charIndex - 1] == "S" && input[lineIndex + 1][charIndex + 1] == "S"
            ) {
                count++
            }
            if (
                input[lineIndex - 1][charIndex - 1] == "S" && input[lineIndex - 1][charIndex + 1] == "S" &&
                input[lineIndex + 1][charIndex - 1] == "M" && input[lineIndex + 1][charIndex + 1] == "M"
            ) {
                count++
            }
        }
        return count
    }

    fun countOccurrencesOfChristmas(
        charIndex: Int,
        lineIndex: Int,
        input: List<List<String>>
    ): Int {
        var count = 0
        val line = input[lineIndex]
        if (charIndex >= 3) {
            if (line[charIndex - 1] == "M" && line[charIndex - 2] == "A" && line[charIndex - 3] == "S") {
                count++
            }
            if (lineIndex >= 3 &&
                (input[lineIndex - 1][charIndex - 1] == "M" && input[lineIndex - 2][charIndex - 2] == "A" && input[lineIndex - 3][charIndex - 3] == "S")
            ) {
                count++
            }
            if (lineIndex < input.size - 3 &&
                (input[lineIndex + 1][charIndex - 1] == "M" && input[lineIndex + 2][charIndex - 2] == "A" && input[lineIndex + 3][charIndex - 3] == "S")
            ) {
                count++
            }
        }
        if (lineIndex >= 3) {
            if (input[lineIndex - 1][charIndex] == "M" && input[lineIndex - 2][charIndex] == "A" && input[lineIndex - 3][charIndex] == "S") {
                count++
            }
            if (charIndex < line.size - 3 &&
                (input[lineIndex - 1][charIndex + 1] == "M" && input[lineIndex - 2][charIndex + 2] == "A" && input[lineIndex - 3][charIndex + 3] == "S")
            ) {
                count++
            }
        }

        if (lineIndex < input.size - 3) {
            if (input[lineIndex + 1][charIndex] == "M" && input[lineIndex + 2][charIndex] == "A" && input[lineIndex + 3][charIndex] == "S") {
                count++
            }

            if (lineIndex < input.size - 3 && charIndex < line.size - 3 &&
                (input[lineIndex + 1][charIndex + 1] == "M" && input[lineIndex + 2][charIndex + 2] == "A" && input[lineIndex + 3][charIndex + 3] == "S")  // cross 4
            ) {
                count++
            }
        }

        if ((charIndex < line.size - 3) &&
            (line[charIndex + 1] == "M" && line[charIndex + 2] == "A" && line[charIndex + 3] == "S")
        ) {
            count++
        }
        return count
    }

    fun find(input: List<List<String>>): Int {
        var count = 0

        input.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { charIndex, element ->
                if (element == "X") {
                    count += countOccurrencesOfChristmas(charIndex, lineIndex, input)
                }
            }
        }
        return count
    }

    fun find2(input: List<List<String>>): Int {
        var count = 0
        input.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { charIndex, element ->
                if (element == "A") {
                    count += countOccurrencesOfXmas(charIndex, lineIndex, input)
                }
            }
        }

        return count
    }

    fun part1(input: List<String>): Int {
        return find(input.map { it.toCharArray().toList().map { char -> char.toString() } })
    }

    fun part2(input: List<String>): Int {
        return find2(input.map { it.toCharArray().toList().map { char -> char.toString() } })
    }

    fun refactor1(input: List<String>): Int {
        val matrix = Matrix(input)

        val findCoordinatesOfX = matrix.findCoordinatesOfValue("X")
        return findCoordinatesOfX.sumOf {
            countOccurrencesOfChristmas(it.first, it.second, matrix.matrix)
        }
    }

    fun refactor2(input: List<String>): Int {
        val matrix = Matrix(input)

        val findCoordinatesOfA = matrix.findCoordinatesOfValue("A")
        return findCoordinatesOfA.sumOf {
            countOccurrencesOfXmas(it.first, it.second, matrix.matrix)
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")

//    println("Part 1 with testInput: ${part1(testInput)}")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    "Part 1 ".println()
    part1(input).println()
    "Part 2 ".println()
    part2(input).println()

    "Refactor Part1 ${refactor1(testInput)}".println()
    "Refactor Part1 ${refactor1(input)}".println()
    "Refactor Part1 ${refactor2(input)}".println()
}
