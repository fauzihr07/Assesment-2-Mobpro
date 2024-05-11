package org.d3if3012.assesment2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3012.assesment2.model.Barang

@Dao
interface NamaBarang {
    @Insert
    suspend fun insert(listBarang: Barang)

    @Update
    suspend fun update(listBarang: Barang)

    @Query("SELECT * FROM listBarang ORDER BY jenis, jumlah")
    fun getBarang(): Flow<List<Barang>>

    @Query("SELECT * FROM listBarang WHERE id = :id")
    suspend fun getBarangById(id: Long): Barang?

    @Query("DELETE FROM listBarang WHERE id = :id")
    suspend fun deleteById(id: Long)
}