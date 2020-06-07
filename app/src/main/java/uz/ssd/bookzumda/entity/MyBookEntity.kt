package uz.ssd.bookzumda.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
@Entity(tableName = "my_books")
@JsonClass(generateAdapter = true)
class MyBookEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val author: String?,
    val price: Long,
    val amount: Int,
    val photo: String?,
    val janr_type: String?,
    val favorite: Int,
    val date: String
)