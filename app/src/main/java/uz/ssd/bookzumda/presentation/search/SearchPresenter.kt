package uz.ssd.bookzumda.presentation.search

import moxy.InjectViewState
import uz.ssd.bookzumda.Screens
import uz.ssd.bookzumda.entity.BooksEntity
import uz.ssd.bookzumda.model.data.storage.Prefs
import uz.ssd.bookzumda.model.interactor.BookIntegrator
import uz.ssd.bookzumda.model.system.flow.FlowRouter
import uz.ssd.bookzumda.presentation.global.BasePresenter
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@InjectViewState
class SearchPresenter @Inject constructor(
    private val bookDao: BookIntegrator,
    private val prefs: Prefs,
    private val router: FlowRouter
) : BasePresenter<SearchsView>() {

    private var bookInfo: List<BooksEntity>? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getAllBooks()
    }

    fun getAllBooks() {
        bookDao.getAllBooks()
            .doOnSubscribe {
                viewState.showProgress(true)
            }.doAfterTerminate {
                viewState.showProgress(false)
            }
            .subscribe({ response ->
                bookInfo = response
                viewState.showBooks(response)
            }, { error ->
                viewState.showMessage(error.message.toString())
                error.printStackTrace()
            }).connect()
    }

    fun onClickedBook(book: BooksEntity) {
        router.newChain(Screens.DetailBook(book.id))
    }


    fun onBackPressed() = router.exit()

}