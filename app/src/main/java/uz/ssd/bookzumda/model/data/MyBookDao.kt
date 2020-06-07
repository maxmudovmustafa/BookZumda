package uz.ssd.bookzumda.model.data

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import uz.ssd.bookzumda.entity.BooksEntity
import uz.ssd.bookzumda.entity.MyBookEntity

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
@Dao
interface MyBookDao {
    @Query("SELECT * FROM my_books")
    fun getAllBook(): Single<List<MyBookEntity>>

    @Query("SELECT * FROM my_books WHERE favorite = :favotire")
    fun getFavorite(favotire: Int): Single<List<MyBookEntity>>

    @Delete
    fun delete(book: MyBookEntity)

    @Update
    fun update(book: MyBookEntity)

    @Query("SELECT * FROM my_books WHERE id = :id ")
    fun getBook(id: Int): Single<MyBookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBook(book: MyBookEntity): Long
}