package org.example.showcase.store.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.example.showcase.store.domain.model.IProduct

@Serializable
data class ProductDto(
    @SerialName("id") val idNum : Int,
    @SerialName("title") override val title: String,
    @SerialName("price") val price: Float,
    @SerialName("category") override val category: String,
    @SerialName("description") override val description: String,
    @SerialName("image") val image: String
) : IProduct {

    @Transient
    override val id: String = idNum.toString()

    @Transient
    override val priceUsd: Double = price.toDouble()

    @Transient
    override val imageUrl: String  = image
}