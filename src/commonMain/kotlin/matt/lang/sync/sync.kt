package matt.lang.sync

expect fun <R> inSync(monitor: Any, op: ()->R): R