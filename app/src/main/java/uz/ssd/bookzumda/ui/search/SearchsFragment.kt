package uz.ssd.bookzumda.ui.search

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.item_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick
import uz.ssd.bookzumda.R
import uz.ssd.bookzumda.presentation.search.SearchPresenter
import uz.ssd.bookzumda.presentation.search.SearchsView
import uz.ssd.bookzumda.ui.global.BaseFragment
import uz.ssd.bookzumda.ui.global.showSnackMessage
import uz.ssd.bookzumda.ui.global.visible

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class SearchsFragment : BaseFragment(), SearchsView {
    override val layoutRes = R.layout.fragment_search

    @InjectPresenter
    lateinit var vendorPresenter: SearchPresenter

    @ProvidePresenter
    fun getProviderPresenter(): SearchPresenter =
        scope.getInstance(SearchPresenter::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            visible(false)
        }
        tvSearchVendor.background.alpha = 76
    }

    override fun onBackPressed() {
        vendorPresenter.onBackPressed()
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

}