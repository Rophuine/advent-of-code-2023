fun main() {
    class Game(public var reds: Int, public var greens: Int, public var blues: Int, public val game: Int = 0)

    fun getRGB(input: String): Game {
        val result = Game(0, 0, 0)
        val strings = input.split(',')
        for (str in strings) {
            val number = str.trim().split(' ')[0].toInt()
            if (str.contains("red")) result.reds += number
            if (str.contains("green")) result.greens += number
            if (str.contains("blue")) result.blues += number
        }
        return result
    }

    fun getMinimumColorsNeeded(game: Int, input: String): Game {
        val data = input.split(':')[1].trim()
        val sets = data.split(';').map { s -> s.trim() }
        val rgbs = sets.map { s -> getRGB(s) }
        val reds = rgbs.maxBy { s -> s.reds }.reds
        val greens = rgbs.maxBy { s -> s.greens }.greens
        val blues = rgbs.maxBy { s -> s.blues }.blues
        return Game(reds, greens, blues, game)
    }

    fun part1(input: List<String>): Int {
        val games = input.mapIndexed { index, i -> getMinimumColorsNeeded(index+1, i) }
        val possible = games.filter {g -> g.reds <= 12 && g.greens <= 13 && g.blues <= 14}.map{g -> g.game}
        return possible.sum()
    }

    fun part2(input: List<String>): Int {
        val games = input.mapIndexed { index, i -> getMinimumColorsNeeded(index+1, i) }
        val powers = games.map { g -> g.reds * g.greens * g.blues }
        return powers.sum()
    }

    val input = readInput("Day02")

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    println("Part 1: ${part1(input)}")

    check(part2(testInput) == 2286)
    println("Part 2: ${part2(input)}")
}