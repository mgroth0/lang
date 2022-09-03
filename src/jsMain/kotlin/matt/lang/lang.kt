package matt.lang

import kotlinx.browser.window
import matt.lang.KotlinPlatform.JS
import kotlin.js.Date
import kotlin.time.Duration.Companion.milliseconds

actual fun unixTime() = Date().getTime().milliseconds
actual fun preciseTime() = window.performance.now().milliseconds
actual val currentPlatform = JS