package com.davisdabols.shotsandbeer.common

import timber.log.Timber

class LineNumberDebugThree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement) =
        "$TAG_NAME: (${element.fileName}:${element.lineNumber}) #${element.methodName} "
}
