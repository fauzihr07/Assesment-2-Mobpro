package org.d3if3012.assesment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listBarang")
data class Barang(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val barang: String,
    val jenis: String,
    val jumlah: String

)
