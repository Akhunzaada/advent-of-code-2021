fun main() {
    fun part1(commands: List<Command>): Int {
        var position = 0
        var depth = 0
        commands.forEach {
            when (it.type) {
                "forward" -> position += it.movement
                "down" -> depth += it.movement
                "up" -> depth -= it.movement
            }
        }
        return position * depth
    }

    fun part2(commands: List<Command>): Int {
        var position = 0
        var aim = 0
        var depth = 0
        commands.forEach {
            when (it.type) {
                "forward" -> {
                    position += it.movement
                    depth += aim * it.movement
                }
                "down" -> aim += it.movement
                "up" -> aim -= it.movement
            }
        }
        return position * depth
    }

    // test if implementation meets criteria from the description
    val testInput = readInput("Day02_test").toCommands()
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02").toCommands()
    println(part1(input))
    println(part2(input))
}

private fun List<String>.toCommands(): List<Command> = this.map {
    with (it.split(" ")) {
        Command(this[0], this[1].toInt())
    }
}

data class Command(val type: String, val movement: Int)
