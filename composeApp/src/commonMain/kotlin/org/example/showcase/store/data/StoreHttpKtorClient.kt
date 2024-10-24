package org.example.showcase.store.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import org.example.showcase.common.domain.model.IError
import org.example.showcase.common.domain.model.NetworkError
import org.example.showcase.store.data.dto.ProductDto
import org.example.showcase.common.domain.model.Result
import org.example.showcase.store.domain.model.IProduct

class StoreHttpKtorClient(private val httpClient: HttpClient) {

    suspend fun getAllProducts(): Result<List<IProduct>, IError> {
        val response = try {
            httpClient.get(urlString = API_ENDPOINT)
        } catch(e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch(e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        // todo: make an extension?
        return when(response.status.value) {
            in 200..299 -> {
                val body = response.body<List<ProductDto>>()
                Result.Success(body)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun getProductById(id: String): Result<IProduct, IError> {
        val response = try {
            httpClient.get(urlString = "$API_ENDPOINT/$id")
        } catch(e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch(e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        // todo: make an extension?
        return when(response.status.value) {
            in 200..299 -> {
                val body = response.body<ProductDto>()
                Result.Success(body)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    companion object {
        const val API_ENDPOINT = "https://fakestoreapi.com/products"
    }
}