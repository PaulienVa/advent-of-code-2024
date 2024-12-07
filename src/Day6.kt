import kotlin.math.abs

fun main() {

    val directions = arrayOf(
        (0 to 1), // vertical down
        (0 to -1), // vertical up
        (1 to 0), // horizontal right
        (-1 to 0) // horizontal left
    )

    fun inGrid(xGuard: Int, input: List<String>, yGuard: Int) =
        xGuard < input[0].length || xGuard > -1 || yGuard > -1 || yGuard < input.size

    fun nextUpcomingObstacle(
        obstaclesCoordinates: List<Pair<Int, Int>>,
        direction: Pair<Int, Int>,
        xGuard: Int,
        yGuard: Int
    ) = obstaclesCoordinates.filter { coordinates ->
        when (direction) {
            directions[0] -> {
                coordinates.first == xGuard && coordinates.second >= yGuard
            }

            directions[1] -> {
                coordinates.first == xGuard && coordinates.second <= yGuard
            }

            directions[2] -> {
                coordinates.second == yGuard && coordinates.first >= xGuard
            }

            else -> coordinates.second == yGuard && coordinates.first <= xGuard
        }
    }.minByOrNull { if (xGuard == it.first) abs(yGuard - it.second) else abs(xGuard - it.first) }

    fun walkUntilNextObstacle(
        coordinates: Pair<Int, Int>,
        xGuard: Int,
        yGuard: Int,
        direction: Pair<Int, Int>
    ): Triple<Pair<Int, Int>, Pair<Int, Int>, MutableSet<Pair<Int, Int>>> {
        var xGuard1 = xGuard
        var yGuard1 = yGuard
        var direction1 = direction
        val visitedPositions = mutableSetOf<Pair<Int, Int>>()
        if (coordinates.first == xGuard1) {
            if (coordinates.second <= yGuard1) {
                for (y in yGuard1 downTo coordinates.second + 1) {
                    visitedPositions.add(xGuard1 to y)
                }
                direction1 = (1 to 0)
//                println("Now walking up")
                yGuard1 = coordinates.second + 1
            } else {
                for (y in yGuard1..<coordinates.second) {
                    visitedPositions.add(xGuard1 to y)
                }
//                println("Now walking down")
                direction1 = (-1 to 0)
                yGuard1 = coordinates.second - 1
            }
        } else if (coordinates.second == yGuard1) {
            if (coordinates.first <= xGuard1) {
                for (x in xGuard1 downTo coordinates.first + 1) {
                    visitedPositions.add(x to yGuard1)
                }
//                println("Now walking left")
                direction1 = (0 to -1)
                xGuard1 = coordinates.first + 1
            } else {
                for (x in xGuard1..<coordinates.first) {
                    visitedPositions.add(x to yGuard1)
                }
//                println("Now walking right")
                direction1 = (0 to 1)
                xGuard1 = coordinates.first - 1
            }
        }
        return Triple(direction1, xGuard1 to yGuard1, visitedPositions)
    }

    fun leaveTheGrid(
        direction: Pair<Int, Int>,
        yGuard: Int,
        visitedPositions: MutableSet<Pair<Int, Int>>,
        xGuard: Int,
        input: List<String>
    ): Unit {
        if (direction.second == -1) {
            for (y in 0..yGuard) {
                visitedPositions.add(xGuard to y)
            }
        } else if (direction.second == 1) {
            for (y in yGuard..<input.size) {
                visitedPositions.add(xGuard to y)
            }
        } else if (direction.first == -1) {
            for (x in 0..xGuard) {
                visitedPositions.add(x to yGuard)
            }
        } else if (direction.first == 1) {
            for (x in xGuard..<input[0].length) {
                visitedPositions.add(x to yGuard)
            }
        }
    }

    fun findTheGuardRoutes(input: List<String>): MutableSet<Pair<Int, Int>> {
        val map = Matrix(input)
        var (xGuard, yGuard) = map.findCoordinatesOfValue("^")[0]
        val obstaclesCoordinates = map.findCoordinatesOfValue("#")
        val visitedPositions = mutableSetOf(Pair(xGuard, yGuard))
        var direction = (0 to -1)

        while (inGrid(xGuard, input, yGuard)) {
            val coordinates = nextUpcomingObstacle(obstaclesCoordinates, direction, xGuard, yGuard)

            if (coordinates != null) {
//                println("Encountering obstacle with coordinates $coordinates and guard position: ($xGuard, $yGuard) ")
                val (newDirection, newGuardPosition, newlyVisitedPositions) = walkUntilNextObstacle(
                    coordinates,
                    xGuard,
                    yGuard,
                    direction
                )
                direction = newDirection
                xGuard = newGuardPosition.first
                yGuard = newGuardPosition.second
                visitedPositions.addAll(newlyVisitedPositions)
            } else {
                leaveTheGrid(direction, yGuard, visitedPositions, xGuard, input)
                break
            }
        }
        return visitedPositions
    }

    fun findTheGuardRoutesAndLoops(input: List<String>, obstaclesCoordinates: List<Pair<Int, Int>>, start: Pair<Int, Int>): Boolean {
//        val map = Matrix(input)
        var xGuard = start.first
        var yGuard = start.second
        val turns = mutableSetOf<Pair<Pair<Int,Int>, Pair<Int, Int>>>()
        val visitedPositions = mutableSetOf<Pair<Int, Int>>()
        var direction = (0 to -1)
        var looping = false

        while (inGrid(xGuard, input, yGuard)) {
            val coordinates = nextUpcomingObstacle(obstaclesCoordinates, direction, xGuard, yGuard)

            if (coordinates != null) {
//                println("Encountering obstacle with coordinates $coordinates and guard position: ($xGuard, $yGuard) ")
                val (newDirection, newGuardPosition, newlyVisitedPositions) = walkUntilNextObstacle(
                    coordinates,
                    xGuard,
                    yGuard,
                    direction
                )
                if(newGuardPosition to newDirection in turns) {
                    looping = true
                    break
                } else {
                    turns.add(newGuardPosition to newDirection)
                    direction = newDirection
                    xGuard = newGuardPosition.first
                    yGuard = newGuardPosition.second
                    visitedPositions.addAll(newlyVisitedPositions)
                }
            } else {
                leaveTheGrid(direction, yGuard, visitedPositions, xGuard, input)
                break
            }
        }
        return looping
    }

    fun part1(input: List<String>): MutableSet<Pair<Int, Int>> {
        return findTheGuardRoutes(input)
    }

    fun part2(input: List<String>, visitedPositions: MutableSet<Pair<Int, Int>>): Int {
        val map = Matrix(input)
        var (xGuard, yGuard) = map.findCoordinatesOfValue("^")[0]
        var obstaclesCoordinates = map.findCoordinatesOfValue("#").toMutableList()
        val newObstacles = mutableSetOf<Pair<Int, Int>>()

        for (visitedPosition in visitedPositions) {
            obstaclesCoordinates.add(visitedPosition)
//            "Setting obstacle at $visitedPosition".println()
            if (findTheGuardRoutesAndLoops(input, obstaclesCoordinates, xGuard to yGuard)) {
//                "Validating obstacle at $visitedPosition".println()
                newObstacles.add(visitedPosition)
            }
            obstaclesCoordinates.remove(visitedPosition)
        }

        newObstacles.println()

        return newObstacles.size
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")

//    println("Part 1 with testInput: ${part1(testInput)}")
    val testVisitedPostions = part1(testInput)
    check(testVisitedPostions.size == 41)
    check(part2(testInput, testVisitedPostions) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
//    "Part 1 ".println()
    val realVisitedPostions = part1(input)
    realVisitedPostions.println()
    "Part 2 ".println()
    part2(input, realVisitedPostions).println()

}
