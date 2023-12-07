fun main() {
    data class Race(val time: Long, val record: Long)
    fun distance(race: Race, holdTime: Long): Long {
        return holdTime * (race.time - holdTime)
    }
    fun getWinOptions(race: Race): Long {
        val firstWin = generateSequence(1L) { it+1 }.first { distance(race, it) > race.record }
        val lastWin = generateSequence(race.time-1) { it-1 }.first{ distance(race, it) > race.record }
        return lastWin-firstWin+1
    }
    fun part1(input: List<String>): Long {
        val times = input[0].split("\\s+".toRegex()).drop(1).map {it.toLong()}
        val records = input[1].split("\\s+".toRegex()).drop(1).map {it.toLong()}
        val races = times.zip(records).map {Race(it.first, it.second)}
        val raceResults = races.map {getWinOptions(it)}
        return raceResults.fold(1) { acc, waysToWin -> acc*waysToWin }
    }

    fun part2(input: List<String>): Long {
        val time = input[0].filter {!it.isWhitespace() }.split(':')[1].toLong()
        val record = input[1].filter {!it.isWhitespace() }.split(':')[1].toLong()
        return getWinOptions(Race(time, record))
    }

    val input = readInput("Day06")

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
    println("Part 1: ${part1(input)}")

    check(part2(testInput) == 71503L)
    println("Part 2: ${part2(input)}")
}