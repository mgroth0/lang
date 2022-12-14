@file:JvmName("SyncKtJvm")

package matt.lang.sync

actual inline fun <R> inSync(monitor: Any, op: ()->R): R {
  return synchronized(monitor) {
	op()
  }
}

actual fun stackSize(): Int {
  return Thread.currentThread().stackTrace.size
  /*StackWalker.getInstance().walk {

  }*/
  //  return Thread.getAllStackTraces()[Thread.currentThread()].size
}

