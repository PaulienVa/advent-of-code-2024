import kotlin.math.abs

fun main() {

    fun List<Int>.isAscendingOrDescending() = this.sorted() == this || this.sortedDescending() == this

    fun List<Int>.safeOrNot(): Boolean {
        if (isAscendingOrDescending()) {
            val differences = (this.size - 1 downTo 1).map { abs(this[it] - this[it - 1]) }
            return differences.all { it in 1..3 }
        } else {
            return false
        }
    }

    fun part1(input: List<String>): Int {
        return input.map { it.split(" ").map { nr -> nr.toInt() } }.filter { it.safeOrNot() }.size
    }

    fun removeOneAndCheckIfSafe(records: List<List<Int>>): Int {
        var count = 0
        records.forEach {
            if (it.safeOrNot()) {
                count++
            } else {
                var tempList = it.toMutableList()
                run escape@ {
                    it.forEachIndexed { index, _ ->
                        if (index <= tempList.size) {
                            tempList.removeAt(index)
                            if (tempList.safeOrNot()) {
                                count++
                                return@escape
                            } else {
                                tempList = it.toMutableList()
                            }
                        }
                    }
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val records = input.map { it.split(" ").map { nr -> nr.toInt() } }
        return removeOneAndCheckIfSafe(records)
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")

    println(part1(testInput))
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    "Part 1 ".println()
    part1(input).println()
    "Part 2 ".println()
    part2(input).println()
}
