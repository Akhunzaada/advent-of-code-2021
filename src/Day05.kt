fun main() {
    fun part1(input: HydrothermalVents): Int {
        return intersections(input.lines.filter { it.isStraightLine() }, input.maxX, input.maxY)
    }

    fun part2(input: HydrothermalVents): Int {
        return intersections(input.lines, input.maxX, input.maxY)
    }

    // test if implementation meets criteria from the description
    val testInput = HydrothermalVents.load("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = HydrothermalVents.load("Day05")
    println(part1(input))
    println(part2(input))
}

fun intersections(lines: List<Line>, maxX: Int, maxY: Int): Int {
    val grid = Array(maxX+1) { IntArray(maxY+1) }
    lines.forEach {
        if (it.isStraightLine()) {
            it.iteratorX().forEach { x ->
                it.iteratorY().forEach { y ->
                    grid[x][y]++
                }
            }
        } else if (it.isDiagonalLine()) {
            val itX = it.iteratorX()
            val itY = it.iteratorY()
            while (itX.hasNext() && itY.hasNext()) {
                grid[itX.nextInt()][itY.nextInt()]++
            }
        }
    }
    return grid.sumOf { it.count { it > 1 } }
}

data class HydrothermalVents(val lines: List<Line>, val maxX: Int, val maxY: Int) {
    companion object {
        fun load(name: String): HydrothermalVents {
            var maxX = 0
            var maxY = 0
            val lines = readInput(name).map {
                val (from, to) = it.split(" -> ")
                val fromPoint = with (from.split(",")) { Point(this[0].toInt(), this[1].toInt()) }
                val toPoint = with (to.split(",")) { Point(this[0].toInt(), this[1].toInt()) }
                maxX = maxOf(maxX, fromPoint.x, toPoint.x)
                maxY = maxOf(maxY, fromPoint.y, toPoint.y)
                Line(fromPoint, toPoint)
            }
            return HydrothermalVents(lines, maxX, maxY)
        }
    }
}

data class Line(val from: Point, val to: Point) {

    fun isStraightLine() = from.x == to.x || from.y == to.y

    fun isDiagonalLine() = kotlin.math.abs(getAngle()) % 45.0 == 0.0

    fun getAngle(): Double {
        val angle = kotlin.math.atan2((from.y - to.y).toDouble(), (from.x - to.x).toDouble())
        return angle * 180 / kotlin.math.PI
    }

    fun iteratorX() =  (if (from.x < to.x) from.x.rangeTo(to.x) else from.x.downTo(to.x)).iterator()

    fun iteratorY() = (if (from.y < to.y) from.y.rangeTo(to.y) else from.y.downTo(to.y)).iterator()
}

data class Point(val x: Int, val y: Int)