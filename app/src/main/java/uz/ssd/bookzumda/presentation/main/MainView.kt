package uz.ssd.bookzumda.presentation.main

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import uz.ssd.bookzumda.model.entity.AccountMainBadge

/**
 * Created by MrShoxruxbek on 21,May,2020
 */

@StateStrategyType(OneExecutionStateStrategy::class)
interface MainView : MvpView {
    fun showBottomBar(show: Boolean)
    fun setAssignedNotifications(badges: AccountMainBadge)
}