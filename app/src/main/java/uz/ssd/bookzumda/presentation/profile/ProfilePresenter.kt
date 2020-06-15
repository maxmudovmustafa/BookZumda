package uz.ssd.bookzumda.presentation.profile

import moxy.InjectViewState
import uz.ssd.bookzumda.Screens
import uz.ssd.bookzumda.model.entity.UserAccount
import uz.ssd.bookzumda.model.data.storage.Prefs
import uz.ssd.bookzumda.model.system.flow.FlowRouter
import uz.ssd.bookzumda.presentation.global.BasePresenter
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@InjectViewState
class ProfilePresenter @Inject constructor(
    private val prefs: Prefs,
    private val router: FlowRouter
) : BasePresenter<ProfileView>() {

    private var account = UserAccount("", "", "")

    override fun onFirstViewAttach() {
        account = prefs.account
        viewState.showDetails(
            ProfileView.Details(
                account.full_name,
                    account.phone
            )
        )
    }

    fun onClickLogout() {
        viewState.showLogoutConfirmDialog()
    }

    fun onClickConfirmLogout() {
    }

    fun saveData(name: String, phone: String) {
        account.full_name = name
        account.phone = phone
        prefs.account = account
        viewState.showSuccessDialog()
    }

    fun showMyBooks() {
        router.newChain(Screens.MyBooks(TYPE_BUY))
    }

    fun showFavorites() {
        router.newChain(Screens.MyBooks(TYPE_FAV))
    }

    fun openInfo() {
        router.newChain(Screens.Info)
    }

    fun onBackPressed() = router.exit()

    companion object {
        const val TYPE_FAV = 0
        const val TYPE_BUY = 1
    }
}