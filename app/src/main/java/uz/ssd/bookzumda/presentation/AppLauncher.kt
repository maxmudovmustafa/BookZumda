package uz.ssd.bookzumda.presentation

import ru.terrakok.cicerone.Router
import uz.ssd.bookzumda.Screens
import uz.ssd.bookzumda.model.interactor.LaunchInteractor
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
class AppLauncher @Inject constructor(
    private val launchInteractor: LaunchInteractor,
    private val router: Router
) {

    fun onLaunch() {
        launchInteractor.signInToLastSession()
    }

    fun coldStart() {
        val rootScreen =
            if (launchInteractor.hasAccount) Screens.MainFlow
            else Screens.MainFlow//Screens.AuthFlow

        router.newRootScreen(rootScreen)
    }
}