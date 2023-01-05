package matt.lang.model.value

import kotlin.jvm.JvmInline

interface ValueWrapperIdea

interface ValueWrapper<T>: ValueWrapperIdea {
  val value: T
}

/*perfect for wen null is not the same thing as empty*/
/*should be value class*/
@JvmInline
value class Value<T>(override val value: T): ValueWrapper<T> {
  companion object {
	init {
	  if (KotlinVersion.CURRENT.isAtLeast(1, 8)) {
		println("this should be a generic value class")
	  }
	}
  }
}

class LazyValue<T>(private val op: ()->T): ValueWrapper<T> {
  override val value by lazy { op() }
}

//
//class LazyVariable<T>(private val op: ()->T): ValueWrapper<T> {
//  override val value by lazy {
//	op()
//  }
//}