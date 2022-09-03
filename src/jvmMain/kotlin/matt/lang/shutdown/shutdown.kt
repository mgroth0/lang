package matt.lang.shutdown

import matt.lang.RUNTIME
import matt.lang.shutdown.ShutdownExecutor.taskList


fun duringShutdown(task: ()->Unit) = taskList.add(task)


private object ShutdownExecutor {
  val taskList = ArrayList<()->Unit>()

  init {
	RUNTIME.addShutdownHook(Thread {
	  taskList.forEach { it() }
	})
  }
}

