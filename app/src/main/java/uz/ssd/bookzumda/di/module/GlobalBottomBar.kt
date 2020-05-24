package uz.ssd.bookzumda.di.module

import toothpick.config.Module
import uz.ssd.bookzumda.presentation.global.GlobalBottomBarController

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class GlobalBottomBar : Module() {
    init {
        bind(GlobalBottomBarController::class.java).toInstance(GlobalBottomBarController())
    }
}