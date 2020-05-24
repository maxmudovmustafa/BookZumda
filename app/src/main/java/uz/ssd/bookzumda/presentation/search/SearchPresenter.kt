package uz.ssd.bookzumda.presentation.search

import moxy.InjectViewState
import uz.ssd.bookzumda.model.system.flow.FlowRouter
import uz.ssd.bookzumda.presentation.global.BasePresenter
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
@InjectViewState
class SearchPresenter @Inject constructor(
    private val router: FlowRouter
) : BasePresenter<SearchsView>() {

    fun onBackPressed() = router.exit()

}