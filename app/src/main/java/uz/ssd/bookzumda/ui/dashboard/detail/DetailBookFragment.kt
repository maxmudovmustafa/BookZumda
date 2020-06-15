package uz.ssd.bookzumda.ui.dashboard.detail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.davemorrissey.labs.subscaleview.ImageSource
import kotlinx.android.synthetic.main.fragment_dashboard_detail.*
import kotlinx.android.synthetic.main.fragment_search.fullscreenProgressView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module
import uz.ssd.bookzumda.R
import uz.ssd.bookzumda.di.BOOK_ID
import uz.ssd.bookzumda.di.PrimitiveWrapper
import uz.ssd.bookzumda.model.entity.BooksEntity
import uz.ssd.bookzumda.presentation.dashboard.detail.DetailBookPresentor
import uz.ssd.bookzumda.presentation.dashboard.detail.DetailBookView
import uz.ssd.bookzumda.ui.dashboard.detail.list.MoreAuthoerList
import uz.ssd.bookzumda.ui.dashboard.detail.list.MoreBookList
import uz.ssd.bookzumda.ui.dashboard.list.BooksEntityList
import uz.ssd.bookzumda.ui.global.*
import uz.ssd.bookzumda.util.Animation
import uz.ssd.bookzumda.util.ElegantNumberButton


/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class DetailBookFragment : BaseFragment(), DetailBookView {
    override val layoutRes = R.layout.fragment_dashboard_detail

    @InjectPresenter
    lateinit var presentor: DetailBookPresentor

    @ProvidePresenter
    fun getProviderPresenter(): DetailBookPresentor =
        scope.getInstance(DetailBookPresentor::class.java)


    private val moreAuthor: MoreAuthoerList by lazy {
        MoreAuthoerList { presentor.onClickedBook(it) }
    }


    private val moreBook: MoreBookList by lazy {
        MoreBookList { presentor.onClickedBook(it) }
    }

    private val idBook: Int by argument(ID_BOOK)

    override fun installModules(scope: Scope) {
        scope.installModules(
            object : Module() {
                init {
                    bind(PrimitiveWrapper::class.java)
                        .withName(BOOK_ID::class.java)
                        .toInstance(PrimitiveWrapper(idBook))
                }
            }
        )
    }

    private val requestAdapter: BooksEntityList by lazy {
        BooksEntityList { presentor }
    }

    private var isAddedFavorite = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        imgFavorite.setOnClickListener {
            if (isAddedFavorite)
                showDialogAddFavorite()
            else {
                imgFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite))
                isAddedFavorite = true
                presentor.removeFavorite()
            }
            true
        }

        val policy = StrictMode.ThreadPolicy.Builder()
            .permitAll().build()
        StrictMode.setThreadPolicy(policy)
        btnBuy.setOnClickListener {
            showProgressDialog(true)
            presentor.createDialog(item_counter.value)
        }

        imgBack.setOnClickListener {
            presentor.onBackPressed()
        }

        item_counter.setRange(1, 100)

        item_counter.setOnClickListener(ElegantNumberButton.OnClickListener { item_counter.number })

        rcAuthor.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            setRecycledViewPool(RecyclerView.RecycledViewPool())
            adapter = this@DetailBookFragment.moreAuthor
            visible(false)
        }

        rcMoreBook.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            setRecycledViewPool(RecyclerView.RecycledViewPool())
            adapter = this@DetailBookFragment.moreBook
            visible(false)
        }

    }

    override fun showProgress(show: Boolean) {
        fullscreenProgressView.visible(show)
    }

    @SuppressLint("SetTextI18n")
    override fun showBook(books: BooksEntity) {
        tvNameBook.text = books.name
        tvAuthorName.text = books.author

        Glide.with(context!!)
            .load(books.photo)
            .into(imgBook)

        if (books.favorite == 1) {
            imgFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_red))
            isAddedFavorite = false
        }
        tvPriceBook.text = (books.price * 100L).formattedMoney() +"  "+ getString(R.string.price)
        tvDetailBook.text = books.description

        tvEmail.text = tvEmail.text.toString() +"\n"+ getString(R.string.my_email)
        tvPhone.text = tvPhone.text.toString() +"\n"+ getString(R.string.my_phone_number)
        imageView.setImage(ImageSource.resource(R.drawable.geo_location))
        activity?.hideSoftInput()
        activity?.hideKeyboard()
    }

    override fun showAuthors(books: List<BooksEntity>) {
        tvFromThisAuthor.visible(true)
        rcAuthor.fadeIn()
        postViewAction { moreAuthor.setData(books) }
        moreAuthor.notifyDataSetChanged()
    }

    override fun showMoreBooks(books: List<BooksEntity>) {
        tvSimularBooks.visible(true)
        rcMoreBook.fadeIn()
        postViewAction { moreBook.setData(books) }
        moreBook.notifyDataSetChanged()
    }

    private fun showDialogAddFavorite() {
        val view = layoutInflater.inflate(R.layout.item_add_favorite, null)
        presentor.addToFavorite()
        isAddedFavorite = false
        val dialog = createDialog(view, false)
        dialog.show()
        val animation = view.findViewById<LottieAnimationView>(R.id.animationView)
        animation.speed = 0.9f
        animation.postDelayed({
            animation.playAnimation()
        }, 300)
        Handler().postDelayed({
            dialog.dismiss()
            imgFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_red))
        }, 2000)

    }

    override fun showDialog() {
        showProgressDialog(false)
        val view = layoutInflater.inflate(R.layout.item_registration, null)
        val etNumber = view.findViewById<EditText>(R.id.etAbonentCode)
        val next = view.findViewById<Button>(R.id.btnNext)
        val cancel = view.findViewById<Button>(R.id.btnCancel)
        val dialog = createDialog(view, true)

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        next.setOnClickListener {
            if (etNumber.text.toString().trim().length == 13) {
                etNumber.error = null
                dialog.dismiss()
                activity?.hideSoftInput()
                activity?.hideKeyboard()
                showProgressDialog(true)
                presentor.buyBook(etNumber.text.toString(), item_counter.value)
            } else {
                etNumber.setError(getString(R.string.invalid_phone), null)
            }
        }

        if (etNumber.text.toString().length == 13) {
            etNumber.error = null
        } else {
            etNumber.setError(getString(R.string.invalid_phone), null)
        }

        isAddedFavorite = false
        dialog.create()
        dialog.show()
        Animation().Pulse(etNumber).start()
    }

    private fun createDialog(view: View, confiure: Boolean): Dialog {
        val dialog = Dialog(activity!!, R.style.Theme_Dim_Dialog)
        dialog.setContentView(view)
        dialog.setCancelable(confiure)
        dialog.setCanceledOnTouchOutside(confiure)
        dialog.window?.setLayout(
            if (!confiure) LinearLayout.LayoutParams.MATCH_PARENT
            else LinearLayout.LayoutParams.WRAP_CONTENT,
            if (!confiure) LinearLayout.LayoutParams.MATCH_PARENT
            else LinearLayout.LayoutParams.WRAP_CONTENT
        )

        dialog.window?.setGravity(Gravity.CENTER)
        return dialog
    }

    private fun getPhoneNumber(phone: String): String {
        if (phone.isEmpty() && phone.length < 4) return "+998"
        val _phone = phone.substring(4, phone.length)
        val length = _phone.length
        println(_phone)
        return "+998 " + when (length) {
            1 -> "(${_phone}"
            2 -> "(${_phone})"
            3 -> "(${_phone.substring(0, 2)}) ${_phone.substring(2, 3)}"
            4 -> "(${_phone.substring(0, 2)}) ${_phone.substring(2, 4)}"
            5 -> "(${_phone.substring(0, 2)}) ${_phone.substring(2, 5)}"
            6 -> "(${_phone.substring(0, 2)}) ${_phone.substring(2, 5)} ${_phone.substring(5, 6)}"
            7 -> "(${_phone.substring(0, 2)}) ${_phone.substring(2, 5)} ${_phone.substring(5, 7)}"
            8 -> "(${_phone.substring(0, 2)}) ${_phone.substring(2, 5)} ${_phone.substring(5, 8)}"
            9 -> "(${_phone.substring(0, 2)}) ${_phone.substring(2, 5)} ${_phone.substring(5, 9)}"
            else -> _phone
        }
    }

    override fun showError(message: String) {

    }

    override fun showSuccessful(message: String) {
        showProgressDialog(false)
        val dialog = AlertDialog.Builder(activity!!)
        dialog.setTitle(getString(R.string.successfull))
        dialog.setPositiveButton(getString(R.string.ok)
        ) { dialog, _ -> dialog?.dismiss() }
        dialog.setMessage(getString(R.string.info_success))
        dialog.create()
        dialog.show()
    }

    override fun onBackPressed() {
        presentor.onBackPressed()
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    companion object {
        private const val ID_BOOK = "id_book_dashboard"

        fun create(
            idBook: Int
        ) = DetailBookFragment().apply {
            arguments = Bundle().apply {
                putInt(ID_BOOK, idBook)
            }
        }
    }
}