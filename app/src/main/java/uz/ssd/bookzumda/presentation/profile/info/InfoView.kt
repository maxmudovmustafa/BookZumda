package uz.ssd.bookzumda.presentation.profile.info

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by MrShoxruxbek on 31,May,2020
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface InfoView : MvpView {
    fun showInfo(boolean: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)
}