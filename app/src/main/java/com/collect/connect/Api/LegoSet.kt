package com.collect.connect.Api


data class LegoSet(
    val set_num: String,
    val name: String,
    val year: Int,
    val theme_id: Int,
    val num_parts: Int,
    val set_img_url: String,
    val set_url: String
)