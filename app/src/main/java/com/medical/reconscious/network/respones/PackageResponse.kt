package com.medical.reconscious.network.respones

data class PackageResponse(
    val id: Int,
    val name: String,
    val icon: String,
    val price: Float,
    val description: String
)