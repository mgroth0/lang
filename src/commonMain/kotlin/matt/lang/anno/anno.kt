package matt.lang.anno

import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.reflect.KClass

annotation class GeneralizationCandidate
annotation class Recycle
annotation class NeedsTest

@Target(AnnotationTarget.TYPEALIAS)
annotation class TemporaryCode
annotation class ExperimentalCode
annotation class DoesNotAlwaysWork
annotation class Duplicated
annotation class NullToReduceObjects
annotation class MergeWith(vararg val classes: KClass<*>)

@Target(AnnotationTarget.FILE, CLASS, FUNCTION)
/*I would like this to be AnnotationTarget.ANY but that does not seem to exist*/
annotation class See(val link: KClass<*>)