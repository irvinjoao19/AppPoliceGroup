package com.dsige.police.group.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class ParteDiarioPhoto {
    @PrimaryKey(autoGenerate = true)
    var photoId: Int = 0
    var parteDiarioId: Int = 0
    var fotoUrl: String = ""
    var estado: Int = 0
    var identity : Int = 0
    var active : Int = 0
}