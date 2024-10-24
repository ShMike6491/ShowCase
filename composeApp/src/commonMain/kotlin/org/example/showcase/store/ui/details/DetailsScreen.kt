package org.example.showcase.store.ui.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import org.example.showcase.store.ui.model.ProductDetailState
import org.jetbrains.compose.resources.painterResource
import showcase.composeapp.generated.resources.Res
import showcase.composeapp.generated.resources.compose_multiplatform

@Composable
fun DetailScreen(state: ProductDetailState) {
    Column(modifier = Modifier.padding(16.dp)) {
        Header(state)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = state.titleText.asString(),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .testTag("titleText")
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        InfoRow(
            modifier = Modifier.testTag("priceInfo"),
            tagText = state.priceTagText.asString(),
            valueText = state.price.formatted.asString()
        )

        Spacer(modifier = Modifier.height(16.dp))

        InfoRow(
            modifier = Modifier.testTag("categoryInfo"),
            tagText = state.categoryTagText.asString(),
            valueText = state.category
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = state.descriptionTagText.asString(),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.testTag("infoTagText")
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = state.descriptionText.asString(),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.testTag("infoTagText")
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            modifier = Modifier
                .testTag("primaryButton")
                .align(Alignment.End),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colors.primary,
                backgroundColor = Color.Transparent
            ),
            onClick = { state.primaryAction?.invoke() }
        ) {
            Text(
                modifier = Modifier.testTag("primaryButtonText"),
                text = state.actionText.asString(),
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
fun Header(state: ProductDetailState,) {
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
                onClick = { state.navigationAction?.invoke() }
            ) {
                Icon(
                    modifier = Modifier.testTag("headerNavigationIcon"),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    // todo: hardcoded description
                    contentDescription = "Navigate back",
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
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .align(Alignment.CenterVertically)
            )

            // todo: like click animation?
            IconButton(
                modifier = Modifier
                    .testTag("headerActionIconButton")
                    .size(24.dp),
                onClick = { state.likeAction?.invoke() }
            ) {
                Icon(
                    modifier = Modifier.testTag("headerActionIcon"),
                    //Icons.Default.Favorite - this is filled resource
                    imageVector = Icons.Default.FavoriteBorder,
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
