package uz.ssd.bookzumda.model.interactor

import android.content.Context
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.Observable
import io.reactivex.Single
import uz.ssd.bookzumda.entity.BooksEntity
import uz.ssd.bookzumda.model.data.BooksDao
import uz.ssd.bookzumda.model.data.storage.Prefs
import uz.ssd.bookzumda.model.system.SchedulersProvider
import java.io.IOException
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
class BookIntegrator @Inject constructor(
    private val booksDao: BooksDao,
    private val prefs: Prefs,
    private val context: Context,
    private val moshi: Moshi,
    private val scheduler: SchedulersProvider
) {
    private val booksType =
        Types.newParameterizedType(List::class.java, BooksEntity::class.java)

    val JSON_FILE_NAME = "csvjson.json"

    fun saveBooks(items: List<BooksEntity>): Observable<String> =
        Observable.create<String> {
            try {
                items.forEach {
                    booksDao.addBook(it)
                }
                it.onComplete()
            } catch (e: Throwable) {
                it.onError(e)
            }
        }.subscribeOn(scheduler.io()).observeOn(scheduler.ui())


    private fun saveBook(item: BooksEntity): Observable<String> =
        Observable.create<String> {
            try {
                booksDao.addBook(item)
                it.onComplete()
            } catch (e: Throwable) {
                it.onError(e)
            }
        }.subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())


    fun getAllBooks(): Single<List<BooksEntity>> {
        return booksDao.getAllBook()
            .map {
                if (it.isNullOrEmpty()) {
                    prefs.item = "0"
                    readJson()
                } else it
            }.subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
    }

    fun getBookSByjanr(id_janr: String): Single<List<BooksEntity>> {
        return booksDao.getBooksByJanr(id_janr)
            .map {
                if (it.isNullOrEmpty()) {
                    emptyList()
                } else it
            }.subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
    }

    fun getSingleBook(id: Int) =
        booksDao.getBook(id).map { it }.subscribeOn(scheduler.io()).observeOn(scheduler.ui())

    fun refreshBooks() =
        booksDao.getBook(0)
            .map {
                readJson()
            }.subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())

    fun readJson(): List<BooksEntity> {
        val jsonString: String? = try {
            val inputStream = context.assets.open(JSON_FILE_NAME)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            null
        }

        val gson = GsonBuilder().create()
        val listOfBooks = gson.fromJson(jsonString, Array<BooksEntity>::class.java).toList()
        return listOfBooks

//        val booksList = moshi.adapter<List<BooksEntity>>(booksType).fromJson(jsonString)
//            ?: emptyList()
//
//        return booksList
    }


}