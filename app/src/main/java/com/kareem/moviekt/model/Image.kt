package com.kareem.moviekt.model

data class Image(
    val id: Int,
    val backdrops: List<image1>,
    val poaster: List<image2>
)

data class image1(
    var height:Int,
    var width:Int
)
data class image2(
    var height:Int,
    var width:Int
)