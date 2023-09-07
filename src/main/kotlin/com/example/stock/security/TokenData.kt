package com.example.stock.security

import org.springframework.security.core.context.SecurityContextHolder


data class TokenData(val canalVenda: String = "", val canalSolicitacao: String = "") {
    companion object MAPPER {
        fun getTokenData(): TokenData {
            return SecurityContextHolder.getContext().authentication.principal as TokenData
        }
    }
}