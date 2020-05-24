package uz.ssd.bookzumda.ui.main

import toothpick.Scope
import uz.ssd.bookzumda.Screens
import uz.ssd.bookzumda.di.module.GlobalBottomBar
import uz.ssd.bookzumda.ui.global.FlowFragment

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class MainFlowFragment: FlowFragment() {

    override fun installModules(scope: Scope) {
        super.installModules(scope)
        scope.installModules(
            GlobalBottomBar()
        )
    }

    override fun getLaunchScreen() = Screens.Main
}