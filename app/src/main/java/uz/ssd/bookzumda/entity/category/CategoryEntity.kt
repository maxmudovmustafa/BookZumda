package uz.ssd.bookzumda.entity.category

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

/**
 * Created by MrShoxruxbek on 05,June,2020
 */
@Entity(tableName = "category_book")
@JsonClass(generateAdapter = true)
class CategoryEntity(
    @PrimaryKey
    @NonNull
    val id: Int,
    val name: String,
    var amount: Int
)