package uz.ssd.bookzumda.ui.profile

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_my_books.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module
import uz.ssd.bookzumda.R
import uz.ssd.bookzumda.di.PrimitiveWrapper
import uz.ssd.bookzumda.di.TYPE
import uz.ssd.bookzumda.model.entity.BooksEntity
import uz.ssd.bookzumda.model.entity.MyBookEntity
import uz.ssd.bookzumda.presentation.profile.MyBooksPresenter
import uz.ssd.bookzumda.presentation.profile.MyBooksView
import uz.ssd.bookzumda.presentation.search.list.AllBooksAdapter
import uz.ssd.bookzumda.ui.global.*
import uz.ssd.bookzumda.util.Dialog

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class MyBooksFragment : BaseFragment(), MyBooksView {
    override val layoutRes = R.layout.fragment_my_books

    private val type: Int by argument(TYPE_FRAGMENT)

    @InjectPresenter
    lateinit var presentor: MyBooksPresenter

    @ProvidePresenter
    fun getProviderPresenter(): MyBooksPresenter =
        scope.getInstance(MyBooksPresenter::class.java)

    private var amount: Long = 0

    private val booksAdapter: MyBooksList by lazy {
        MyBooksList { presentor.onClickedBook(it) }
    }

    private val favoriteBooksAdapter: AllBooksAdapter by lazy {
        AllBooksAdapter { presentor.onClickedFavoriteBook(it) }
    }

    override fun installModules(scope: Scope) {
        super.installModules(scope)
        scope.installModules(
            object : Module() {
                init {
                    bind(PrimitiveWrapper::class.java)
                        .withName(TYPE::class.java)
                        .toInstance(PrimitiveWrapper(type))
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (type == 0)
            toolbar.title = getString(R.string.my_favorites)
        else toolbar.title = getString(R.string.my_books)
        toolbar.setOnClickListener {
            presentor.onBackPressed()
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            visible(false)
            adapter = this@MyBooksFragment.booksAdapter

        }
    }

    override fun showProgress(show: Boolean) {
        fullscreenProgressView.visible(show)
    }

    override fun showError(message: String) {
        val dialogBuilder = Dialog.confirmDialog(activity!!)

        dialogBuilder.setMessage(getString(R.string.error))
        dialogBuilder.setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }

        dialogBuilder.show()

    }

    override fun showWarning(message: String) {
    }

    override fun showBayedBooks(books: List<MyBookEntity>) {
        recyclerView.adapter = booksAdapter
        recyclerView.fadeIn()
        postViewAction { booksAdapter.setData(books) }
        books.forEach {
            amount += it.price
        }
        tvAmount.visible(true)
        tvAmount.text = amount.formattedMoney() + " " + getString(R.string.price)
        booksAdapter.notifyDataSetChanged()
    }

    override fun showFavoriteBooks(books: List<BooksEntity>) {
        recyclerView.adapter = favoriteBooksAdapter
        recyclerView.fadeIn()
        postViewAction { favoriteBooksAdapter.setData(books) }
        favoriteBooksAdapter.notifyDataSetChanged()
    }

    override fun createDialogMyBook(book: MyBookEntity) {
        val dialogBuilder = Dialog.confirmDialog(activity!!)
        dialogBuilder.setPositiveButton(
            getString(R.string.ok)
        ) { dialog, _ ->
            dialog.dismiss()
            booksAdapter.deleteBook(book)
            presentor.myBooksDelete(book)
        }
        dialogBuilder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }
        dialogBuilder.show()
    }

    override fun createDialogFavorite(book: BooksEntity) {
        val dialogBuilder = Dialog.confirmDialog(activity!!)
        dialogBuilder.setPositiveButton(
            getString(R.string.ok)
        ) { dialog, _ ->
            dialog.dismiss()
            favoriteBooksAdapter.deleteBook(book)
            presentor.myFavoriteDelete(book)
        }
        dialogBuilder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }
        dialogBuilder.show()
    }

    override fun onBackPressed() {
        presentor.onBackPressed()
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    companion object {
        private const val TYPE_FRAGMENT = "arg_type_fragment"
        fun create(type: Int) = MyBooksFragment().apply {
            arguments = Bundle().apply {
                putInt(TYPE_FRAGMENT, type)
            }
        }
    }
}