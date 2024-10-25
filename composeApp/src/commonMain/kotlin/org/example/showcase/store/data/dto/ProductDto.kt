package org.example.showcase.store.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.showcase.store.domain.model.IProduct

@Serializable
data class ProductDto(
    @SerialName("id") val idNum : Int,
    @SerialName("title") val titleName: String,
    @SerialName("price") val price: Float,
    @SerialName("category") val categoryName: String,
    @SerialName("description") val descriptionText: String,
    @SerialName("image") val image: String
)

fun ProductDto.asDataModel() = object : IProduct {
    override val title: String = titleName
    override val priceUsd: Double = price.toDouble()
    override val category: String = categoryName
    override val description: String = descriptionText
    override val imageUrl: String = image
    override val id: String = idNum.toString()
}