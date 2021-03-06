import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads handler to the given input txt file.
 */
fun openFile(name: String) = File("src", "$name.txt")

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Reads lines from the given input txt file and map them to Ints.
 */
fun readInputAsInts(name: String) = File("src", "$name.txt").readLines().map { it.toInt() }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Inverts bits in a binary string.
 */
fun String.invertBinary(): String = this.map { if (it == '0') '1' else '0' }.joinToString("")
