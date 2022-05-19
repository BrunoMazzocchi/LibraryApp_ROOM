package com.example.libraryapproom.api.dataClass

import com.google.gson.annotations.SerializedName

data class Student (
    @SerializedName("student_id") var student_id : Int? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("surname") var surname : String? = null,
    @SerializedName("birth_date") var birth_date : String? = null,
    @SerializedName("gender") var gender : String? = null,
    @SerializedName("class") var `class` : String? = null,
    @SerializedName("point") var point : Int? = null,
        )