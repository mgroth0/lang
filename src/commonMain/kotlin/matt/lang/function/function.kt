package matt.lang.function

typealias Op = ()->Unit
typealias Produce<T> = ()->T
typealias Pred = Produce<Boolean>
typealias On<T> = (T)->Unit
typealias MetaFunction = (op: ()->Unit)->Unit
typealias MultiOpHandler = (ops: List<()->Unit>)->Unit
