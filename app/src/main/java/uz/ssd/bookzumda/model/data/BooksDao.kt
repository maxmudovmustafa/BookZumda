package uz.ssd.bookzumda.model.data

import androidx.room.*
import io.reactivex.Single
import uz.ssd.bookzumda.model.entity.BooksEntity

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
@Dao
interface BooksDao {
    @Query("SELECT * FROM book_sale")
    fun getAllBook(): Single<List<BooksEntity>>

    @Query("SELECT * FROM book_sale WHERE janr_id = :id_janr")
    fun getBooksByJanr(id_janr: String): Single<List<BooksEntity>>

    @Query("SELECT * FROM book_sale WHERE author = :author")
    fun getAuthorBooks(author: String): Single<List<BooksEntity>>

    @Query("SELECT * FROM book_sale WHERE favorite = :favorite")
    fun getFavorites(favorite: Int): Single<List<BooksEntity>>

    @Query("SELECT * FROM book_sale WHERE  janr_type LIKE '%' || :janr || '%' ")
    fun getMoreBooks(janr: String): Single<List<BooksEntity>>

    @Update
    fun update(book: BooksEntity)

    @Query("SELECT * FROM book_sale WHERE id = :id ")
    fun getBook(id: Int): Single<BooksEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBook(book: BooksEntity): Long
}