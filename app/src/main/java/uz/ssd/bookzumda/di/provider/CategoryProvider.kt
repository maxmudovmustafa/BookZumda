package uz.ssd.bookzumda.di.provider

import uz.ssd.bookzumda.model.data.AppDatabase
import uz.ssd.bookzumda.model.data.CategoryDao
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
class CategoryProvider @Inject constructor(
    private val database: AppDatabase
) : Provider<CategoryDao> {

    override fun get() = database.myCategory()
}