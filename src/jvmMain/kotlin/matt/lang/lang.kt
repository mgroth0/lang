@file:JvmName("LangJvmKt")

package matt.lang


import matt.lang.KotlinPlatform.JVM
import java.lang.management.ManagementFactory
import java.lang.management.RuntimeMXBean
import java.net.InetAddress
import kotlin.concurrent.thread
import kotlin.contracts.InvocationKind.EXACTLY_ONCE
import kotlin.contracts.contract
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.nanoseconds


actual fun unixTime() = System.currentTimeMillis().milliseconds
actual fun preciseTime() = System.nanoTime().nanoseconds

inline fun <R> Any.sync(op: ()->R): R {
  contract {
	callsInPlace(op, EXACTLY_ONCE)
  }
  return synchronized(this, op)
}


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

actual val currentPlatform by lazy { JVM }


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


const val DO_NOT_SHUTDOWN_WITH_FX_THREAD =
  "NEVER RELY ON JAVAFX APPLICATION.STOP() FOR ANY SHUTDOWN THINGS. DESIGN MY PROGRAM TO NOT RELY ON THE FX THREAD FOR ANY KIND OF CLEANUP AT ALL! Application.stop() does not reliable execute when kill signals are raised and the JavaFX thread is unresponsive to runLater or even Platform.exit() inside of shutdown hooks. In general, I should do everything possible to keep the MY shutdown process in MY control, and not rely on understanding whatever weird stuff and shutdown hooks the javafx programmers made for their \"special\" thread"


/*purpose is for lambdas that require a Unit return type*/
fun runThread(
  start: Boolean = true,
  isDaemon: Boolean = false,
  contextClassLoader: ClassLoader? = null,
  name: String? = null,
  priority: Int = -1,
  block: ()->Unit
) {
  thread(
	start = start,
	isDaemon = isDaemon,
	contextClassLoader = contextClassLoader,
	name = name,
	priority = priority,
	block = block
  )
}