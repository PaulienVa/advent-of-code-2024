fun main() {


    fun part1(input: List<String>): Int {
        val map = Matrix(input)
        var (xGuard, yGuard) = map.findCoordinatesOfValue("^")[0]
        val obstaclesCoordinates = map.findCoordinatesOfValue("#")
        val visitedPositions = mutableSetOf(Pair(xGuard, yGuard))
        var direction = (0 to -1)

        val directions = arrayOf(
            (0 to 1), // vertical down
            (0 to -1), // vertical up
            (1 to 0), // horizontal right
            (-1 to 0) // horizontal left
        )

        while(xGuard < input[0].length || xGuard > -1 || yGuard > -1 || yGuard < input.size) {
            val coordinates = obstaclesCoordinates.find { coordinates ->
                when (direction) {
                    directions[0] -> { coordinates.first == xGuard && coordinates.second >= yGuard }
                    directions[1] -> { coordinates.first == xGuard && coordinates.second <= yGuard }
                    directions[2] -> { coordinates.second == yGuard && coordinates.first >= xGuard }
                    else -> coordinates.second == yGuard && coordinates.first <= xGuard
                }
            }
            if (coordinates != null) {
                if (coordinates.first == xGuard) {
                    if (coordinates.second <= yGuard) {
                        for (y in yGuard downTo  coordinates.second + 1  ) { visitedPositions.add(xGuard to y) }
                        direction = (1 to 0)
                        yGuard = coordinates.second + 1
                    }
                    else {
                        for (y in yGuard..<coordinates.second) { visitedPositions.add(xGuard to y) }
                        direction = (-1 to 0)
                        yGuard = coordinates.second - 1
                    }
                }
                else if (coordinates.second == yGuard) {
                    if (coordinates.first <= xGuard) {
                        for (x in xGuard downTo  coordinates.first + 1) { visitedPositions.add(x to yGuard) }
                        direction = (0 to -1)
                        xGuard = coordinates.first + 1
                    }
                    else {
                        for (x in xGuard..<coordinates.first) { visitedPositions.add(x to yGuard) }
                        direction = (0 to 1)
                        xGuard = coordinates.first - 1
                    }
                }
            } else {
                if (direction.second == -1) {
                    for (y in 0 .. yGuard) { visitedPositions.add(xGuard to y) }
                    break
                } else if (direction.second == 1) {
                    for (y in yGuard..<input.size) { visitedPositions.add(xGuard to y) }
                    break
                } else if (direction.first == -1) {
                    for (x in 0 .. xGuard) { visitedPositions.add(x to yGuard) }
                    break
                } else if (direction.first  == 1) {
                    for (x in xGuard..<input[0].length) { visitedPositions.add(x to yGuard) }
                    break
                }
            }
        }

        visitedPositions.size.println()
        return visitedPositions.size
    }

    fun part2(input: List<String>): Int {
        return 0
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")

//    println("Part 1 with testInput: ${part1(testInput)}")
    check(part1(testInput) == 41)
    check(part2(testInput) == 0)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    "Part 1 ".println()
    part1(input).println()
    "Part 2 ".println()
    part2(input).println()

}
