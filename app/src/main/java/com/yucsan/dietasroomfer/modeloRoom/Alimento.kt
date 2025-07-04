package com.yucsan.dietasroomfer.modeloRoom

import android.util.Log
import androidx.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable
import java.util.*

// ENUM: Tipo de Componente
enum class TipoComponente {
    SIMPLE, PROCESADO, MENU, RECETA, DIETA;

    fun esSimpleOProcesado() = this == SIMPLE || this == PROCESADO
    fun esCompuesto() = this == MENU || this == RECETA || this == DIETA
}


@Entity(tableName = "componente_dieta")
data class ComponenteDieta(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "nombre") var nombre: String,
    @ColumnInfo(name = "tipo_componente") var tipo: TipoComponente = TipoComponente.SIMPLE,
    @ColumnInfo(name = "gr_hc_ini") var grHC_ini: Double = 0.0,
    @ColumnInfo(name = "gr_lip_ini") var grLip_ini: Double = 0.0,
    @ColumnInfo(name = "gr_pro_ini") var grPro_ini: Double = 0.0,
    @ColumnInfo(name = "cantidad") var cantidad: Double = 100.0
) : Serializable{


    @get:Ignore
    val totalGrHC: Double
        get() = grHC_ini

    @get:Ignore
    val totalGrLip: Double
        get() = grLip_ini

    @get:Ignore
    val totalGrPro: Double
        get() = grPro_ini

    @get:Ignore
    val totalKcal: Double
        get() = (4 * totalGrHC) + (4 * totalGrPro) + (9 * totalGrLip)

}

@Entity(
    tableName = "componente_dieta_relacion",
    primaryKeys = ["componente_padre_id", "componente_hijo_id"],
    foreignKeys = [
        ForeignKey(
            entity = ComponenteDieta::class,
            parentColumns = ["id"],
            childColumns = ["componente_padre_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ComponenteDieta::class,
            parentColumns = ["id"],
            childColumns = ["componente_hijo_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["componente_padre_id"]), Index(value = ["componente_hijo_id"])]
)
data class ComponenteDietaRelacion(
    @ColumnInfo(name = "componente_padre_id") val componentePadreId: String,
    @ColumnInfo(name = "componente_hijo_id") val componenteHijoId: String,
    @ColumnInfo(name = "cantidad") val cantidad: Double // Cantidad del ingrediente en el padre
)


data class ComponenteConIngredientes(
    @Embedded val componente: ComponenteDieta,
    @Relation(
        parentColumn = "id",
        entityColumn = "componente_padre_id",
        entity = ComponenteDietaRelacion::class
    )
    val ingredientes: List<IngredienteCantidad>
)

data class IngredienteCantidad(
    @Embedded val relacion: ComponenteDietaRelacion,
    @Relation(
        parentColumn = "componente_hijo_id",
        entityColumn = "id"
    )
    val ingrediente: ComponenteDieta
){
    @get:Ignore
    val totalGrHC: Double
        get() = ingrediente.grHC_ini * (relacion.cantidad / 100)

    @get:Ignore
    val totalGrLip: Double
        get() = ingrediente.grLip_ini * (relacion.cantidad / 100)

    @get:Ignore
    val totalGrPro: Double
        get() = ingrediente.grPro_ini * (relacion.cantidad / 100)

    @get:Ignore
    val totalKcal: Double
        get() = (4 * totalGrHC) + (4 * totalGrPro) + (9 * totalGrLip)
}



