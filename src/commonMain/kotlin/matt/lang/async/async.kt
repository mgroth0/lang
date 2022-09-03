package matt.lang.async

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import matt.lang.async.EveryFirst.DELAY
import matt.lang.async.EveryFirst.OP
import kotlin.time.Duration

enum class EveryFirst {
  DELAY, OP
}

suspend fun every(d: Duration, first: EveryFirst, op: ()->Unit) {
  when (first) {
	DELAY -> while (true) {
	  op()
	  delay(d)
	}

	OP    -> while (true) {
	  op()
	  delay(d)
	}
  }
}



