package uz.ssd.bookzumda.presentation.profile

import com.google.gson.Gson
import moxy.InjectViewState
import uz.ssd.bookzumda.di.PrimitiveWrapper
import uz.ssd.bookzumda.di.TYPE
import uz.ssd.bookzumda.entity.BooksEntity
import uz.ssd.bookzumda.entity.MyBookEntity
import uz.ssd.bookzumda.entity.Tuple4
import uz.ssd.bookzumda.model.data.storage.Prefs
import uz.ssd.bookzumda.model.interactor.BookIntegrator
import uz.ssd.bookzumda.model.system.flow.FlowRouter
import uz.ssd.bookzumda.presentation.global.BasePresenter
import uz.ssd.bookzumda.util.Date
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@InjectViewState
class MyBooksPresenter @Inject constructor(
    private val bookDao: BookIntegrator,
    private val prefs: Prefs,
    private val router: FlowRouter,
    @TYPE private val type: PrimitiveWrapper<Int>
) : BasePresenter<MyBooksView>() {

    private var bookInfo: List<MyBookEntity>? = null
    private var bookFavorite: List<BooksEntity>? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (type.value == ProfilePresenter.TYPE_FAV)
            getAllFavorites()
        else getAllMyBooks()
    }

    private fun getAllFavorites() {
        bookDao.getFavorites()
            .doOnSubscribe {
                viewState.showProgress(true)
            }.doAfterTerminate {
                viewState.showProgress(false)
            }
            .subscribe({ response ->
                bookFavorite = response
                viewState.showFavoriteBooks(response)
            }, { error ->
                viewState.showMessage(error.message.toString())
                error.printStackTrace()
            }).connect()
    }

    private fun getAllMyBooks() {
        bookDao.getAllMyBooks()
            .doOnSubscribe {
                viewState.showProgress(true)
            }.doAfterTerminate {
                viewState.showProgress(false)
            }
            .subscribe({ response ->
                response.filter {
                    it.date != Date.getDate()
                }

                bookInfo = response
                viewState.showBayedBooks(response)
            }, { error ->
                viewState.showMessage(error.message.toString())
                error.printStackTrace()
            }).connect()
    }

    fun onClickedBook(book: MyBookEntity) {
        viewState.createDialogMyBook(book)
    }

    fun onClickedFavoriteBook(book: BooksEntity) {
        viewState.createDialogFavorite(book)
    }


    fun myBooksDelete(book: MyBookEntity) {
        cancelOrder(book)
    }

    private fun cancelOrder(book: MyBookEntity) {
        viewState.showProgress(true)
        var urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s"
        val apiToken = "1282139079:AAGxWsN3uZnBYW8bFbOP8hMZJT7t6L_0DGs"
        val chatId = "194952542"

        val text = Tuple4(
            prefs.account.phone,
            book.amount,
            book.name,
            book.photo ?: "",
            book.author ?: "" + "cancel"
        )

        val json = Gson().toJson(text)
        urlString = String.format(urlString, apiToken, chatId, json)

        val url = URL(urlString)
        val conn = url.openConnection() as HttpURLConnection
        conn.readTimeout = 10000
        conn.connectTimeout = 15000
        conn.requestMethod = "POST"
        conn.doInput = true
        conn.doOutput = true

        try {
            val inputStream = BufferedInputStream(conn.inputStream)
            val br = BufferedReader(InputStreamReader(inputStream))
            val response = br.readText()
            viewState.showProgress(false)
            bookDao.cancelOrder(book)
        } catch (ex: Exception) {
            viewState.showProgress(false)
            viewState.showError(ex.message.toString())
        }
    }

    fun myFavoriteDelete(book: BooksEntity) {
        book.favorite = 0
        bookDao.removeFavorite(book)
    }

    fun onBackPressed() = router.exit()

}