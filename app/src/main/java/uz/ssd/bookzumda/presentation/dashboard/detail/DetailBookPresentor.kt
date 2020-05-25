package uz.ssd.bookzumda.presentation.dashboard.detail

import moxy.InjectViewState
import uz.ssd.bookzumda.di.BOOK_ID
import uz.ssd.bookzumda.di.PrimitiveWrapper
import uz.ssd.bookzumda.model.data.storage.Prefs
import uz.ssd.bookzumda.model.interactor.BookIntegrator
import uz.ssd.bookzumda.model.system.flow.FlowRouter
import uz.ssd.bookzumda.presentation.global.BasePresenter
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
class DetailBookPresentor @Inject constructor(
    private val bookDao: BookIntegrator,
    private val prefs: Prefs,
    private val router: FlowRouter,
    @BOOK_ID private val id_book: PrimitiveWrapper<Int>
) : BasePresenter<DetailBookView>() {

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
                viewState.showBook(response)
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

    fun createDialog() {
        viewState.showDialog()
    }

    fun buyBook(phone: String) {
        viewState.showProgress(true)
        var urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s"

        val apiToken = "1282139079:AAGxWsN3uZnBYW8bFbOP8hMZJT7t6L_0DGs"

        val chatId = "194952542"

        val text = phone

        urlString = String.format(urlString, apiToken, chatId, text)

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
                viewState.showSuccessful(response)
            }
        } catch (ex: Exception){
            viewState.showProgress(false)
            viewState.showError(ex.message.toString())
        }



    }

    fun onBackPressed() = router.exit()

}