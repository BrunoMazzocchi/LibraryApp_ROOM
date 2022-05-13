package com.example.libraryapproom.api.dataClass

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("authorId" ) var authorId : Int?    = null,
    @SerializedName("name"     ) var name     : String? = null,
    @SerializedName("surname"  ) var surname  : String? = null
)
