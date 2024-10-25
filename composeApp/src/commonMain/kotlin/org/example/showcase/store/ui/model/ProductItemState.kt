package org.example.showcase.store.ui.model

import org.example.showcase.common.domain.model.IIdentifiable
import org.example.showcase.common.domain.roundTo
import org.example.showcase.common.ui.model.UiNumber
import org.example.showcase.common.ui.model.UiString
import org.example.showcase.common.ui.model.asUiString
import org.example.showcase.store.domain.model.IProduct

data class ProductItemState(
    override val id: String,
    val titleText: UiString,
    val price: UiNumber,
    val imageUrl: String,
    //todo: change hardcoded
    val actionText: UiString = "Buy".asUiString(),
) : IIdentifiable

fun IProduct.asProductItemState(
): ProductItemState {
    val roundedPriceText = priceUsd.roundTo(2)
        .toString()
        .asUiString()
    return ProductItemState(
        id = id,
        titleText = title.asUiString(),
        price = UiNumber(priceUsd, roundedPriceText),
        imageUrl = imageUrl
    )
}