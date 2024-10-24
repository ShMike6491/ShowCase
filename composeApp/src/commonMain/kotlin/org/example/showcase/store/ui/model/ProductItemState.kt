package org.example.showcase.store.ui.model

import org.example.showcase.common.domain.model.IIdentifiable
import org.example.showcase.common.domain.roundTo
import org.example.showcase.common.ui.model.UiNumber
import org.example.showcase.common.ui.model.UiString
import org.example.showcase.common.ui.model.asUiString
import org.example.showcase.store.domain.model.IProduct
import kotlin.math.pow
import kotlin.math.roundToInt

// todo: add icon vector resource

data class ProductItemState(
    override val id: String,
    val titleText: UiString,
    val price: UiNumber,
    val imageUrl: String,

    //todo: change hardcoded
    val actionText: UiString = "Buy".asUiString(),
    val primaryAction: (() -> Unit)? = null,
    val sideAction: (() -> Unit)? = null,
) : IIdentifiable

fun IProduct.asProductItemState(
    primaryAction: (() -> Unit)? = null,
    sideAction: (() -> Unit)? = null
): ProductItemState {
    val roundedPriceText = priceUsd.roundTo(2)
        .toString()
        .asUiString()
    return ProductItemState(
        id = id,
        titleText = title.asUiString(),
        price = UiNumber(priceUsd, roundedPriceText),
        imageUrl = imageUrl,
        primaryAction = primaryAction,
        sideAction = sideAction
    )
}