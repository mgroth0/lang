package matt.lang.enums

inline fun <reified E: Enum<E>> forEach(op: (E)->Unit) = enumValues<E>().forEach(op)
inline fun <reified E: Enum<E>, R> map(op: (E)->R) = enumValues<E>().map(op)
inline fun <reified E: Enum<E>> filter(op: (E)->Boolean) = enumValues<E>().filter(op)
inline fun <reified E: Enum<E>> sequenceOfValues() = enumValues<E>().asSequence()