package com.example.libraryapproom.api.dataClass

import com.google.gson.annotations.SerializedName

data class Books(
    @SerializedName("bookId"    ) var bookId    : Int?    = null,
    @SerializedName("name"      ) var name      : String? = null,
    @SerializedName("pageCount" ) var pageCount : String?    = null,
    @SerializedName("point"     ) var point     : String?    = null,
    @SerializedName("authorId"  ) var authorId  : String?    = null,
    @SerializedName("typeId"    ) var typeId    : String?    = null,
    @SerializedName("author"    ) var author    : String? = null,
    @SerializedName("type"      ) var type      : String?   = null
)
