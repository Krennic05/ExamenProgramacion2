package cl.felixortiz.examenprogramacion2.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cl.felixortiz.examenprogramacion2.Aplicacion
import cl.felixortiz.examenprogramacion2.dao.MedidorDao
import cl.felixortiz.examenprogramacion2.entities.Medidor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListaMedidorViewModel( private val medidorDao:MedidorDao) : ViewModel() {

    var medidores by mutableStateOf(listOf<Medidor>())

    fun insertarMedidor(medidor: Medidor){
        viewModelScope.launch(Dispatchers.IO) {
            medidorDao.insert(medidor)
            obtenerMedidores()
        }
    }

    fun obtenerMedidores(): List<Medidor> {
        viewModelScope.launch(Dispatchers.IO) {
            medidores = medidorDao.getAll()
        }
        return medidores
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val aplicacion = (this[APPLICATION_KEY] as Aplicacion)
                ListaMedidorViewModel(aplicacion.medidorDao)
            }
        }
    }
}