fun main() {
    fun part1(input: List<Int>): Int {
        var count = 0
        for (i in 1 until input.size) {
            if (input[i] > input[i-1]) count++
        }
        return count
    }

    fun part2(input: List<Int>): Int {
        var count = 0
        for (i in 0 until input.size-3) {
            if (input[i] < input[i+3]) count++
        }
        return count
    }

    // test if implementation meets criteria from the description
    val testInput = readInputAsInts("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInputAsInts("Day01")
    println(part1(input))
    println(part2(input))
}
