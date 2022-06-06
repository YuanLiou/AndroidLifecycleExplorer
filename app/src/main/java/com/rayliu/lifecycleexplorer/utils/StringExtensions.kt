package com.rayliu.lifecycleexplorer.utils

import android.widget.TextView

fun TextView.printLogs(id: String, message: String) {
    val currentLogMessage = this.text.toString()
    if (currentLogMessage.isEmpty()) {
        this.text = id + ": " + message
    } else {
        this.text = currentLogMessage + "\n" + id + ": " + message
    }
}