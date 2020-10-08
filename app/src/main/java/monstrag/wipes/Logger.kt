package monstrag.wipes

import android.util.Log

object Debug {
    fun log(vararg messages: Any) {
        val caller = getCallerCallerClassName()
        messages.forEach { Log.v(caller, it.toString())  }
    }

    private fun getCallerCallerClassName(): String? {
        val stElements = Thread.currentThread().stackTrace
        var caller: String? = null
        for (i in 1 until stElements.size) {
            val ste = stElements[i]
            if (ste.className != Debug::class.java.name && ste.className.indexOf("java.lang.Thread") != 0) {
                if (caller == null) {
                    caller = ste.tag
                } else if (caller != ste.tag) {
                    return ste.tag
                }
            }
        }
        return null
    }

    private val StackTraceElement.tag: String
        get() = "$className::$methodName"
}