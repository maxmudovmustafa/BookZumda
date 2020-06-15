package uz.ssd.bookzumda.presentation.dashboard

import moxy.InjectViewState
import uz.ssd.bookzumda.Screens
import uz.ssd.bookzumda.model.entity.BooksEntity
import uz.ssd.bookzumda.model.data.storage.Prefs
import uz.ssd.bookzumda.model.interactor.BookIntegrator
import uz.ssd.bookzumda.model.system.flow.FlowRouter
import uz.ssd.bookzumda.presentation.global.BasePresenter
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@InjectViewState
class DashboardPresentor @Inject constructor(
    private val bookDao: BookIntegrator,
    private val router: FlowRouter,
    private val prefs: Prefs
) : BasePresenter<DashboardView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadAllBooks()
        getBooksByType()
    }

    private fun loadAllBooks() {
        bookDao.getAllBooks()
            .doOnSubscribe {
                viewState.showProgress(true)
            }.doAfterTerminate {
                viewState.showProgress(false)
            }
            .subscribe({ response ->
                if (prefs.item == "0") {
                    saveBooks(response)
                    val dashboardList = response.filter {
                        it.janr_id == "50"
                    }
                    viewState.showCardsList(dashboardList)
                }
                val fictionBooks = response.filter {
                    it.janr_id == "100"
                }
                viewState.showAllBooks(fictionBooks)

            }, { error ->
                viewState.showMessage(error.message.toString())
                error.printStackTrace()
            }).connect()
    }


    private fun getBooksByType() {
        bookDao.getBookSByjanr("50")
            .doOnSubscribe {
                viewState.showProgress(true)
            }.doAfterTerminate {
                viewState.showProgress(false)
            }
            .subscribe({ response ->
                viewState.showCardsList(response)
            }, { error ->
                viewState.showMessage(error.message.toString())
                error.printStackTrace()
            }).connect()
    }

    fun reload() {
        bookDao.refreshBooks()
            .doOnSubscribe {
                viewState.showProgress(true)
            }.doAfterTerminate {
                viewState.showProgress(false)
            }
            .subscribe({ response ->
                viewState.showCardsList(response)
            }, { error ->
                viewState.showMessage(error.message.toString())
                error.printStackTrace()
            }).connect()
    }

    private fun saveBooks(books: List<BooksEntity>) {
        bookDao.saveBooks(books)
            .doOnSubscribe {
                viewState.showProgress(true)
            }.doAfterTerminate {
                viewState.showProgress(false)
            }
            .subscribe({ response ->
                getBooksByType()
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