package org.example.showcase.store.ui.model

import androidx.compose.runtime.Immutable
import org.example.showcase.common.domain.getRandomString
import org.example.showcase.common.domain.model.IIdentifiable
import org.example.showcase.common.ui.model.UiString

@Immutable
data class ProductFeedState(
    override val id: String = getRandomString(),
    val items: List<ProductItemState> = emptyList(),
    val errorMessage: UiString? = null,
    val isLoading: Boolean = true,
) : IIdentifiable