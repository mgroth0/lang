package matt.lang.safeconvert

fun Long.requireIsByte(): Byte {
  require(this >= Byte.MIN_VALUE && this <= Byte.MAX_VALUE)
  return toByte()
}