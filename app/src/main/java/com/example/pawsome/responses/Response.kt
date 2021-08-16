package com.example.pawsome.responses

import java.io.Serializable


data class PhotoResult(val breeds: List<Breeds>,
                       val id: String?,
                       val url: String?): Serializable

data class Breeds(val weight: Weight,
                  val height: Height,
                  val id: Int?,
                  val name: String?,
                  val bred_for: String?,
                  val breed_group: String?,
                  val life_span: String?,
                  val temperament: String?,
                  val reference_image_id: String?): Serializable

data class Weight(val metric: String?): Serializable
data class Height(val metric: String?): Serializable