package matt.lang.shutdown

import matt.lang.RUNTIME
import matt.lang.shutdown.ShutdownExecutor.shutdownThread
import matt.lang.shutdown.ShutdownExecutor.taskList
import java.util.concurrent.Semaphore

private val sem = Semaphore(1)

fun duringShutdown(task: ()->Unit) = run {
  require(Thread.currentThread() != shutdownThread)
  sem.acquire()
  taskList.add(task)
  sem.release()
}


private object ShutdownExecutor {

  val taskList = ArrayList<()->Unit>()

  val shutdownThread by lazy {
	Thread {
	  sem.acquire()
	  taskList.forEach { it() }
	  sem.release()
	}
  }

  init {
	RUNTIME.addShutdownHook(shutdownThread)
  }
}

