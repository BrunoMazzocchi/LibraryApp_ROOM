package com.example.libraryapproom

import com.example.libraryapproom.bd.entidades.LibrosModels

class LibrosProvider {
    companion object{
        var BookList = listOf<LibrosModels>(
            LibrosModels("100 años de soledad", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.amazon.es%2Fsoledad-CONTEMPORANEA-Gabriel-Garcia-Marquez%2Fdp%2F8497592204&psig=AOvVaw3Dl69R2qqSqZXwkT1Ha6UJ&ust=1651304014486000&source=images&cd=vfe&ved=0CAwQjRxqFwoTCJjyi6PhuPcCFQAAAAAdAAAAABAD", "Gabriel Garcia Marques", "Drama", 471, 1),
            LibrosModels("Halo the fall of Reach", "https://m.media-amazon.com/images/I/51LKSVwgMuL.jpg","Eric Nylund", "Ciencia Ficcion", 700, 2)
        )
    }
}