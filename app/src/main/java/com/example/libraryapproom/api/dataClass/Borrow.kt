package com.example.libraryapproom.api.dataClass

import com.google.gson.annotations.SerializedName

data class Borrow(
    @SerializedName("borrowId") var borrowId: Int? = null,
    @SerializedName("student_id") var studentId: Int? = null,
    @SerializedName("student") var student: Student? = Student(),
    @SerializedName("book_id") var bookId: Int? = null,
    @SerializedName("book") var book: Books? = Books(),
    @SerializedName("takenDate") var takenDate: String? = null,
    @SerializedName("broughtDate") var broughtDate: String? = null
)
