package uz.ssd.bookzumda.presentation.dashboard

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import uz.ssd.bookzumda.model.entity.BooksEntity

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface DashboardView : MvpView {
    fun showProgress(show: Boolean)
    fun showCardsList(cardsList: List<BooksEntity>)
    fun showAllBooks(cardsList: List<BooksEntity>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

}