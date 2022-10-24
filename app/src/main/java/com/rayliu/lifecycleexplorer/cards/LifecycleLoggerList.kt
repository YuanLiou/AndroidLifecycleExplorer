package com.rayliu.lifecycleexplorer.cards

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LifecycleLoggerList(
    logs: List<LifecycleLog> = emptyList()
) {
    LazyColumn {
        items(logs) {
            val message = "${it.id}: ${it.message}"
            LifecycleLoggerItem(title = message)
        }
    }
}

@Composable
fun LifecycleLoggerItem(
    title: String = ""
) {
    Text(title)
}

@Preview
@Composable
private fun LifecycleLoggerListPreview() {
    MaterialTheme {
        Surface {
            LifecycleLoggerList(
                listOf(
                    LifecycleLog("ID01", "Message01"),
                    LifecycleLog("ID02", "Message02"),
                    LifecycleLog("ID03", "Message03")
                )
            )
        }
    }
}

@Preview
@Composable
private fun LifecycleLoggerItemPreview() {
    MaterialTheme {
        Surface {
            LifecycleLoggerItem("Logger Item Preview")
        }
    }
}
