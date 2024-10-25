package org.example.showcase.store.ui.model

import org.example.showcase.common.domain.getRandomString
import org.example.showcase.common.domain.model.IIdentifiable
import org.example.showcase.common.domain.roundTo
import org.example.showcase.common.ui.model.UiNumber
import org.example.showcase.common.ui.model.UiString
import org.example.showcase.common.ui.model.asUiString
import org.example.showcase.store.domain.model.IProduct

data class ProductDetailState(
    override val id: String = getRandomString(),
    val titleText: UiString? = null,
    val imageUrl: String? = null,

    //todo: change hardcoded
    val priceTagText: UiString = "Price".asUiString(),
    val price: UiNumber? = null,

    //todo: change hardcoded
    val categoryTagText: UiString = "Category".asUiString(),
    val category: String? = null,

    //todo: change hardcoded
    val descriptionTagText: UiString = "Description".asUiString(),
    val descriptionText: UiString? = null,

    val isLiked: Boolean = false,
    val isLoading: Boolean = true,
    val errorMessage: UiString? = null,
) : IIdentifiable

fun IProduct.asDetailState(
): ProductDetailState {
    val roundedPriceText = priceUsd.roundTo(2).toString()
    return ProductDetailState(
        id = id,
        titleText = title.asUiString(),
        imageUrl = imageUrl,
        price = UiNumber(priceUsd, "$$roundedPriceText".asUiString()),
        category = category,
        descriptionText = description.asUiString(),
        isLoading = false,
        errorMessage = null
    )
}