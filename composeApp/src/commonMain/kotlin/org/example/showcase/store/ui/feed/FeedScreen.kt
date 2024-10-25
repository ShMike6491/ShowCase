package org.example.showcase.store.ui.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.showcase.common.ui.model.UiIntent
import org.example.showcase.store.ui.components.ProductListItem
import org.example.showcase.store.ui.model.ProductFeedState

@Composable
fun FeedScreen(component: FeedComponent) {
    val state = component.model.subscribeAsState()
    Content(
        state = state.value,
        action = component::onIntent
    )
}

@Composable
private fun Content(
    state: ProductFeedState,
    action: (UiIntent) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .testTag("loadingContainer")
                        .fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }
            }
            state.errorMessage != null -> {
                Text(
                    text = state.errorMessage.asString(),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .testTag("errorText")
                        .fillMaxWidth()
                )
            }
            state.items.isEmpty() -> {
                Text(
                    // todo: extract hardcoded
                    text = "The list is empty",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .testTag("titleText")
                        .fillMaxWidth()
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(state.items, key = { it.id }) { item ->
                        ProductListItem(
                            modifier = Modifier.testTag("itemCard${item.id}"),
                            state = item,
                            action = { action(it) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}