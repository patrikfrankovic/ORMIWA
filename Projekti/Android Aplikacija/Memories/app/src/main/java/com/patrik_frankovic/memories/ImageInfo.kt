package com.patrik_frankovic.memories

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageInfo(
    var imageUrl: String? = null,
    var name : String? = null,
    var description : String? = null,
    var date : String? = null,
    var format: String? = null
) : Parcelable


