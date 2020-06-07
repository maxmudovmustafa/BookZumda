package uz.ssd.bookzumda.presentation.search

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import uz.ssd.bookzumda.entity.BooksEntity

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface SearchsView : MvpView {
    fun showProgress(show: Boolean)
    fun showBooks(books: List<BooksEntity>)
    fun showError(message: String)
    fun showSuccessful(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

}