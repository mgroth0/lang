package matt.lang.safeconvert

fun Long.requireIsByte(): Byte {
  require(this >= Byte.MIN_VALUE && this <= Byte.MAX_VALUE) {
    "${this} is not a byte"
  }
  return toByte()
}

fun Long.requireIsInt(): Int {
  require(this >= Int.MIN_VALUE && this <= Int.MAX_VALUE) {
    "${this} is not an int"
  }
  return toInt()
}