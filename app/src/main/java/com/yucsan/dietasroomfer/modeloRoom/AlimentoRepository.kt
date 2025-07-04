package com.yucsan.dietasroomfer.modeloRoom


import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class ComponenteDietaRepository(private val dao: ComponenteDietaDao) {

   // Obtener todos los componentes
   fun getComponenteDietas(): Flow<List<ComponenteDieta>> =
      dao.getComponenteDietas().flowOn(Dispatchers.IO)

   // Obtener componentes por nombre
   fun getComponenteDietaByName(nombre: String): Flow<List<ComponenteDieta>> =
      dao.getComponenteDietaByName(nombre).flowOn(Dispatchers.IO)

   // Obtener componente por ID
   fun getComponenteDietaById(id: String): Flow<ComponenteDieta?> =
      dao.getComponenteDietaById(id).flowOn(Dispatchers.IO)

   // Obtener un componente con sus ingredientes
   fun obtenerComponenteDietaConIngredientes(componenteId: String): Flow<ComponenteConIngredientes> =
      dao.obtenerComponenteDietaConIngredientes(componenteId).flowOn(Dispatchers.IO)

   // Obtener todos los componentes con ingredientes
   fun obtenerTodosComponentesConIngredientes(): Flow<List<ComponenteConIngredientes>> =
      dao.obtenerTodosComponentesConIngredientes().flowOn(Dispatchers.IO)

   // Obtener ingredientes de un componente específico
   fun obtenerIngredientesDeComponente(componenteId: String): LiveData<List<IngredienteCantidad>> {
      return dao.obtenerRelacionesPorComponente(componenteId).asLiveData()
   }

   // Insertar un componente
   suspend fun insertarComponenteDieta(componente: ComponenteDieta) {
      dao.insertarComponenteDieta(componente)
   }

   // Actualizar un componente
   suspend fun actualizarComponenteDieta(componente: ComponenteDieta) {
      dao.actualizarComponenteDieta(componente)
   }

   // Borrar un componente
   suspend fun borrarComponenteDieta(componente: ComponenteDieta) {
      dao.borrarComponenteDieta(componente)
   }

   // Borrar todos los componentes
   suspend fun borrarTodosLosComponenteDietas() {
      dao.borrarTodosLosComponenteDietas()
   }

   // Borrar un componente por ID
   suspend fun borrarComponenteDietaPorId(id: String) {
      dao.borrarComponenteDietaPorId(id)
   }

   // Insertar una relación (agregar ingrediente a un componente compuesto)
   suspend fun insertarRelacionComponente(relacion: ComponenteDietaRelacion) {
      dao.insertarRelacionComponente(relacion)
   }

   // Borrar una relación (eliminar ingrediente de un componente compuesto)   ///// noooo
   suspend fun borrarRelacionComponente(relacion: ComponenteDietaRelacion) {
      dao.borrarRelacionComponente(relacion)
   }

   suspend fun borrarRelacionesDeComponente(componenteId: String) {
      dao.borrarRelacionesDeComponente(componenteId)
   }

   suspend fun borrarComponenteConTodo(componente: ComponenteDieta) {   ///------------
      dao.borrarComponenteYRelaciones(componente.id)
   }




   suspend fun borrarRelacionesDeIngrediente(idIngrediente: String) {
      dao.borrarRelacionesDeIngrediente(idIngrediente)
   }

   // Borrar todas las relaciones
   suspend fun borrarTodasLasRelaciones() {
      dao.borrarTodasLasRelaciones()
   }
}


