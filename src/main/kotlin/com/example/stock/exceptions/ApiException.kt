package com.example.stock.exceptions

data class ApiException(var errorCode: String, var detail: String? = null) : RuntimeException() {
    var errorType: String = ""

    init {
        var err: Map<String, String>? = mapOf()
        if (ERRORS.filter { it.key == errorCode }.none()) {
            errorCode = "GENERIC_ERROR"
        }
        err = ERRORS[errorCode]
        errorType = err?.get("errorType") ?: ""
        detail = detail ?: err?.get("detail")
    }

}