package org.example.showcase.store.ui.model

import org.example.showcase.common.domain.model.IIdentifiable
import org.example.showcase.common.ui.model.UiNumber
import org.example.showcase.common.ui.model.UiString
import org.example.showcase.common.ui.model.asUiString
import org.example.showcase.store.domain.model.IProduct

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
    return ProductItemState(
        id = id,
        titleText = title.asUiString(),
        price = UiNumber(priceUsd, priceUsd.toString().asUiString()),
        imageUrl = imageUrl,
        primaryAction = primaryAction,
        sideAction = sideAction
    )
}