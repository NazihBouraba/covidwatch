package com.cowa.covidwatch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TokenAnswer(
  val  access_token: String,
  val expires_in: String,
  val  token_type: String,
  val scope: String,
  val  refresh_token: String


) : Parcelable {
}