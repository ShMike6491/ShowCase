package org.example.showcase.store.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.showcase.store.ui.model.ProductItemState
import org.jetbrains.compose.resources.painterResource
import showcase.composeapp.generated.resources.Res
import showcase.composeapp.generated.resources.compose_multiplatform

@Composable
fun ProductListItem(
    modifier: Modifier = Modifier,
    state: ProductItemState
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.primary.copy(alpha = 0.2f))
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // todo: like click animation?
            IconButton(
                modifier = Modifier
                    .testTag("itemSideIconButton")
                    .size(24.dp),
                onClick = { state.sideAction?.invoke() }
            ) {
                Icon(
                    modifier = Modifier.testTag("itemSideIcon"),
                    //Icons.Default.Favorite - this is filled resource
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Add to favorites",
                    tint = MaterialTheme.colors.primary
                )
            }

            // todo: load image from url
            Image(
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = "Card Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .testTag("itemImage")
                    .size(100.dp)
                    .clip(CircleShape)
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
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        modifier = Modifier
                            .testTag("itemPriceTagText")
                            .align(alignment = Alignment.CenterVertically),
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
                        onClick = { state.primaryAction?.invoke() }
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