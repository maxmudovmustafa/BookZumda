package uz.ssd.bookzumda.model.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.ssd.bookzumda.entity.BooksEntity

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
@Dao
interface BooksDao {
    @Query("SELECT * FROM book_sale")
    fun getAllBook(): Single<List<BooksEntity>>

    @Query("SELECT * FROM book_sale WHERE janr_id = :id_janr")
    fun getBooksByJanr(id_janr: String): Single<List<BooksEntity>>

    @Query("SELECT * FROM book_sale WHERE id = :id ")
    fun getBook(id: Int): Single<BooksEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBook(book: BooksEntity): Long
}