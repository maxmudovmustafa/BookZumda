package uz.ssd.bookzumda.presentation.dashboard.detail

import moxy.InjectViewState
import uz.ssd.bookzumda.di.BOOK_ID
import uz.ssd.bookzumda.di.PrimitiveWrapper
import uz.ssd.bookzumda.model.interactor.BookIntegrator
import uz.ssd.bookzumda.model.system.flow.FlowRouter
import uz.ssd.bookzumda.presentation.global.BasePresenter
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@InjectViewState
class DetailBookPresentor @Inject constructor(
    private val bookDao: BookIntegrator,
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

    fun onBackPressed() = router.exit()

}