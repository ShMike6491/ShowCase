package org.example.showcase.store.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.example.showcase.common.ui.model.UiIntent
import org.example.showcase.store.ui.model.ProductItemState

@Composable
fun ProductListItem(
    modifier: Modifier = Modifier,
    state: ProductItemState,
    action: (UiIntent) -> Unit
) {
    var likedState: Boolean by remember { mutableStateOf(state.isLiked) }
    val vectorRes = if (likedState) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.fillMaxWidth()
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
                    .testTag("itemSideIconButton")
                    .size(24.dp),
                onClick = {
                    val updatedState = !likedState
                    likedState = updatedState
                    action(UiIntent.ToggleChange(updatedState, state.id))
                }
            ) {
                Icon(
                    modifier = Modifier.testTag("itemSideIcon"),
                    imageVector = vectorRes,
                    contentDescription = "Add to favorites",
                    tint = MaterialTheme.colors.primary
                )
            }

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
                            .testTag("itemFailedImage")
                            .size(40.dp)
                            .align(Alignment.Center)
                    )
                },
                modifier = Modifier
                    .testTag("itemImage")
                    .size(100.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(Color.White)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .testTag("itemDetailColumn")
                    .defaultMinSize(minHeight = 100.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.testTag("itemTitleText"),
                    text = state.titleText.asString(),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .testTag("itemPriceTagText")
                            .align(alignment = Alignment.CenterVertically)
                            .padding(start = 16.dp),
                        text = state.price.formatted.asString(),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.primary
                    )

                    Spacer(modifier = Modifier.width(32.dp))

                    OutlinedButton(
                        modifier = Modifier.testTag("itemPrimaryButton"),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colors.primary,
                            backgroundColor = Color.Transparent
                        ),
                        onClick = { action(UiIntent.ButtonClick(state.id)) }
                    ) {
                        Text(
                            modifier = Modifier.testTag("itemPrimaryButtonText"),
                            text = state.actionText.asString(),
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}