package uz.ssd.bookzumda.presentation.profile

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import uz.ssd.bookzumda.model.entity.BooksEntity
import uz.ssd.bookzumda.model.entity.MyBookEntity

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface MyBooksView : MvpView {
    fun showProgress(show: Boolean)
    fun showError(message: String)
    fun showWarning(message: String)
    fun showFavoriteBooks(books: List<BooksEntity>)
    fun showBayedBooks(books: List<MyBookEntity>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun createDialogFavorite(book: BooksEntity)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun createDialogMyBook(book: MyBookEntity)

}