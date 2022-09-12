package matt.lang.control

inline fun <T> Iterable<T>.forEachNested(action: (T, T)->Unit): Unit {
  for (element1 in this) for (element2 in this) action(element1, element2)
}