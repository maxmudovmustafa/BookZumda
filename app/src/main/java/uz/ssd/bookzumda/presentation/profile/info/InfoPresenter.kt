package uz.ssd.bookzumda.presentation.profile.info

import moxy.InjectViewState
import uz.ssd.bookzumda.Screens
import uz.ssd.bookzumda.entity.UserAccount
import uz.ssd.bookzumda.model.data.storage.Prefs
import uz.ssd.bookzumda.model.system.flow.FlowRouter
import uz.ssd.bookzumda.presentation.global.BasePresenter
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@InjectViewState
class InfoPresenter @Inject constructor(
    private val prefs: Prefs,
    private val router: FlowRouter
) : BasePresenter<InfoView>() {

    private var account = UserAccount("", "", "")

    override fun onFirstViewAttach() {
        account = prefs.account
    }


    fun onBackPressed() = router.exit()

}