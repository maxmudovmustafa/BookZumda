package uz.ssd.bookzumda.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
@Entity(tableName = "book_sale")
@JsonClass(generateAdapter = true)
class BooksEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val value: String,
    val author: String?,
    val price: Long,
    val amount: Int?,
    val description: String?,
    val janr_id: String?,
    val photo: String?,
    val janr_type: String?
)