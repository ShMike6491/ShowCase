package org.example.showcase.store.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.ContentConvertException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import org.example.showcase.common.domain.model.IError
import org.example.showcase.common.data.model.NetworkError
import org.example.showcase.store.data.dto.ProductDto
import org.example.showcase.common.domain.model.Result
import org.example.showcase.store.data.dto.asDataModel
import org.example.showcase.store.domain.model.IProduct

class StoreHttpKtorClient(private val httpClient: HttpClient) {

    suspend fun getAllProducts(): Result<List<IProduct>, IError> {
        return httpRequest(API_ENDPOINT) { response ->
            response.handleResponse {
                response.body<List<ProductDto>>()
                    .map { it.asDataModel() }
            }
        }
    }

    suspend fun getProductById(id: String): Result<IProduct, IError> {
        return httpRequest("$API_ENDPOINT/$id") { response ->
            response.handleResponse {
                response.body<ProductDto>().asDataModel()
            }
        }
    }

    private suspend fun <T> httpRequest(
        url: String,
        request: suspend (HttpResponse) -> Result<T, IError>
    ): Result<T, IError> {
        return try {
            val response = httpClient.get(urlString = url)
            request(response)
        } catch (e: UnresolvedAddressException) {
            Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            Result.Error(NetworkError.SERIALIZATION)
        }  catch (e: IOException) {
            Result.Error(NetworkError.NO_INTERNET)
        }
    }

    private suspend inline fun <T> HttpResponse.handleResponse(
        successHandler: (HttpResponse) -> T
    ): Result<T, IError> {
        return when (status.value) {
            in 200..299 -> {
                try {
                    Result.Success(successHandler(this))
                } catch (e: ContentConvertException) {
                    Result.Error(NetworkError.CONFLICT)
                }
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