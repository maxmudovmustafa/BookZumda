package uz.ssd.bookzumda.di.provider

import android.content.Context
import androidx.room.Room
import uz.ssd.bookzumda.model.data.AppDatabase
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
class AppDataBaseProvider @Inject constructor(
    private val context: Context
) : Provider<AppDatabase> {

    override fun get() = Room.databaseBuilder(context, AppDatabase::class.java, "books")
        .allowMainThreadQueries()
        .build()
}