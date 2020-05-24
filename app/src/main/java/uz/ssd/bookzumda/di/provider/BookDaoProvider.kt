package uz.ssd.bookzumda.di.provider

import uz.ssd.bookzumda.model.data.AppDatabase
import uz.ssd.bookzumda.model.data.BooksDao
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
class BookDaoProvider @Inject constructor(
    private val database: AppDatabase
) : Provider<BooksDao> {

    override fun get() = database.booksDao()
}