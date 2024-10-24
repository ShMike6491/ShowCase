package org.example.showcase.store.domain

import org.example.showcase.common.domain.model.IError
import org.example.showcase.common.domain.model.Result
import org.example.showcase.store.domain.model.IProduct
import org.example.showcase.store.domain.repository.IStoreRepository

class StoreProductsInteractor(private val repository: IStoreRepository) {

    suspend fun getAllProducts(): Result<List<IProduct>, IError> = repository.getAllProducts()

    suspend fun getProductById(id: String): Result<IProduct, IError> = repository.getProductById(id)
}