package com.rayliu.lifecycleexplorer.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rayliu.lifecycleexplorer.R
import com.rayliu.lifecycleexplorer.cards.LifecycleLoggerList

@Composable
fun FragmentLifecycleScreen(
    modifier: Modifier = Modifier,
    viewModel: FragmentLifecycleViewModel
) {
    Column(Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.black))
        )
        val uiState = viewModel.uiState.collectAsState()
        when (val viewState = uiState.value) {
            is FragmentLifecycleViewState.LifecycleUpdate -> {
                LifecycleLoggerList(logs = viewState.logs)
            }
            FragmentLifecycleViewState.RESET -> {
                LifecycleLoggerList(logs = emptyList())
            }
        }
    }
}

@Preview
@Composable
fun FragmentLifecycleScreenPreview() {
    FragmentLifecycleScreen(viewModel = FragmentLifecycleViewModel())
}
