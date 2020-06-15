package uz.ssd.bookzumda.model.data

import androidx.room.*
import io.reactivex.Single
import uz.ssd.bookzumda.model.entity.category.CategoryEntity

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
@Dao
interface CategoryDao {
    @Query("SELECT * FROM category_book")
    fun getAllCategory(): Single<List<CategoryEntity>>
    
    @Update
    fun update(book: CategoryEntity)

    @Query("SELECT * FROM category_book WHERE idColumn = :id ")
    fun getCategory(id: Int): Single<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCategory(category: CategoryEntity): Long
}