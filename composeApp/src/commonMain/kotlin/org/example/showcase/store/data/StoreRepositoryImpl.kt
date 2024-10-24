package org.example.showcase.store.data

import org.example.showcase.store.domain.repository.IStoreRepository

class StoreRepositoryImpl(private val apiClient: StoreHttpKtorClient) : IStoreRepository {

    override suspend fun getAllProducts() = apiClient.getAllProducts()

    override suspend fun getProductById(id: String) = apiClient.getProductById(id)
}