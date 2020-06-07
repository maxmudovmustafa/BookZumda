package uz.ssd.bookzumda.presentation.profile

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ProfileView : MvpView {

    fun showDetails(details: Details)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLogoutConfirmDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSuccessDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    class Details(
        val fullName: String,
        val phoneNumber: String
    )
}

