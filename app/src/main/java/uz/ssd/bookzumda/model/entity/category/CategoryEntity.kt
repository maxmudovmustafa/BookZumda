package uz.ssd.bookzumda.model.entity.category

import androidx.annotation.NonNull
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import uz.ssd.bookzumda.model.entity.BooksEntity

/**
 * Created by MrShoxruxbek on 05,June,2020
 */
@Entity(tableName = "category_book")
@JsonClass(generateAdapter = true)
class CategoryEntity(
    @PrimaryKey
    @NonNull
    @SerializedName("id_column")
    val idColumn: Int,
    val name: String,
    var amount: Int
)

class ModelRelation {
    @Embedded
    lateinit var info: CategoryEntity
    @Relation(
        parentColumn = "id_could",
        entityColumn = "type_id_book",
        entity = BooksEntity::class)
    var books: List<BooksEntity>? = arrayListOf()
}

@Entity(foreignKeys = [ForeignKey(
    entity = CategoryEntity::class,
    parentColumns = arrayOf("id_column"),
    childColumns = arrayOf("type_id"),
    onDelete = ForeignKey.CASCADE
)], tableName = "relation")

data class ModelKey(
    @PrimaryKey
    @NonNull
    val id: Int,
    @SerializedName("type_id")
    var typeId: Int ? = 1
)

@Entity(tableName = "relation_category")
data class RelationIds(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("id_book")
    val idBook: Int,
    @SerializedName("id_category")
    val idCategory: Int
)