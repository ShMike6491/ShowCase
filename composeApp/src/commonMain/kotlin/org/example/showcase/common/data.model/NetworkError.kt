package org.example.showcase.common.data.model

import org.example.showcase.common.domain.model.IError

enum class NetworkError : IError {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN;
}