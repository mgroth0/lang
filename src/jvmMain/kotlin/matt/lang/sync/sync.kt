@file:JvmName("SyncKtJvm")
package matt.lang.sync

actual fun <R> inSync(monitor: Any, op: ()->R): R {
  return synchronized(monitor) {
	op()
  }
}