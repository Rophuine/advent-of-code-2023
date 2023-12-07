import java.lang.Long.*

class RangeMapper(destStart: Long, private val sourceStart: Long, length: Int, private val name: String) {
    private val sourceEnd = sourceStart+length-1
    private val delta = destStart-sourceStart
    fun matches(value: Long): Boolean {
        return (value in sourceStart..sourceEnd)
    }
    fun mapValue(value: Long): Long {
        return value + delta
    }
    fun splitRange(range: Range): List<Range> {
        if (range.start > sourceEnd || range.end < sourceStart || range.name == name) return listOf(range)

        val result = mutableListOf<Range>()
        if (range.start < sourceStart) result.add(Range(range.start, sourceStart-1))
        val withinMapRange = Range(max(sourceStart, range.start), min(range.end, sourceEnd))
        result.add(Range(this.mapValue(withinMapRange.start), this.mapValue(withinMapRange.end), this.name))
        if (range.end > sourceEnd) result.add(Range(sourceEnd+1, range.end))
        return result
    }
}
data class Range(val start: Long, val end: Long, val name: String = "")

class Map {
    private val map = mutableListOf<RangeMapper>()
    private var name = ""
    fun add(input: String) {
        if (input.contains("map")) {
            this.name = input.split(' ')[0]
        }
        else {
            val (dest, source, length) = input.split(' ')
            this.add(dest.toLong(), source.toLong(), length.toInt())
        }
    }
    private fun add(dest: Long, source: Long, length: Int) {
        map.add(RangeMapper(dest, source, length, name))
    }
    fun mapValue(value: Long): Long {
        for (rangeMapper in map) {
            if (rangeMapper.matches(value)) return rangeMapper.mapValue(value)
        }
        return value
    }
    fun mapRange(ranges: List<Range>): List<Range> {
        return map.fold(ranges) { rs, mapper -> rs.flatMap { r -> mapper.splitRange(r) } }
    }
}

fun getMaps(input: List<String>): List<Map> {
    data class Acc(val maps: MutableList<Map>, var nextMap: Map)
    val result = input.fold(Acc(mutableListOf(), Map())) {
        acc, line ->
        if (line.isEmpty()) {
            acc.maps.add(acc.nextMap)
            acc.nextMap = Map()
        }
        else {
            acc.nextMap.add(line)
        }
        acc
    }
    result.maps.add(result.nextMap)
    return result.maps
}

fun main() {
    fun part1(input: List<String>): Long {
        val seeds = input[0].split(' ').drop(1).map { it.toLong() }
        val maps = getMaps(input.subList(2, input.size))
        val results = seeds.map { s -> maps.fold(listOf(s)) { current, map -> current.plus(map.mapValue(current.last())) } }
        return results.minOf { it.last() }
    }

    fun part2(input: List<String>): Long {
        val seedData = input[0].split(' ').drop(1).map { it.toLong() }
        val seedRanges = seedData.chunked(2).map { (start, length) -> Range(start, start+length-1) }
        val maps = getMaps(input.subList(2, input.size))
        val finalRanges = maps.fold(seedRanges) { acc, map -> map.mapRange(acc) }
        val lowest = finalRanges.minBy { it.start }
        return lowest.start
    }

    val input = readInput("Day05")

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    println("Part 1: ${part1(input)}")

    check(part2(testInput) == 46L)
    println("Part 2: ${part2(input)}")
}