package uz.ssd.bookzumda.presentation.profile

import moxy.InjectViewState
import uz.ssd.bookzumda.model.system.flow.FlowRouter
import uz.ssd.bookzumda.presentation.global.BasePresenter
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@InjectViewState
class ProfilePresenter @Inject constructor(
    private val router: FlowRouter
) : BasePresenter<ProfileView>() {

    override fun onFirstViewAttach() {
        viewState.showDetails(
            ProfileView.Details(
                "Shoxruxbek",
                "(\\d{5})(\\d{3})(\\d{2})(\\d{2})".toRegex().replaceFirst(
//                    account.replace("+", ""),
                    "998998578086",
                    "+$1 $2 $3 $4"
                )
        ))
    }

    fun onClickLogout() {
        viewState.showLogoutConfirmDialog()
    }

    fun onClickConfirmLogout() {
    }

    fun onBackPressed() = router.exit()

}