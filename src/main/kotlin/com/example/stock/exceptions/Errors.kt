package com.example.stock.exceptions

val ERRORS = mapOf(
    "INVALID_PAYLOAD" to mapOf("errorType" to "VALIDATION", "detail" to "invalid data on payload"),
    "GENERIC_ERROR" to mapOf("errorType" to "GENERIC", "detail" to "generic error on request")
)