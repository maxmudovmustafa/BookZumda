package uz.ssd.bookzumda.presentation.categories

import moxy.InjectViewState
import uz.ssd.bookzumda.entity.BooksEntity
import uz.ssd.bookzumda.entity.Tuple5
import uz.ssd.bookzumda.entity.category.CategoryEntity
import uz.ssd.bookzumda.model.data.storage.Prefs
import uz.ssd.bookzumda.model.interactor.BookIntegrator
import uz.ssd.bookzumda.model.system.flow.FlowRouter
import uz.ssd.bookzumda.presentation.global.BasePresenter
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@InjectViewState
class CategoriesPresenter @Inject constructor(
    private val bookDao: BookIntegrator,
    private val prefs: Prefs,
    private val router: FlowRouter
) : BasePresenter<CategoryViewView>() {

    private var bookInfo: List<BooksEntity>? = null
    private var category: List<CategoryEntity> = emptyList()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getCategory()
    }

    private fun getCategory() {
        bookDao.getAllCategory()
            .doOnSubscribe { viewState.showProgress(true) }
            .doAfterTerminate { viewState.showProgress(false) }
            .subscribe({
                category = it
                getAllBooks()
            }, {
                viewState.showMessage(it.message.toString())
            }).connect()
    }

    private fun getAllBooks() {
        bookDao.getAllBooks()
            .doOnSubscribe {
                viewState.showProgress(true)
            }.doAfterTerminate {
                viewState.showProgress(false)
            }
            .subscribe({ response ->
                val books = response.filter { book ->
                    val list = category.first {
                        book.janr_type?.contains(it.name) ?: false
                    }
                    list != null
                }
                bookInfo = books
                viewState.showBooks(books)
            }, { error ->
                viewState.showMessage(error.message.toString())
                error.printStackTrace()
            }).connect()
    }

    fun onClickedBook(book: Tuple5) {
        viewState.showMessage(book.detail)
//        router.newChain(Screens.DetailBook(book.drawable))
    }


    fun onBackPressed() = router.exit()

}