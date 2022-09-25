package matt.lang.whenlang

import kotlin.random.Random

/*https://discuss.kotlinlang.org/t/when-with-less-than/2345/14*/
interface WhenComparator<T> {
  fun test(other: T): Boolean
  operator fun contains(other: T) = test(other)
}

fun <T: Comparable<T>> lt(value: T) = object: WhenComparator<T> {
  override fun test(other: T) = other < value
}

private fun demo() {
  val x = Random.nextInt()
  val y = when (x) {
	in lt(1) -> "less than 1"
	1        -> "one"
	else     -> "greater than one"
  }
  println(y)
}

