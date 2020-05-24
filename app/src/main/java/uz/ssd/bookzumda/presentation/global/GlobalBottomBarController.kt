package uz.ssd.bookzumda.presentation.global

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import java.util.*

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class GlobalBottomBarController {
    private val stateRelay = PublishRelay.create<Boolean>()

    val state: Observable<Boolean> = stateRelay
    fun show() = stateRelay.accept(true)
    fun hide() = stateRelay.accept(false)
}