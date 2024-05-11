package org.d3if3012.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3012.assesment2.database.NamaBarang
import org.d3if3012.assesment2.model.Barang

class MainViewModel(dao: NamaBarang) : ViewModel() {
    val data: StateFlow<List<Barang>> = dao.getBarang().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

}