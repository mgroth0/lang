@file:JvmName("WeakJvmKt")

package matt.lang.weak

import matt.lang.function.Op
import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

actual open class WeakRef<T: Any> actual constructor(value: T) {
  private val weakRef: WeakReference<T> = WeakReference(value)
  actual open fun deref() = weakRef.get()
}


actual class SoftRef<T: Any> actual constructor(value: T) {
  private val softRef: SoftReference<T> = SoftReference(value)
  actual fun deref() = softRef.get()
}


object Dump {
  private val refQueue = ReferenceQueue<Any>()

  init {
	thread(name = "dump", isDaemon = true) {
	  while (true) {
		val ref = refQueue.remove()
		require(ref.get() == null)
		synchronized(this@Dump) {
		  (ref as GarbageHandler).handler()
		  handlerRefs -= ref
		}
	  }
	}
  }

  private class GarbageHandler(obj: Any, val handler: ()->Unit): WeakReference<Any>(obj, refQueue)


  @Synchronized
  internal fun onGarbageCollected(obj: Any, op: Op) {
	handlerRefs += GarbageHandler(obj, op)
  }

  private val handlerRefs = mutableSetOf<GarbageHandler>()
}


fun Any.onGarbageCollected(op: Op) = Dump.onGarbageCollected(this, op)