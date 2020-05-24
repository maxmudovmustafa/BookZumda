package uz.ssd.bookzumda.model.system.flow

import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
class FlowRouter(private val appRouter: Router) : Router() {
    fun startFlow(screen: SupportAppScreen) {
        appRouter.navigateTo(screen)
    }

    fun newRootFlow(screen: SupportAppScreen) {
        appRouter.newRootChain(screen)
    }

    fun finishFlow(){
        appRouter.exit()
    }
}