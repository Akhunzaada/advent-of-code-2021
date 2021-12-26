fun main() {
    fun part1(input: List<String>): Int {
        val gamma = buildString {
            for (n in input[0].indices) {
                append(input.countInColumn(n).maxByOrNull { it.value }?.key)
            }
        }
        val epsilon = gamma.invertBinary()
        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun part2(input: List<String>): Int {
        fun reduceInput(input: List<String>, minMax: MinMax): String {
            var reduceList = input
            for (n in input[0].indices) {
                val commonBit = reduceList.countInColumn(n).let {
                    if (minMax == MinMax.MAX) {
                        if ((it['0'] ?: 0) > (it['1'] ?: 0)) '0' else '1'
                    } else {
                        if ((it['1'] ?: 0) < (it['0'] ?: 0)) '1' else '0'
                    }
                }
                reduceList = reduceList.filter { it[n] == commonBit }
                if (reduceList.size == 1) break
            }
            return reduceList.single()
        }
        return reduceInput(input, MinMax.MAX).toInt(2) * reduceInput(input, MinMax.MIN).toInt(2)
    }

    // test if implementation meets criteria from the description
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.countInColumn(n: Int) = this.groupingBy { it[n] }.eachCount()

enum class MinMax {
    MIN, MAX
}
