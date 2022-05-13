package com.example.libraryapproom.api.dataClass

import com.google.gson.annotations.SerializedName

data class Borrow(
    @SerializedName("borrowId"    ) var borrowId    : Int?    = null,
    @SerializedName("studentId"   ) var studentId   : Int?    = null,
    @SerializedName("bookId"      ) var bookId      : Int?    = null,
    @SerializedName("takenDate"   ) var takenDate   : String? = null,
    @SerializedName("broughtDate" ) var broughtDate : String? = null
)
