package com.cowa.covidwatch.model


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import com.facebook.AccessToken
@Parcelize
data class Token (

    val backend : String,
    val  client_id : String,
    val  client_secret : String,
    val  token : String?,
    val  grant_type : String



): Parcelable {

}