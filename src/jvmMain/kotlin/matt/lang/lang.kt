@file:JvmName("LangJvmKt")

package matt.lang


import matt.lang.KotlinPlatform.JVM
import java.lang.management.ManagementFactory
import java.lang.management.RuntimeMXBean
import java.net.InetAddress
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.nanoseconds

actual fun unixTime() = System.currentTimeMillis().milliseconds
actual fun preciseTime() = System.nanoTime().nanoseconds

fun <R> Any.sync(op: ()->R): R = synchronized(this, op)


val RUNTIME by lazy { Runtime.getRuntime()!! }
val RUNTIME_MX: RuntimeMXBean by lazy { ManagementFactory.getRuntimeMXBean() }

/*Thing()::class.java.classLoader*/
/*ClassLoader.getPlatformClassLoader()*/
fun resourceTxt(name: String) = resourceStream(name)?.bufferedReader()?.readText()


fun resourceStream(name: String) =
  ClassLoader.getSystemClassLoader().getResourceAsStream(name)




enum class Env {
  JAVA_HOME,
  SLURM_JOBID;

  fun get(): String? = System.getenv(name)
}


inline fun until(stopAt: Long, op: ()->Unit) {
  while (unixTime().inWholeMilliseconds < stopAt) {
	op()
  }
}

actual val currentPlatform = JVM


fun mAssert(b: Boolean) {
  if (!b) {
	throw RuntimeException("Bad!")
  }
}

fun massert(b: Boolean) = mAssert(b)


val hostname: String by lazy {
  println("checking hostname")
  val r = InetAddress.getLocalHost().hostName
  println("hostname=${r}")
  r
}
val os: String by lazy { System.getProperty("os.name") }
val userName: String by lazy { System.getProperty("user.name") }
val userHome: String by lazy { System.getProperty("user.home") }
val arch: String by lazy { System.getProperty("os.arch") }

