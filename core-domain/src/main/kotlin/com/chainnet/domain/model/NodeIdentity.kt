package com.chainnet.domain.model

data class NodeIdentity(
    val id: String,
    val publicKey: ByteArray,
    val displayName: String? = null
)
