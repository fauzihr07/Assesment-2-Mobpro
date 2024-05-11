package org.d3if3012.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3012.assesment2.database.NamaBarang
import org.d3if3012.assesment2.model.Barang

class DetailViewModel(private val dao: NamaBarang) : ViewModel() {
    fun insert(barang: String, jenis: String, jumlah: String){
        val Barangs = Barang(
            barang = barang,
            jenis = jenis,
            jumlah = jumlah
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(Barangs)
        }
    }
    suspend fun getBarang(id: Long): Barang? {
        return dao.getBarangById(id)
    }

    fun update(id: Long, barang: String, jenis: String, jumlah: String){
        val Barangs = Barang(
            id = id,
            barang = barang,
            jenis = jenis,
            jumlah = jumlah
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(Barangs)
        }
    }

    fun delete(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}