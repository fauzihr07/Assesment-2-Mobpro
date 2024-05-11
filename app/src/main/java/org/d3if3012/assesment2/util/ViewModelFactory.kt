package org.d3if3012.assesment2.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3012.assesment2.database.NamaBarang
import org.d3if3012.assesment2.ui.screen.DetailViewModel
import org.d3if3012.assesment2.ui.screen.MainViewModel

class ViewModelFactory(
    private val dao: NamaBarang
) : ViewModelProvider.Factory {
    @Suppress("unchceked_cast", "UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}