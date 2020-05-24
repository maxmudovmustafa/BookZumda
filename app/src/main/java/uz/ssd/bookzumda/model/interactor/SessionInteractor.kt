package uz.ssd.bookzumda.model.interactor

import uz.ssd.bookzumda.model.data.storage.Prefs
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class SessionInteractor @Inject constructor(
    private val prefs: Prefs
) {

    fun getCurrentUserAccount() = prefs.account
}