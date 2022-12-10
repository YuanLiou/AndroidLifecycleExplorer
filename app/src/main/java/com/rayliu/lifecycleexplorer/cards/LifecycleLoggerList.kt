package com.rayliu.lifecycleexplorer.cards

import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rayliu.lifecycleexplorer.R

@Composable
fun LifecycleLoggerList(
    modifier: Modifier = Modifier,
    logs: List<LifecycleLog> = emptyList()
) {
    LazyColumn {
        items(logs) {
            val message = "${it.id}: ${it.message}"
            LifecycleLoggerItem(
                title = message,
                colorResId = it.colorBackgroundResId
            )
        }
    }
}

@Composable
fun LifecycleLoggerItem(
    @ColorRes colorResId: Int,
    modifier: Modifier = Modifier,
    title: String = ""
) {
    Surface(
        color = colorResource(id = colorResId),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            title,
            fontSize = 18.sp,
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 16.dp
            )
        )
    }
}

@Preview
@Composable
private fun LifecycleLoggerListPreview() {
    MaterialTheme {
        Surface {
            LifecycleLoggerList(
                logs = listOf(
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
            LifecycleLoggerItem(R.color.white, title = "Logger Item Preview")
        }
    }
}
