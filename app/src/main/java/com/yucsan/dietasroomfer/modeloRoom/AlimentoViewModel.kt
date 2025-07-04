package com.yucsan.dietasroomfer.modeloRoom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ComponenteDietaViewModel(application: Application) : AndroidViewModel(application) {

   private val dao = AppDatabase.obtenerInstancia(application).alimentoDao()
   private val repository = ComponenteDietaRepository(dao)

   val componentesDieta: LiveData<List<ComponenteDieta>> = repository.getComponenteDietas().asLiveData()

   fun getComponenteDietaByName(nombre: String): LiveData<List<ComponenteDieta>> =
      repository.getComponenteDietaByName(nombre).asLiveData()

   fun getComponenteDietaById(id: String): LiveData<ComponenteDieta?> =
      repository.getComponenteDietaById(id).asLiveData()

   fun obtenerComponenteDietaConIngredientes(componenteId: String): LiveData<ComponenteConIngredientes> =
      repository.obtenerComponenteDietaConIngredientes(componenteId).asLiveData()

   fun obtenerTodosComponentesConIngredientes(): LiveData<List<ComponenteConIngredientes>> =
      repository.obtenerTodosComponentesConIngredientes().asLiveData()

   fun obtenerIngredientesComponente(componenteId: String): LiveData<List<IngredienteCantidad>> {
      return repository.obtenerIngredientesDeComponente(componenteId)
   }

   fun insertarComponenteDieta(componente: ComponenteDieta) {
      viewModelScope.launch(Dispatchers.IO) {
         repository.insertarComponenteDieta(componente)
      }
   }

   fun actualizarComponenteDieta(componente: ComponenteDieta) {
      viewModelScope.launch(Dispatchers.IO) {
         repository.actualizarComponenteDieta(componente)
      }
   }

   fun borrarComponenteDieta(componente: ComponenteDieta) {
      viewModelScope.launch(Dispatchers.IO) {
         repository.borrarComponenteDieta(componente)
      }
   }

   fun borrarComponenteconTodo(componente: ComponenteDieta) {
      viewModelScope.launch(Dispatchers.IO) {
         repository.borrarComponenteDieta(componente)
      }
   }




   fun agregarIngrediente(componentePadreId: String, ingredienteId: String, cantidad: Double) {
      viewModelScope.launch(Dispatchers.IO) {
         val nuevaRelacion = ComponenteDietaRelacion(componentePadreId, ingredienteId, cantidad)
         repository.insertarRelacionComponente(nuevaRelacion)
      }
   }

   fun eliminarIngrediente(componentePadreId: String, ingredienteId: String) {
      viewModelScope.launch(Dispatchers.IO) {
         val relacion = ComponenteDietaRelacion(componentePadreId, ingredienteId, 0.0) // Asigna cantidad 0 si no es relevante
         repository.borrarRelacionComponente(relacion)
      }
   }

   fun borrarTodasLasRelacionesDeComponente(componenteId: String) {
      viewModelScope.launch(Dispatchers.IO) {
         repository.borrarRelacionesDeComponente(componenteId) // ------------------------------------
      }
   }


   fun borrarTodosLosComponenteDietas() {
      viewModelScope.launch(Dispatchers.IO) {
         repository.borrarTodosLosComponenteDietas()
      }
   }

   fun calcularTotalesIngredientes(componenteId: String): LiveData<Map<String, Double>> {
      return repository.obtenerComponenteDietaConIngredientes(componenteId).map { componente ->
         val totalHC = componente.ingredientes.sumOf { it.totalGrHC }
         val totalLip = componente.ingredientes.sumOf { it.totalGrLip }
         val totalPro = componente.ingredientes.sumOf { it.totalGrPro }
         val totalKcal = (4 * totalHC) + (4 * totalPro) + (9 * totalLip)

         mapOf("HC" to totalHC, "Lip" to totalLip, "Pro" to totalPro, "Kcal" to totalKcal)
      }.asLiveData()
   }
}


