fun main() {

    fun pageOrderingRules(input: List<String>) = input.map { it.split("|") }.map { it[0].toInt() to it[1].toInt() }

    fun updates(input: List<String>) = input.map { it.split(",") }.map { it.map { nr -> nr.toInt() } }

    fun List<Int>.isValidUpdate(
        orderingRules: List<Pair<Int, Int>>,
    ) = this.filterIndexed { index, el ->
        orderingRules.count {
            it.first == el && this.subList(index, this.size).count { rest -> rest == it.second } > 0
        } > 0 && orderingRules.count {
            it.second == el && this.subList(index, this.size).count { rest -> rest == it.first } > 0
        } == 0
    }.size == this.size - 1

    fun extractOrderingRulesAndUpdates(input: List<String>): Pair<List<Pair<Int, Int>>, List<List<Int>>> {
        val splitIndex = input.indexOf("")
        "Line to split at $splitIndex".println()
        val orderingRules = pageOrderingRules(input.subList(0, splitIndex))
        val updates = updates(input.subList(splitIndex + 1, input.size))
        orderingRules.println()
        updates.println()
        return Pair(orderingRules, updates)
    }

    fun part1(input: List<String>): Int {
        val (orderingRules, updates) = extractOrderingRulesAndUpdates(input)

        val correctUpdates = updates.filter { updateRule -> updateRule.isValidUpdate(orderingRules) }
        "Correct Update: $correctUpdates".println()

        val middleOnes = correctUpdates.map { rule -> rule[rule.size / 2] }
        middleOnes.println()
        return middleOnes.sum()
    }

    fun reOrderToBeCorrect(
        updates: MutableList<Int>,
        newList: MutableList<Int>,
        orderingRules: List<Pair<Int, Int>>,
        count: Int
    ): List<Int> {
        val comparator = { a : Int,b: Int -> if( (a to b) in orderingRules ) 1 else if ( (b to a) in orderingRules) -1 else 0 }
        updates.sortWith(comparator)
        return updates
    }

    fun part2(input: List<String>): Int {
        val (orderingRules, updates) = extractOrderingRulesAndUpdates(input)
        val incorrectUpdates = updates.filterNot { updateRule ->
            updateRule.isValidUpdate(orderingRules)
        }

        val reOrder = incorrectUpdates.map { reOrderToBeCorrect(it.toMutableList(), it.toMutableList(), orderingRules, 0) }
//        val reOrder = incorrectUpdates.map { rule -> rule.sortedBy { orderingRules.find { rule -> rule.first == it }  } }
        "Reorder".println()
        reOrder.println()
        reOrder.map { rule -> rule[rule.size / 2] }.println()
        return reOrder.map { rule -> rule[rule.size / 2] }.sum()
    }


// Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")

    println("Part 1 with testInput: ${part1(testInput)}")
//    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

// Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    "Part 1 ".println()
    part1(input).println()
    "Part 2 ".println()
    part2(input).println()

}
