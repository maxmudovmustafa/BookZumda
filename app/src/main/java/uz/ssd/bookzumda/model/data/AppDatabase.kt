package uz.ssd.bookzumda.model.data

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.ssd.bookzumda.model.entity.BooksEntity
import uz.ssd.bookzumda.model.entity.MyBookEntity
import uz.ssd.bookzumda.model.entity.category.CategoryEntity

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
@Database(entities = [
BooksEntity::class,
CategoryEntity::class,
MyBookEntity::class
],
    version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun booksDao() : BooksDao
    abstract fun myBookDao() : MyBookDao
    abstract fun myCategory() : CategoryDao

}