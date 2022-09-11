package matt.lang.reflect

import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

actual fun classForName(qualifiedName: String): KClass<*>? {
  return try {
	Class.forName(qualifiedName).kotlin
  } catch (e: ClassNotFoundException) {
	null
  }
}

actual fun KClass<*>.isSubTypeOf(cls: KClass<*>): Boolean = this.isSubclassOf(cls)