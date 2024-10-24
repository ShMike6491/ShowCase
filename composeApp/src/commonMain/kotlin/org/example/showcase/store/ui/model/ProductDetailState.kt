package org.example.showcase.store.ui.model

import org.example.showcase.common.domain.model.IIdentifiable
import org.example.showcase.common.ui.model.UiNumber
import org.example.showcase.common.ui.model.UiString
import org.example.showcase.common.ui.model.asUiString
import org.example.showcase.store.domain.model.IProduct

// todo: add like icon vector resource??

data class ProductDetailState(
    override val id: String,
    val titleText: UiString,
    val imageUrl: String,

    //todo: change hardcoded
    val priceTagText: UiString = "Price".asUiString(),
    val price: UiNumber,

    //todo: change hardcoded
    val categoryTagText: UiString = "Category".asUiString(),
    val category: String,

    //todo: change hardcoded
    val descriptionTagText: UiString = "Description".asUiString(),
    val descriptionText: UiString,

    //todo: change hardcoded
    val actionText: UiString = "Buy".asUiString(),
    val primaryAction: (() -> Unit)? = null,
    val navigationAction: (() -> Unit)? = null,
    val likeAction: (() -> Unit)? = null
) : IIdentifiable

fun IProduct.asDetailState(
    primaryAction: (() -> Unit)? = null,
    navigationAction: (() -> Unit)? = null,
    likeAction: (() -> Unit)? = null
): ProductDetailState {
    return ProductDetailState(
        id = id,
        titleText = title.asUiString(),
        imageUrl = imageUrl,
        price = UiNumber(priceUsd, "$$priceUsd".asUiString()),
        category = category,
        descriptionText = description.asUiString(),
        primaryAction = primaryAction,
        navigationAction = navigationAction,
        likeAction = likeAction
    )
}