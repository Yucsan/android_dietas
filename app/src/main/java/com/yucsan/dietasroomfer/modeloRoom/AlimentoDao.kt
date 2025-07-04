package com.yucsan.dietasroomfer.modeloRoom



import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ComponenteDietaDao {

   // Inserción de un componente de dieta
   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertarComponenteDieta(componente: ComponenteDieta)

   // Inserción de una relación entre componentes (ingrediente dentro de otro)
   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertarRelacionComponente(relacion: ComponenteDietaRelacion)

   // Borrar un componente de dieta
   @Delete
   suspend fun borrarComponenteDieta(componente: ComponenteDieta)


   @Transaction
   suspend fun borrarComponenteYRelaciones(componenteId: String) {
      borrarRelacionesDeComponente(componenteId) // Borra las relaciones primero
      borrarComponenteDietaPorId(componenteId) // Luego borra el componente
   }



   // Borrar una relación entre componentes
   @Delete
   suspend fun borrarRelacionComponente(relacion: ComponenteDietaRelacion)

   // Actualizar un componente de dieta
   @Update
   suspend fun actualizarComponenteDieta(componente: ComponenteDieta)

   // Obtener todos los componentes de la dieta
   @Query("SELECT * FROM componente_dieta")
   fun getComponenteDietas(): Flow<List<ComponenteDieta>>

   // Obtener un componente de la dieta por nombre
   @Query("SELECT * FROM componente_dieta WHERE nombre = :nombre")
   fun getComponenteDietaByName(nombre: String): Flow<List<ComponenteDieta>>

   // Obtener un componente de la dieta por ID
   @Query("SELECT * FROM componente_dieta WHERE id = :id")
   fun getComponenteDietaById(id: String): Flow<ComponenteDieta?>

   // Obtener un componente con sus ingredientes
   @Transaction
   @Query("SELECT * FROM componente_dieta WHERE id = :componenteId")
   fun obtenerComponenteDietaConIngredientes(componenteId: String): Flow<ComponenteConIngredientes>

   // Obtener todas las relaciones donde un componente es ingrediente de otro
   @Transaction
   @Query("SELECT * FROM componente_dieta_relacion WHERE componente_padre_id = :componenteId")
   fun obtenerRelacionesPorComponente(componenteId: String): Flow<List<IngredienteCantidad>>


   // Obtener todos los componentes con sus ingredientes
   @Transaction
   @Query("SELECT * FROM componente_dieta")
   fun obtenerTodosComponentesConIngredientes(): Flow<List<ComponenteConIngredientes>>

   // Borrar todos los componentes de la dieta
   @Query("DELETE FROM componente_dieta")
   suspend fun borrarTodosLosComponenteDietas()

   // Borrar un componente de la dieta por ID
   @Query("DELETE FROM componente_dieta WHERE id = :id")
   suspend fun borrarComponenteDietaPorId(id: String)


   @Query("DELETE FROM componente_dieta_relacion WHERE componente_padre_id = :componenteId")
   suspend fun borrarRelacionesDeComponente(componenteId: String)



   @Query("DELETE FROM componente_dieta_relacion WHERE componente_hijo_id = :idIngrediente")
   suspend fun borrarRelacionesDeIngrediente(idIngrediente: String)


   // Borrar todas las relaciones (ingredientes)
   @Query("DELETE FROM componente_dieta_relacion")
   suspend fun borrarTodasLasRelaciones()
}




