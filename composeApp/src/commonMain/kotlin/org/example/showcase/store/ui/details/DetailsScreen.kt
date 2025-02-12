package org.example.showcase.store.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.example.showcase.common.ui.model.UiIntent
import org.example.showcase.store.ui.model.ProductDetailState

@Composable
fun DetailScreen(component: DetailComponent) {
    val state = component.model.subscribeAsState()

    Content(
        state = state.value,
        action = component::onIntent
    )
}

@Composable
private fun Content(
    state: ProductDetailState,
    action: (UiIntent) -> Unit
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

            else -> Column(
                modifier = Modifier
                    .testTag("contentContainer")
                    .padding(16.dp)
            ) {
                Header(state) { action(it) }
                Spacer(modifier = Modifier.height(24.dp))

                if (state.titleText != null) {
                    Text(
                        text = state.titleText.asString(),
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier
                            .testTag("titleText")
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (state.price != null) {
                    InfoRow(
                        modifier = Modifier.testTag("priceInfo"),
                        tagText = state.priceTagText.asString(),
                        valueText = state.price.formatted.asString()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (state.category.isNullOrBlank().not()) {
                    InfoRow(
                        modifier = Modifier.testTag("categoryInfo"),
                        tagText = state.categoryTagText.asString(),
                        valueText = state.category ?: ""
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (state.descriptionText != null) {
                    Text(
                        text = state.descriptionTagText.asString(),
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier.testTag("descriptionTagText")
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = state.descriptionText.asString(),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier.testTag("descriptionText")
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun Header(
    state: ProductDetailState,
    action: (UiIntent) -> Unit
) {
    var likedState: Boolean by remember { mutableStateOf(state.isLiked) }
    val vectorRes = if (likedState) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .testTag("headerCard")
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.primary.copy(alpha = 0.2f))
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier
                    .testTag("headerNavigationIconButton")
                    .size(24.dp),
                // todo: other use of action handling?
                onClick = { action(UiIntent.ButtonClick()) }
            ) {
                Icon(
                    modifier = Modifier.testTag("headerNavigationIcon"),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    // todo: hardcoded description
                    contentDescription = "Navigate back",
                    tint = MaterialTheme.colors.primary
                )
            }

            if (state.imageUrl != null) {
                CoilImage(
                    imageModel = { state.imageUrl },
                    imageOptions = ImageOptions(
                        contentDescription = "Card Image",
                        contentScale = ContentScale.Inside,
                        alignment = Alignment.Center
                    ),
                    failure = {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = "Failed Load Image",
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.Center)
                        )
                    },
                    modifier = Modifier
                        .testTag("itemImage")
                        .size(150.dp)
                        .clip(RoundedCornerShape(percent = 50))
                        .background(Color.White)
                        .align(Alignment.CenterVertically)
                )
            }

            IconButton(
                modifier = Modifier
                    .testTag("headerActionIconButton")
                    .size(24.dp),
                onClick = {
                    val updatedState = !likedState
                    likedState = updatedState
                    action(UiIntent.ToggleChange(updatedState, state.id))
                }
            ) {
                Icon(
                    modifier = Modifier.testTag("headerActionIcon"),
                    imageVector = vectorRes,
                    // todo: hardcoded description
                    contentDescription = "Add to favorites",
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun InfoRow(
    modifier: Modifier = Modifier,
    tagText: String,
    valueText: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = tagText,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.testTag("infoTagText")
        )

        Text(
            text = valueText,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.testTag("infoValueText")
        )
    }
}
