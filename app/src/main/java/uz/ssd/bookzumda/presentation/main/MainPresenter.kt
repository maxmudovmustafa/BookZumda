package uz.ssd.bookzumda.presentation.main

import moxy.InjectViewState
import uz.ssd.bookzumda.Screens
import uz.ssd.bookzumda.model.data.storage.Prefs
import uz.ssd.bookzumda.presentation.global.BasePresenter
import uz.ssd.bookzumda.presentation.global.GlobalBottomBarController
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
@InjectViewState
class MainPresenter @Inject constructor(
    private val bottomBarController: GlobalBottomBarController,
    val pref: Prefs
) : BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        bottomBarController.state.subscribe {
            viewState.showBottomBar(it)
        }.connect()
//        accountInteractor.getAccountMainBadges()
//            .subscribe(
//                { viewState.setAssignedNotifications(it) },
//                {
//                }
//            )
//            .connect()
    }
}