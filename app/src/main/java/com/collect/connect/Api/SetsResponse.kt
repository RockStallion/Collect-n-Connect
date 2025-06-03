package com.collect.connect.Api


data class SetsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<LegoSet>
)