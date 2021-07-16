package com.example.testcustomview

import android.os.Parcel
import android.os.Parcelable

data class Sentence(
    var bg: Long? = 0,
    var ed: Long? = 0,
    var onebest: String? = "",
    var speaker: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(bg)
        parcel.writeValue(ed)
        parcel.writeString(onebest)
        parcel.writeString(speaker)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Sentence> {
        override fun createFromParcel(parcel: Parcel): Sentence {
            return Sentence(parcel)
        }

        override fun newArray(size: Int): Array<Sentence?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other != null && other is Sentence) {
            if (bg == other.bg && ed == other.ed && onebest == other.onebest) {
                return true
            }
        }
        return false
    }

    override fun hashCode(): Int {
        return bg.hashCode() + ed.hashCode() + onebest.hashCode()
    }
}