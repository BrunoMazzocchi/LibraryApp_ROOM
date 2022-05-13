package com.example.libraryapproom.api.dataClass

import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("typeId" ) var typeId : Int?    = null,
    @SerializedName("name"   ) var name   : String? = null
)
