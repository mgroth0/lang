package matt.lang.sync

import matt.lang.NOT_IMPLEMENTED

actual fun <R> inSync(monitor: Any, op: ()->R): R = NOT_IMPLEMENTED
actual fun stackSize(): Int {
  TODO("Not yet implemented")
}