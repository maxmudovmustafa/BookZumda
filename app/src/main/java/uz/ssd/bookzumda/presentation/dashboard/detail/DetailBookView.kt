package uz.ssd.bookzumda.presentation.dashboard.detail

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import uz.ssd.bookzumda.entity.BooksEntity

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface DetailBookView : MvpView {
    fun showProgress(show: Boolean)
    fun showBook(books: BooksEntity)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

}