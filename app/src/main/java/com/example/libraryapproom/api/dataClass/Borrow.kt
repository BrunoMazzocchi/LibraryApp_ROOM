package com.example.libraryapproom.api.dataClass

import com.google.gson.annotations.SerializedName

data class Borrow(
    @SerializedName("borrowId") var borrowId: Int? = null,
    @SerializedName("studentId") var studentId: Int? = null,
    @SerializedName("student") var student: Student? = Student(),
    @SerializedName("bookId") var bookId: Int? = null,
    @SerializedName("book") var book: Books? = Books(),
    @SerializedName("takenDate") var takenDate: String? = null,
    @SerializedName("broughtDate") var broughtDate: String? = null
)
