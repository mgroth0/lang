package matt.lang.sync

/*JS is single-threaded*/
actual fun <R> inSync(monitor: Any, op: ()->R): R = op()
actual fun stackSize(): Int {
  TODO("Not yet implemented")
}