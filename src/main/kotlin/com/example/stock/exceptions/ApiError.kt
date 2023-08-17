package com.example.stock.exceptions

import io.swagger.v3.oas.annotations.media.Schema

data class ApiError(
    @Schema(example = "INVALID_PAYLOAD")
    val errorCode: String,
    @Schema(example = "VALIDATION")
    val errorType: String,
    @Schema(example = "field required")
    val detail: String
)