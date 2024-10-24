package org.example.showcase.store.domain.model

import org.example.showcase.common.domain.model.IIdentifiable

interface IProduct : IIdentifiable {
    val title: String
    val priceUsd: Double
    val category: String
    val description: String
    val imageUrl: String
}