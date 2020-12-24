package com.dsige.police.group.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class Empresa {
    @PrimaryKey
    var empresaId: Int = 0
    var nombreEmpresa: String = ""
}