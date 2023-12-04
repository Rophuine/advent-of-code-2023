fun main() {
    class Card(input: String) {
        val cardNumber: Int
        val winners: Set<String>
        val yours: List<String>
        var copies: Int = 1
        init {
            val regex = "^Card\\s+(\\d+):([^|]+)\\|(.+)$".toRegex()
            val matches = regex.matchEntire(input)
            check(matches != null)
            cardNumber = matches.groupValues[1].toInt()
            winners = matches.groupValues[2].trim().split("\\s+".toRegex()).toSet()
            yours = matches.groupValues[3].trim().split("\\s+".toRegex())
        }
    }

    fun part1(input: List<String>): Int {
        val cards = input.map { i -> Card(i) }
        val points = cards.map {
            c ->
            val yourWinners = c.yours.filter { num -> c.winners.contains(num) }
            yourWinners.fold(0) { acc, _ -> if (acc == 0) 1 else acc * 2}
        }
        return points.sum()
    }

    fun part2(input: List<String>): Int {
        val cards = input.map { i -> Card(i) }
        val dict = cards.associateBy { it.cardNumber }
        cards.forEach {
            c ->
            val yourWinners = c.yours.filter { num -> c.winners.contains(num) }.size
            (c.cardNumber+1..c.cardNumber+yourWinners).forEach { i ->
                dict[i]!!.copies += c.copies
            }
        }
        return cards.sumOf { c -> c.copies }
    }

    val input = readInput("Day04")

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    println("Part 1: ${part1(input)}")

    check(part2(testInput) == 30)
    println("Part 2: ${part2(input)}")
}