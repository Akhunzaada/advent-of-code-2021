fun main() {
    fun part1(game: BingoGame): Int {
        return game.play()?.entries?.first()?.value?.let {
            it.board.getScore() * it.number
        } ?: 0
    }

    fun part2(game: BingoGame): Int {
        return game.play(true)?.entries?.last()?.value?.let {
            it.board.getScore() * it.number
        } ?: 0
    }

    // test if implementation meets criteria from the description
    val testGame = BingoGame.load("Day04_test")
    check(part1(testGame.clone()) == 4512)
    check(part2(testGame.clone()) == 1924)

    val game = BingoGame.load("Day04")
    println(part1(game.clone()))
    println(part2(game.clone()))
}

data class BingoGame(val drawNumbers: List<Int>,
                     val boards: MutableList<Board>) {

    fun play(full: Boolean = false): Map<Int, Winner>? {
        val leaderboard = linkedMapOf<Int, Winner>()
        drawNumbers.forEachIndexed { i, num ->
            boards.forEachIndexed { j, board ->
                if (leaderboard.contains(j).not()) {
                    board.drawnNumber(num)
                    if (i >= 4 && board.checkWin()) {
                        if (full) {
                            leaderboard[j] = Winner(board, num)
                            if (leaderboard.size == boards.size) {
                                return leaderboard
                            }
                        } else {
                            return mapOf(j to Winner(board, num))
                        }
                    }
                }
            }
        }
        return null
    }

    fun clone() = copy(boards = boards.map { it.clone() }.toMutableList())

    companion object {
        fun load(name: String): BingoGame {
            with (openFile(name).bufferedReader()) {
                val drawNumbers = this.readLine().split(',').map { it.toInt() }
                val boards = this.readLines().asSequence().filter { it.isNotEmpty() }.map {
                    it.trim().split("\\s+".toRegex()).map { it.toInt() }
                }.flatten().toList().chunked(25).map {
                    Board(it.toMutableList(), IntArray(5), IntArray(5))
                }.toMutableList()
                return BingoGame(drawNumbers, boards)
            }
        }
    }
}

data class Board(val numbers: MutableList<Int>,
                 val drawnRows: IntArray,
                 val drawnColumns: IntArray) {

    fun drawnNumber(num: Int) {
        if (numbers.contains(num)) {
            val index = numbers.indexOf(num)
            numbers[index] = -1
            drawnRows[index/drawnRows.size]++
            drawnColumns[index%drawnColumns.size]++
        }
    }

    fun checkWin() = drawnRows.contains(5) || drawnColumns.contains(5)

    fun getScore() = numbers.filter { it != -1 }.sum()

    fun clone() = Board(numbers = numbers.toMutableList(), drawnRows = drawnRows.clone(), drawnColumns = drawnColumns.clone())
}

data class Winner(val board: Board, val number: Int)

