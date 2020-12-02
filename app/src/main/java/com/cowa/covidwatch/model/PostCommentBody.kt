package com.cowa.covidwatch.model

data class PostCommentBody(
    val content : String,
    val post: Int,
    val is_active: Boolean = true)