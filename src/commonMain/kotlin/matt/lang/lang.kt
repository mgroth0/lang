package matt.lang

import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.contracts.InvocationKind.AT_LEAST_ONCE
import kotlin.contracts.InvocationKind.AT_MOST_ONCE
import kotlin.contracts.InvocationKind.EXACTLY_ONCE
import kotlin.contracts.contract
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.time.Duration


enum class KotlinPlatform {
  JVM, JS, Native
}

expect val currentPlatform: KotlinPlatform

expect fun unixTime(): Duration
expect fun preciseTime(): Duration

inline infix fun Boolean.ifTrue(op: ()->Unit) {
  contract {
	callsInPlace(op, AT_MOST_ONCE)
  }
  if (this) op()
}


infix fun <R> Boolean.ifTrueOrNull(op: ()->R): R? {
  return if (this) op() else null
}

infix fun Boolean.ifFalse(op: ()->Unit) {
  if (!this) op()
}


fun <T: Any> T.inList(): List<T> {
  return listOf(this)
}

inline fun <reified T: Any> T.inArray(): Array<T> {
  return arrayOf(this)
}

fun inlined(op: ()->Unit) {
  //    Pleasework()
  op()
}


val Any.void get() = Unit


infix fun <A, B, C> Pair<A, B>.trip(third: C): Triple<A, B, C> {
  return Triple(first, second, third)
}


inline fun <T: Any> T.alsoPrintln(op: T.()->String): T {
  contract {
	callsInPlace(op, EXACTLY_ONCE)
  }
  println(op.invoke(this))
  return this
}


inline fun <T: Any> T.go(block: (T)->Unit) {
  contract {
	callsInPlace(block, EXACTLY_ONCE)
  }
  block(this)
}


inline fun <T: Any> T.applyIt(block: T.(T)->Unit): T {
  contract {
	callsInPlace(block, EXACTLY_ONCE)
  }
  block(this)
  return this
}

fun <T: Any> T.ifIs(other: Any?) = when {
  this != other -> null
  else          -> this
}

fun <T: Any> T.ifIsNot(other: Any?) = when (other) {
  this -> null
  else -> this
}
typealias B = Boolean
typealias S = String
typealias I = Int
typealias F = Float
typealias D = Double
typealias L = Long
typealias Num = Number


infix fun <T> MutableCollection<T>.setAll(c: Collection<T>) {
  /*when (this) {
    is ObservableList<T>
  }*/
  clear()
  c.forEach {
	add(it)
  }
}

fun <T> Iterator<T>.toList(): List<T> {
  val list = mutableListOf<T>()
  while (hasNext()) {
	list += next()
  }
  return list
}

fun <T> MutableList<T>.setAllOneByOne(iterable: Iterable<T>) {
  val itr = iterable.iterator()
  var i = 0
  while (itr.hasNext()) {
	require(this.size <= i)
	if (this.size == i) {
	  addAll(itr.toList())
	  break
	}
	val next = itr.next()
	if (this[i] != next) {
	  this[i] = next
	}
	i++
  }
  if (i < this.size - 1) subList(i, size - 1).clear()
}

fun <T> MutableCollection<T>.setAll(vararg c: T) {
  clear()
  c.forEach { add(it) }
}

fun <T> MutableCollection<T>.addAll(vararg c: T) = addAll(c)

fun <E> MutableCollection<E>.addIfNotIn(e: E): Boolean {
  return if (e in this) {
	false
  } else {
	add(e)
	true
  }
}


inline fun whileTrue(op: ()->Boolean) {
  contract {
	callsInPlace(op, AT_LEAST_ONCE)
  }
  @Suppress("ControlFlowWithEmptyBody")
  while (op()) {
  }
}

fun unsafeErr(s: String = "") {
  if ("a" + "b" == "c") {
	return
  }
  throw RuntimeException(s)
}

fun err(s: String = ""): Nothing {
  println("demmy")
  throw RuntimeException(s)
}

class MustUseSubclassException: Throwable()

val MUST_USE_SUBCLASS: Nothing get() = throw MustUseSubclassException()
val NOT_IMPLEMENTED: Nothing get() = throw NotImplementedError()
val BAD_ARG: Nothing get() = throw IllegalArgumentException()
val NEVER: Nothing get() = err("NEVER")
val ILLEGAL: Nothing get() = err("Illegal Call")

fun listsEqual(list1: List<*>, list2: List<*>): Boolean {

  if (list1.size != list2.size)
	return false

  val pairList = list1.zip(list2)

  return pairList.all { (elt1, elt2) ->
	elt1 == elt2
  }
}


sealed interface KotlinPrimitive {
  val kls: KClass<*>
}

enum class Ints(override val kls: KClass<*>): KotlinPrimitive {
  Byte(kotlin.Byte::class),
  Short(kotlin.Short::class),
  Int(kotlin.Int::class),
  Long(kotlin.Long::class),
}

enum class IntArrays(override val kls: KClass<*>): KotlinPrimitive {
  ByteArray(kotlin.ByteArray::class),
  IntArray(kotlin.IntArray::class),
  ShortArray(kotlin.ShortArray::class),
  LongArray(kotlin.LongArray::class),
}

enum class FloatingPoints(override val kls: KClass<*>): KotlinPrimitive {
  Float(kotlin.Float::class),
  Double(kotlin.Double::class),
}

enum class FloatingPointArrays(override val kls: KClass<*>): KotlinPrimitive {
  FloatArray(kotlin.FloatArray::class),
  DoubleArray(kotlin.DoubleArray::class),
}

enum class UnsignedInts(override val kls: KClass<*>): KotlinPrimitive {
  UByte(kotlin.UByte::class),
  UShort(kotlin.UShort::class),
  UInt(kotlin.UInt::class),
  ULong(kotlin.ULong::class),
}

@OptIn(ExperimentalUnsignedTypes::class)
enum class UnsignedIntArrays(override val kls: KClass<*>): KotlinPrimitive {
  /*unsigned arrays and ranges*/
  UByteArray(kotlin.UByteArray::class),
  UShortArray(kotlin.UShortArray::class),
  UIntArray(kotlin.UIntArray::class),
  ULongArray(kotlin.ULongArray::class),
}

enum class Booleans(override val kls: KClass<*>): KotlinPrimitive {
  Boolean(kotlin.Boolean::class),
}

enum class BooleanArrays(override val kls: KClass<*>): KotlinPrimitive {
  BooleanArray(kotlin.BooleanArray::class),
}

enum class Chars(override val kls: KClass<*>): KotlinPrimitive {
  Char(kotlin.Char::class),
}

enum class CharArrays(override val kls: KClass<*>): KotlinPrimitive {
  CharArray(kotlin.CharArray::class),
}

enum class Strings(override val kls: KClass<*>): KotlinPrimitive {
  String(kotlin.String::class),
}

enum class Arrays(override val kls: KClass<*>): KotlinPrimitive {
  Array(kotlin.Array::class),
}

inline val <reified T: Any> KClass<T>.jDefault: T?
  get() {
	return when (T::class) {
	  Boolean::class -> false as T
	  Double::class  -> 0.0 as T
	  Float::class   -> 0.0f as T
	  Byte::class    -> 0 as T
	  Int::class     -> 0 as T
	  Long::class    -> 0 as T
	  Short::class   -> 0 as T
	  Char::class    -> '\u0000' as T
	  else           -> null
	}
  }

//@Suppress("UnusedReceiverParameter") val KClass<Boolean>.jDefault: Boolean get() = false
//@Suppress("UnusedReceiverParameter") val KClass<Double>.jDefault: Double get() = 0.0
//@Suppress("UnusedReceiverParameter") val KClass<Byte>.jDefault: Byte get() = 0
//@Suppress("UnusedReceiverParameter") val KClass<Int>.jDefault: Int get() = 0
//@Suppress("UnusedReceiverParameter") val KClass<Long>.jDefault: Long get() = 0
//@Suppress("UnusedReceiverParameter") val KClass<Float>.jDefault: Float get() = 0.0f
//@Suppress("UnusedReceiverParameter") val KClass<Char>.jDefault: Char get() = '\u0000'
//@Suppress("UnusedReceiverParameter") val KClass<Short>.jDefault: Short get() = 0


fun noExceptions(op: ()->Unit): Boolean {
  return try {
	op()
	true
  } catch (e: Exception) {
	false
  }
}

fun <R> nullIfExceptions(op: ()->R): R? {
  return try {
	op()
  } catch (e: Exception) {
	null
  }
}

fun opt(b: Boolean, s: Any): Array<out Any> = if (b) arrayOf(s) else arrayOf()

inline fun <T, reified R> opt(a: T?, s: T.()->R): Array<out R> = if (a != null) arrayOf(s(a)) else arrayOf()

fun <R> ifOrNull(b: Boolean, op: ()->R) = if (b) op() else null

inline fun <reified R> ifTrue(b: Boolean, op: ()->R): Array<out R> = if (b) arrayOf(op()) else arrayOf()
inline fun <reified R> ifFalse(b: Boolean, op: ()->R): Array<out R> = if (!b) arrayOf(op()) else arrayOf()

class If(val b: Boolean) {
  inline fun <reified R> then(vararg s: R): Array<out R> = if (b) s else arrayOf()
  inline operator fun <reified R> invoke(op: ()->R): Array<out R> = if (b) arrayOf(op()) else arrayOf()
}

/*sometimes I want to use [[apply]] but enforce that it has not return value*/
fun <T> T.scope(op: T.()->Unit) {
  op()
}





inline fun <R> infiniteLoop(op: ()->R): R {
  while (true) {
	op()
  }
}

inline fun <R> untilNotNull(op: ()->R?): R {
  var r: R?
  do {
	r = op()
  } while (r == null)
  return r
}

inline fun <reified R> untilIs(op: ()->Any?): R {
  var r: Any?
  do {
	r = op()
  } while (r !is R)
  return r
}


fun repeat(times: Long, op: (Int)->Unit) = kotlin.repeat(times.toInt(), op)


fun Any?.print() = print(this)
fun Any?.println() = println(this)


fun <E: Comparable<E>> comparableComparator(): Comparator<E> = Comparator { a, b -> a.compareTo(b) }

val List<*>.firstIndex get() = if (isEmpty()) -1 else 0
val List<*>.lastIndex get() = size - 1

object Undefined

typealias List2D<T> = List<List<T>>
typealias List3D<T> = List<List2D<T>>
typealias List4D<T> = List<List3D<T>>

inline fun disabledCode(@Suppress("UNUSED_PARAMETER") op: ()->Unit) = Unit

inline fun disabledButDefinitelyStillInByteCodeCode(op: ()->Unit) {
  val makeSureUnusedCodeIsNotErased = Random.nextDouble()
  if (makeSureUnusedCodeIsNotErased == 11345.13451435) {
	op()
  }
}


object YesIUseLang


fun Any.toStringBasic() = "${this::class.simpleName}@${this.hashCode()}"


/*its nicer to read sometimes. more like english*/
fun not(b: Boolean) = !b

infix fun Any.`is`(clazz: KClass<*>) = clazz.isInstance(this)