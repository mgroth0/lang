@file:JvmName("SyncKtJvm")

package matt.lang.sync

actual inline fun <R> inSync(monitor: Any, op: ()->R): R {
  return synchronized(monitor) {
	op()
  }
}