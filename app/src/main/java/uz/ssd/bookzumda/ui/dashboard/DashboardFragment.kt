package uz.ssd.bookzumda.ui.dashboard

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.fullscreenProgressView
import kotlinx.android.synthetic.main.item_dashboard_book.*
import kotlinx.android.synthetic.main.item_dashboard_pager.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick
import uz.ssd.bookzumda.R
import uz.ssd.bookzumda.entity.BooksEntity
import uz.ssd.bookzumda.presentation.dashboard.DashboardPresentor
import uz.ssd.bookzumda.presentation.dashboard.DashboardView
import uz.ssd.bookzumda.ui.dashboard.list.BooksEntityList
import uz.ssd.bookzumda.ui.dashboard.list.BooksViewPager
import uz.ssd.bookzumda.ui.global.*

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class DashboardFragment : BaseFragment(), DashboardView {
    override val layoutRes = R.layout.fragment_dashboard

    @InjectPresenter
    lateinit var presentor: DashboardPresentor

    @ProvidePresenter
    fun getProviderPresenter(): DashboardPresentor =
        scope.getInstance(DashboardPresentor::class.java)

    private val booksViewPager: BooksViewPager by lazy {
        BooksViewPager(
            { book, _ ->
                presentor.onClickedBook(book)
            }, {
            })
    }

    private val bookAdapter: BooksEntityList by lazy {
        BooksEntityList { presentor.onClickedBook(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cardsRecyclerView.adapter = this@DashboardFragment.booksViewPager
        cardsRecyclerView.setItemTransitionTimeMillis(150)

        recyclerView.apply {
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                else GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            setRecycledViewPool(RecyclerView.RecycledViewPool())
            adapter = this@DashboardFragment.bookAdapter
            visible(false)
        }



        tvSearchVendor.findViewById<ImageView>(R.id.search_close_btn).setColorFilter(Color.LTGRAY)
        val textView = tvSearchVendor.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.BLACK)
        textView.setHintTextColor(Color.LTGRAY)

        tvSearchVendor.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                bookAdapter.filter.filter(newText)
                return true
            }
        })

        tvSearchVendor.background.alpha = 40
    }

    override fun showProgress(show: Boolean) {
        fullscreenProgressView.visible(show)
    }

    override fun showCardsList(cardsList: List<BooksEntity>) {
        cardsRecyclerView.fadeIn()
        booksViewPager.setData(cardsList)
        pageIndicator.attachTo(cardsRecyclerView)
        activity?.hideSoftInput()
        activity?.hideKeyboard()
    }

    override fun showAllBooks(cardsList: List<BooksEntity>) {
        recyclerView.fadeIn()
        bookAdapter.setData(cardsList)
        activity?.hideSoftInput()
        activity?.hideKeyboard()
    }

    override fun onBackPressed() {
        presentor.onBackPressed()
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }
}