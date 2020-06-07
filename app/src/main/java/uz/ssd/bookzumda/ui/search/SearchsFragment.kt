package uz.ssd.bookzumda.ui.search

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlsdev.animatedrv.AnimatedRecyclerView
import kotlinx.android.synthetic.main.fragment_search_book.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick
import uz.ssd.bookzumda.R
import uz.ssd.bookzumda.entity.BooksEntity
import uz.ssd.bookzumda.presentation.search.SearchPresenter
import uz.ssd.bookzumda.presentation.search.SearchsView
import uz.ssd.bookzumda.presentation.search.list.AllBooksAdapter
import uz.ssd.bookzumda.ui.global.BaseFragment
import uz.ssd.bookzumda.ui.global.fadeIn
import uz.ssd.bookzumda.ui.global.showSnackMessage
import uz.ssd.bookzumda.ui.global.visible
import uz.ssd.bookzumda.util.ReadJsonServer
import java.net.URL

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class SearchsFragment : BaseFragment(), SearchsView {
    override val layoutRes = R.layout.fragment_search_book

    @InjectPresenter
    lateinit var presentor: SearchPresenter

    @ProvidePresenter
    fun getProviderPresenter(): SearchPresenter =
        scope.getInstance(SearchPresenter::class.java)


    private val alllBooksAdapter: AllBooksAdapter by lazy {
        AllBooksAdapter { presentor.onClickedBook(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvSearchVendor.findViewById<ImageView>(R.id.search_close_btn).setColorFilter(Color.LTGRAY)
        val textView = tvSearchVendor.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.BLACK)
        textView.setHintTextColor(Color.LTGRAY)

        tvSearchVendor.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                alllBooksAdapter.filter.filter(newText)
                return true
            }
        })

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            visible(false)
            adapter = this@SearchsFragment.alllBooksAdapter

        }

        imgSync.setOnClickListener {
//            ReadJsonServer().getJsonFromURL()
            getJsonFromURL("https://raw.githubusercontent.com/MrShoxruxbek/Cloud_Image/master/dashboard/cv.json")
        }

        tvSearchVendor.background.alpha = 76

    }

    fun getJsonFromURL(wantedURL: String) : String {
        return URL(wantedURL).readText()
    }

    override fun showProgress(show: Boolean) {
        fullscreenProgressView.visible(show)
    }

    override fun showBooks(books: List<BooksEntity>) {
        recyclerView.fadeIn()
        postViewAction { alllBooksAdapter.setData(books) }
    }


    override fun showError(message: String) {

    }

    override fun showSuccessful(message: String) {

    }

    override fun onBackPressed() {
        presentor.onBackPressed()
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

}