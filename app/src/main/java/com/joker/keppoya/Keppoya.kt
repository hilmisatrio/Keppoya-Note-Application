package com.joker.keppoya

import android.os.Parcel
import android.os.Parcelable

data class Keppoya(
    val id: String?,
    val title: String?,
    val text: String?,
    val star: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(star)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Keppoya> {
        override fun createFromParcel(parcel: Parcel): Keppoya {
            return Keppoya(parcel)
        }

        override fun newArray(size: Int): Array<Keppoya?> {
            return arrayOfNulls(size)
        }
    }
}