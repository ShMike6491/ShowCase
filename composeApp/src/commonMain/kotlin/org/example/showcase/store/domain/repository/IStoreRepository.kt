package org.example.showcase.store.domain.repository

import org.example.showcase.common.domain.model.IError
import org.example.showcase.store.domain.model.IProduct
import org.example.showcase.common.domain.model.Result

interface IStoreRepository {

    suspend fun getAllProducts(): Result<List<IProduct>, IError>

    suspend fun getProductById(id: String): Result<IProduct, IError>
}