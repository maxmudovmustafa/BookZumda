package uz.ssd.bookzumda.model.interactor

import toothpick.Toothpick
import uz.ssd.bookzumda.di.DI
import uz.ssd.bookzumda.di.module.ServerModule
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class LaunchInteractor @Inject constructor(
    private val sessionInteractor: SessionInteractor
) {

    val hasAccount: Boolean
        get() = sessionInteractor.getCurrentUserAccount() != null

    fun signInToLastSession() {
        if (!Toothpick.isScopeOpen(DI.SERVER_SCOPE)) {
            Toothpick
                .openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE)
                .installModules(ServerModule())
        }
    }
}