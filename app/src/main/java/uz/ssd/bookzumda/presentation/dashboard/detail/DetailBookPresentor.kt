package uz.ssd.bookzumda.presentation.dashboard.detail

import com.google.gson.Gson
import moxy.InjectViewState
import uz.ssd.bookzumda.Screens
import uz.ssd.bookzumda.di.BOOK_ID
import uz.ssd.bookzumda.di.PrimitiveWrapper
import uz.ssd.bookzumda.model.entity.BooksEntity
import uz.ssd.bookzumda.model.entity.MyBookEntity
import uz.ssd.bookzumda.model.entity.Tuple4
import uz.ssd.bookzumda.model.data.storage.Prefs
import uz.ssd.bookzumda.model.interactor.BookIntegrator
import uz.ssd.bookzumda.model.system.flow.FlowRouter
import uz.ssd.bookzumda.presentation.global.BasePresenter
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@InjectViewState
class DetailBookPresentor @Inject constructor(
    private val bookDao: BookIntegrator,
    private val prefs: Prefs,
    private val router: FlowRouter,
    @BOOK_ID private val id_book: PrimitiveWrapper<Int>
) : BasePresenter<DetailBookView>() {

    private var bookInfo: BooksEntity? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getBookById()
    }

    fun getBookById() {
        bookDao.getSingleBook(id_book.value)
            .doOnSubscribe {
                viewState.showProgress(true)
            }.doAfterTerminate {
                viewState.showProgress(false)
            }
            .subscribe({ response ->
                bookInfo = response
                viewState.showBook(response)
                getMoreAuthor()
                getMoreBook()
            }, { error ->
                viewState.showMessage(error.message.toString())
                error.printStackTrace()
            }).connect()
    }

    fun reload() {
        bookDao.getSingleBook(id_book.value)
            .doOnSubscribe {
                viewState.showProgress(true)
            }.doAfterTerminate {
                viewState.showProgress(false)
            }
            .subscribe({ response ->
                viewState.showBook(response)
            }, { error ->
                viewState.showMessage(error.message.toString())
                error.printStackTrace()
            }).connect()
    }

    fun createDialog(value: String) {
        val account = prefs.account
        if (account.phone.isNullOrEmpty())
            viewState.showDialog()
        else buyBook(account.phone, value)
    }

    private fun getMoreAuthor() {
        bookDao.getBookAuthor(bookInfo?.author ?: "")
            .doOnSubscribe {
                viewState.showProgress(true)
            }.doAfterTerminate {
                viewState.showProgress(false)
            }
            .subscribe({ response ->
                val filteredList = response.filter {
                    it.name != bookInfo?.name ?: ""
                }
                if (filteredList.isNotEmpty())
                    viewState.showAuthors(filteredList)
            }, { error ->
                viewState.showMessage(error.message.toString())
                error.printStackTrace()
            }).connect()
    }

    private fun getMoreBook() {
        bookDao.getBooksByJanr(bookInfo?.janr_type ?: "")
            .doOnSubscribe {
                viewState.showProgress(true)
            }.doAfterTerminate {
                viewState.showProgress(false)
            }
            .subscribe({ response ->
                val filteredList = response.filter {
                    it.name != bookInfo?.name ?: ""
                }
                if (filteredList.isNotEmpty())
                    viewState.showMoreBooks(filteredList)
            }, { error ->
                viewState.showMessage(error.message.toString())
                error.printStackTrace()
            }).connect()
    }

    fun addToFavorite() {
        if (bookInfo != null) {
            bookInfo!!.favorite = 1
            bookDao.addToFavorite(bookInfo!!)
        }
    }

    fun removeFavorite() {
        if (bookInfo != null) {
            bookInfo!!.favorite = 0
            bookDao.removeFavorite(bookInfo!!)
        }
    }


    fun buyBook(phone: String, amount: String) {
        viewState.showProgress(true)
        viewState.showProgress(true)
        var urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s"
        val apiToken = "1282139079:AAGxWsN3uZnBYW8bFbOP8hMZJT7t6L_0DGs"
        val chatId = "194952542"
        viewState.showProgress(true)

        val text = Tuple4(
            phone, amount,
            bookInfo?.name ?: "",
            bookInfo?.photo ?: "",
            bookInfo?.author ?: ""
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
            if (response.isNotEmpty()) {

                saveBook(amount, response)
            }
        } catch (ex: Exception) {
            viewState.showProgress(false)
            viewState.showError(ex.message.toString())
        }
    }

    private fun saveBook(amount: String, response: String) {
        bookDao.addToBuy(
            MyBookEntity(
                bookInfo!!.id,
                bookInfo!!.name,
                bookInfo!!.author,
                bookInfo!!.price,
                amount.toInt(),
                bookInfo!!.photo,
                bookInfo!!.janr_type,
                1,
                getDate()
            )
        )
        viewState.showSuccessful(response)
        viewState.showProgress(false)
    }

    fun onClickedBook(book: BooksEntity) {
        router.newChain(Screens.DetailBook(book.id))
    }

    fun getDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy")
        return sdf.format(Date())
    }

    fun onBackPressed() = router.exit()

}