package uz.ssd.bookzumda.model.data

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.ssd.bookzumda.entity.BooksEntity

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
@Database(entities = [
BooksEntity::class
],
    version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun booksDao() : BooksDao

}