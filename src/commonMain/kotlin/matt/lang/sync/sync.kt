package matt.lang.sync

expect fun <R> inSync(monitor: Any, op: ()->R): R
fun <R> inSyncOrJustRun(monitor: Any?, op: ()->R): R {
  return monitor?.let { inSync(it, op) } ?: op()
}

fun <R> inSync(monitors: List<Any>, op: ()->R): R {
  return monitors.fold(op) { acc, mon ->
	{
	  inSync(mon) {
		acc()
	  }
	}
  }.invoke()
}