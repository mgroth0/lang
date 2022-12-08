package matt.lang.anno

import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.EXPRESSION
import kotlin.annotation.AnnotationTarget.FILE
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.LOCAL_VARIABLE
import kotlin.annotation.AnnotationTarget.TYPEALIAS
import kotlin.reflect.KClass

annotation class GeneralizationCandidate
annotation class Recycle
annotation class NeedsTest
annotation class DoesNotNeedToBeSynchronized

@Target(TYPEALIAS)
annotation class TemporaryCode
annotation class ExperimentalCode
annotation class PhaseOut
annotation class DoesNotAlwaysWork
annotation class Duplicated
annotation class NullToReduceObjects
annotation class MergeWith(vararg val classes: KClass<*>)

@Target(FILE, CLASS, FUNCTION)
/*I would like this to be AnnotationTarget.ANY but that does not seem to exist*/
annotation class See(val link: KClass<*>)

@Target(EXPRESSION, LOCAL_VARIABLE, CLASS, AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
@Retention(SOURCE) /*needs to be source to be allowed on expression*/
@Repeatable
annotation class SeeURL(val url: String)


@Target(EXPRESSION, LOCAL_VARIABLE, CLASS)
@Retention(SOURCE) /*needs to be source to be allowed on expression*/
@Repeatable
annotation class SeeURLs(vararg val url: String)