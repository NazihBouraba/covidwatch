package com.cowa.covidwatch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import kotlin.collections.ArrayList

@Parcelize
data class Article(
    val id: Int,
    val title: String,
    val content: String,
    val writer: Writer,
    val timestamp: String,
    var comments: List<Comment>,
    val images: List<Image>
): Parcelable

object DataProviderSeance {

    val data: List<Article>
        get() {


            return ArrayList()
        }

}
