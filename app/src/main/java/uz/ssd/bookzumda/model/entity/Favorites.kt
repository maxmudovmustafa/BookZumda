package uz.ssd.bookzumda.model.entity

import androidx.room.Entity
import com.squareup.moshi.JsonClass

/**
 * Created by MrShoxruxbek at SSD on 5/28/20.
 */
@Entity(tableName = "book_favorite")
@JsonClass(generateAdapter = true)
class Favorites {
}