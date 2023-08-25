package com.example.stock.exceptions

val ERRORS = mapOf(
    "INVALID_PAYLOAD" to mapOf(
        "errorType" to "VALIDATION",
        "detail" to "invalid data on payload"
    ),
    "GENERIC_ERROR" to mapOf(
        "errorType" to "GENERIC",
        "detail" to "generic error on request"
    ),
    "RESOURCE_EXISTS" to mapOf(
        "errorType" to "DUPLICATE_RESOURCE",
        "detail" to "indicates a duplicate / already existing record"
    ),
    "INTERNAL_ERROR" to mapOf(
        "errorType" to "INTERNAL_ERROR",
        "detail" to "internal error on request"
    ),
    "RESOURCE_NOT_FOUND" to mapOf(
        "errorType" to "NOT_FOUND",
        "detail" to "indicates a missing / not found record"
    ),
    "EXPIRED_TOKEN" to mapOf(
        "errorType" to "Authentication",
        "detail" to "Token expired"

    )
)

