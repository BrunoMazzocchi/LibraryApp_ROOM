package com.example.libraryapproom.api.dataClass

import com.google.gson.annotations.SerializedName

data class Student (
    @SerializedName("studentId") var studentId : Int? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("surname") var surname : String? = null,
    @SerializedName("dateOfBirth") var dateOfBirth : String? = null,
    @SerializedName("gender") var gender : String? = null,
    @SerializedName("classroom") var classroom : String? = null,
    @SerializedName("point") var point : Int? = null,
        )