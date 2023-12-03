fun main() {
    data class Symbol(var row: Int, var col: Int, var symbol: Char)
    data class Number(var row: Int, var startCol: Int, var endCol: Int, var number: Int)

    fun getLineSymbols(line: String, rowNum: Int): List<Symbol> {
        return line.mapIndexedNotNull { index, char ->
            if (!char.isDigit() && char != '.') Symbol(rowNum, index, char) else null
        }
    }

    fun getLineNumbers(line: String, rowNum: Int): List<Number> {
        data class Aggregate(var previous: List<Number> = mutableListOf(), var partial: String = "")
        val reduction = line.foldIndexed(Aggregate()) {
            index, acc, char ->
            if (char.isDigit()) {
                Aggregate(acc.previous, acc.partial + char)
            }
            else if (acc.partial.isNotEmpty()) {
                Aggregate(acc.previous.plus(Number(rowNum, index - acc.partial.length, index - 1, acc.partial.toInt())))
            }
            else acc
        }
        return if (reduction.partial.isNotEmpty()) {
            reduction.previous.plus(Number(rowNum, line.length - reduction.partial.length, line.length - 1, reduction.partial.toInt()))
        }
        else reduction.previous
    }

    fun isAdjacent(s: Symbol, n: Number): Boolean {
        return s.row >= n.row-1 && s.row <= n.row + 1 && s.col >= n.startCol - 1 && s.col <= n.endCol + 1
    }

    fun part1(input: List<String>): Int {
        val symbols = input.flatMapIndexed { rowNum, line -> getLineSymbols(line, rowNum) }
        val numbers = input.flatMapIndexed { rowNum, line -> getLineNumbers(line, rowNum) }
        val partNumbers = numbers.filter {
            n -> symbols.any { s -> isAdjacent(s, n) }
        }
        return partNumbers.sumOf { n -> n.number }
    }

    fun part2(input: List<String>): Int {
        val symbols = input.flatMapIndexed { rowNum, line -> getLineSymbols(line, rowNum) }
        val numbers = input.flatMapIndexed { rowNum, line -> getLineNumbers(line, rowNum) }
        val products = symbols.filter { s -> s.symbol == '*' }.mapNotNull {
            g ->
            val adjacent = numbers.filter { n -> isAdjacent(g, n)}
            if (adjacent.size == 2) adjacent[0].number * adjacent[1].number else null
        }
        return products.sum()
    }

    val input = readInput("Day03")

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    println("Part 1: ${part1(input)}")

    check(part2(testInput) == 467835)
    println("Part 2: ${part2(input)}")
}