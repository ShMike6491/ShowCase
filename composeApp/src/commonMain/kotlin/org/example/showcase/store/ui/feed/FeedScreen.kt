package org.example.showcase.store.ui.feed

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.showcase.common.ui.model.UiNumber
import org.example.showcase.common.ui.model.asUiString
import org.example.showcase.store.ui.components.ProductListItem
import org.example.showcase.store.ui.model.ProductItemState

@Composable
fun FeedScreen() {
    // todo: change to actual data
    val productList = remember {
        listOf(
            ProductItemState(
                id = "product_1",
                titleText = "Compact Travel Backpack - Lightweight".asUiString(),
                price = UiNumber(49.99, "$49.99".asUiString()),
                imageUrl = "https://example.com/image1.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_2",
                titleText = "Wireless Bluetooth Earbuds".asUiString(),
                price = UiNumber(19.99, "$19.99".asUiString()),
                imageUrl = "https://example.com/image2.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_3",
                titleText = "Smartphone Screen Protector - Pack of 3".asUiString(),
                price = UiNumber(8.50, "$8.50".asUiString()),
                imageUrl = "https://example.com/image3.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_4",
                titleText = "Portable Solar Power Bank".asUiString(),
                price = UiNumber(35.75, "$35.75".asUiString()),
                imageUrl = "https://example.com/image4.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_5",
                titleText = "Waterproof Smartwatch with Heart Rate Monitor".asUiString(),
                price = UiNumber(79.99, "$79.99".asUiString()),
                imageUrl = "https://example.com/image5.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_6",
                titleText = "Wireless Charging Pad - Fast Charging".asUiString(),
                price = UiNumber(15.00, "$15.00".asUiString()),
                imageUrl = "https://example.com/image6.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_7",
                titleText = "Noise-Canceling Over-Ear Headphones".asUiString(),
                price = UiNumber(120.49, "$120.49".asUiString()),
                imageUrl = "https://example.com/image7.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_8",
                titleText = "Ergonomic Office Chair - Adjustable".asUiString(),
                price = UiNumber(230.99, "$230.99".asUiString()),
                imageUrl = "https://example.com/image8.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_9",
                titleText = "Foldable Treadmill - Compact and Portable".asUiString(),
                price = UiNumber(499.00, "$499.00".asUiString()),
                imageUrl = "https://example.com/image9.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_10",
                titleText = "Stainless Steel Insulated Water Bottle".asUiString(),
                price = UiNumber(12.99, "$12.99".asUiString()),
                imageUrl = "https://example.com/image10.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_11",
                titleText = "Compact Home Air Purifier".asUiString(),
                price = UiNumber(89.95, "$89.95".asUiString()),
                imageUrl = "https://example.com/image11.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_12",
                titleText = "Lightweight Running Shoes".asUiString(),
                price = UiNumber(60.00, "$60.00".asUiString()),
                imageUrl = "https://example.com/image12.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_13",
                titleText = "Smart LED Light Bulb - WiFi Enabled".asUiString(),
                price = UiNumber(25.99, "$25.99".asUiString()),
                imageUrl = "https://example.com/image13.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_14",
                titleText = "Digital Kitchen Scale - Accurate Measurements".asUiString(),
                price = UiNumber(10.99, "$10.99".asUiString()),
                imageUrl = "https://example.com/image14.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_15",
                titleText = "Wireless Gaming Mouse with RGB Lighting".asUiString(),
                price = UiNumber(55.49, "$55.49".asUiString()),
                imageUrl = "https://example.com/image15.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_16",
                titleText = "4K Ultra HD Streaming Stick".asUiString(),
                price = UiNumber(35.00, "$35.00".asUiString()),
                imageUrl = "https://example.com/image16.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_17",
                titleText = "Electric Standing Desk - Adjustable Height".asUiString(),
                price = UiNumber(299.99, "$299.99".asUiString()),
                imageUrl = "https://example.com/image17.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_18",
                titleText = "Smart Door Lock - Fingerprint and WiFi Enabled".asUiString(),
                price = UiNumber(149.99, "$149.99".asUiString()),
                imageUrl = "https://example.com/image18.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_19",
                titleText = "Bluetooth Portable Speaker - Waterproof".asUiString(),
                price = UiNumber(45.00, "$45.00".asUiString()),
                imageUrl = "https://example.com/image19.jpg",
                actionText = "Buy".asUiString(),
            ),
            ProductItemState(
                id = "product_20",
                titleText = "Smart Robot Vacuum Cleaner - WiFi Control".asUiString(),
                price = UiNumber(230.00, "$230.00".asUiString()),
                imageUrl = "https://example.com/image20.jpg",
                actionText = "Buy".asUiString(),
            )
        )
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(productList, key = { it.id }) {
            ProductListItem(state = it)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}