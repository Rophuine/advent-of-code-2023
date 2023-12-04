object dayt {
    val test = readInput("Day_test")
}
fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day")

    val testInput = readInput("Day_test")
    check(part1(testInput) == 0)
    val part1result = part1(input)
//    check(part1result == 0)
    println("Part 1: ${part1result}")

    //val testInput2 = readInput("Day_test")
    //check(part2(testInput2) == 0)
    val part2result = part2(input)
    println("Part 2: ${part2result}")
}