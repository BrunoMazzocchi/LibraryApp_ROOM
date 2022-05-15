package com.example.libraryapproom.api.dataClass

import com.google.gson.annotations.SerializedName

data class Books(

    @SerializedName("bookId"    ) var bookId    : Int?    = null,
    @SerializedName("name"      ) var name      : String? = null,
    @SerializedName("pageCount" ) var pageCount : Int?    = null,
    @SerializedName("point"     ) var point     : Int?    = null,
    @SerializedName("authorId"  ) var authorId  : Int?    = null,
    @SerializedName("typeId"    ) var typeId    : Int?    = null,
    @SerializedName("author"    ) var author    : Author? = Author(),
    @SerializedName("type"      ) var type      : Type?   = Type()

)
