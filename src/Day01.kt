fun main() {
    fun convertWordsToNumbers(input: String): String {
        val numbers = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        // This gets around the problem of eightwothree needing to be 823 by putting the text both before and after
        // the inserted digit, so the replaced text can still form part of other numbers either before or after the replacement.
        // E.g. when replacing "two", "eightwone" becomes "eightwo2twone" - so the leading 't' can still be used with "eight", and the
        // trailing 'o' with "one".
        return numbers.foldIndexed(input) { index, result, text -> result.replace(text, "$text$index$text") }
    }

    fun getFirstAndLastDigits(input: String): String {
        val digits = input.filter { c -> c.isDigit() }
        return "${digits[0]}${digits.last()}"
    }

    fun part1(input: List<String>): Int {
        val numbers = input.map { s -> getFirstAndLastDigits(s).toInt() }
        return numbers.sum()
    }

    fun part2(input: List<String>): Int {
        val replaced = input.map { s -> convertWordsToNumbers(s) }
        val numbers = replaced.map { s -> getFirstAndLastDigits(s).toInt() }
        return numbers.sum()
    }

    val input = readInput("Day01")

    check(part1(readInput("Day01_test")) == 142)
    println("Part 1: ${part1(input)}")

    check(part2(readInput("Day01_test2")) == 281)
    println("Part 2: ${part2(input)}")
}
