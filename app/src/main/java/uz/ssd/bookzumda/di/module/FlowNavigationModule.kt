package uz.ssd.bookzumda.di.module

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module
import uz.ssd.bookzumda.model.system.flow.FlowRouter

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
class FlowNavigationModule(globalRouter: Router): Module() {
    init {
        val cicerone = Cicerone.create(FlowRouter(globalRouter))
        bind(FlowRouter::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
    }
}