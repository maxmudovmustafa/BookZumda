package uz.ssd.bookzumda.model.interactor

import android.content.Context
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.Observable
import io.reactivex.Single
import uz.ssd.bookzumda.entity.BooksEntity
import uz.ssd.bookzumda.entity.MyBookEntity
import uz.ssd.bookzumda.entity.category.CategoryEntity
import uz.ssd.bookzumda.model.data.BooksDao
import uz.ssd.bookzumda.model.data.CategoryDao
import uz.ssd.bookzumda.model.data.MyBookDao
import uz.ssd.bookzumda.model.data.storage.Prefs
import uz.ssd.bookzumda.model.system.SchedulersProvider
import java.io.IOException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
class BookIntegrator @Inject constructor(
    private val booksDao: BooksDao,
    private val myBooksDao: MyBookDao,
    private val categoryDao: CategoryDao,
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
                } else if (prefs.currentDate == getDate()) {
                    it
                } else {
                    prefs.currentDate = getDate()
                    prefs.item = "0"
                    readJson()
                }
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


    fun getFavorites(): Single<List<BooksEntity>> {
        return booksDao.getFavorites(1)
            .map {
                if (it.isNullOrEmpty()) {
                    emptyList()
                } else it
            }.subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
    }

    fun getBookAuthor(author: String): Single<List<BooksEntity>> {
        return booksDao.getAuthorBooks(author)
            .map {
                if (it.isNullOrEmpty()) {
                    emptyList()
                } else it
            }.subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
    }

    fun getBooksByJanr(janr_type: String): Single<List<BooksEntity>> {
        return booksDao.getMoreBooks(janr_type)
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
        val json =
            getJsonFromURL("https://raw.githubusercontent.com/MrShoxruxbek/Cloud_Image/master/dashboard/cv.json")
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
        val listOfBooks = gson.fromJson(json, Array<BooksEntity>::class.java).toList()
        return listOfBooks

//        val booksList = moshi.adapter<List<BooksEntity>>(booksType).fromJson(jsonString)
//            ?: emptyList()
//        return booksList
    }

    fun getJsonFromURL(wantedURL: String): String {
        return URL(wantedURL).readText()
    }


    fun getAllMyBooks(): Single<List<MyBookEntity>> {
        return myBooksDao.getAllBook()
            .map {
                if (it.isNullOrEmpty()) {
                    emptyList()
                } else it
            }.subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
    }

    fun addToFavorite2(book: BooksEntity): Observable<String> =
        Observable.create<String> {
            try {
                it.onNext("")
            } catch (e: Throwable) {
                it.onError(e)
            }
        }.doAfterNext {
            booksDao.update(book)
        }.subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())

    fun addToFavorite(book: BooksEntity) {
        booksDao.addBook(book)
    }

    fun removeFavorite(book: BooksEntity) {
        booksDao.addBook(book)
    }

    fun addToBuy(book: MyBookEntity) {
        myBooksDao.addBook(book)
    }

    fun cancelOrder(book: MyBookEntity) {
        myBooksDao.delete(book)
    }

    fun getAllCategory() = categoryDao.getAllCategory()
        .map {
            when {
                it.isNullOrEmpty() -> {
                    readCategory()
                }
                prefs.currentDate == getDate() -> {
                    it
                }
                else -> {
                    prefs.currentDate = getDate()
                    readCategory()
                }
            }
        }.subscribeOn(scheduler.io())
        .observeOn(scheduler.ui())


    private fun readCategory(): List<CategoryEntity> {
        val json =
            getJsonFromURL("https://raw.githubusercontent.com/MrShoxruxbek/Cloud_Image/master/dashboard/category.json")
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

        return GsonBuilder().create().fromJson(json, Array<CategoryEntity>::class.java).toList()
    }


    private fun getDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy")
        return sdf.format(Date())
    }
}
