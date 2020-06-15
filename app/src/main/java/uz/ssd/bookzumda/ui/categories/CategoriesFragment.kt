package uz.ssd.bookzumda.ui.categories

import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_category.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick
import uz.ssd.bookzumda.R
import uz.ssd.bookzumda.model.entity.Tuple5
import uz.ssd.bookzumda.model.entity.category.CategoryEntity
import uz.ssd.bookzumda.presentation.categories.CategoriesPresenter
import uz.ssd.bookzumda.presentation.categories.CategoryViewView
import uz.ssd.bookzumda.ui.categories.ls.CategoryListList
import uz.ssd.bookzumda.ui.global.*

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class CategoriesFragment : BaseFragment(), CategoryViewView {
    override val layoutRes = R.layout.fragment_category

    @InjectPresenter
    lateinit var presentor: CategoriesPresenter

    @ProvidePresenter
    fun getProviderPresenter(): CategoriesPresenter =
        scope.getInstance(CategoriesPresenter::class.java)

    private val adapter: CategoryListList by lazy {
        CategoryListList { presentor.onClickedBook(it) }
    }

    private var backgroundColor = ArrayList<Int>()
    private var backgroundDrawable = ArrayList<Int>()
    private var category = emptyArray<CategoryEntity>()
    private var textTitles = ArrayList<String>()
    private var textDetail = ArrayList<String>()
    private var items = ArrayList<Tuple5>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.apply {

            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(false)
            visible(false)
            adapter = this@CategoriesFragment.adapter
        }

        items.clear()
//        getDrawables()

        Handler().postDelayed({
            recyclerView.fadeIn()
            fullscreenProgressView.fadeOut()
        }, 2000)

        imgBackground.background.alpha = 45
        imgBackground2.background.alpha = 95 // optimal yaxshisi
        imgBackground3.background.alpha = 95
        imgBackground4.background.alpha = 95
        imgBackground5.background.alpha = 95
        imgBackground6.background.alpha = 95
        imgBackground7.background.alpha = 45
        imgBackground8.background.alpha = 95
    }

    override fun onBackPressed() {
        presentor.onBackPressed()
    }

    override fun showProgress(show: Boolean) {

    }

    override fun showBooks(books: List<CategoryEntity>) {
        category = books.toTypedArray()
        getDrawables()
    }

    override fun showError(message: String) {
    }

    override fun showSuccessful(message: String) {
    }

    private fun getDrawables() {

//        items.add(
//            Tuple5(
//                R.drawable.bg_button_blue_grey,
//                R.drawable.ic_bg_category_9,
//                "Knigi",
//                "Common Common 70",
//                null
//            )
//        )
//
//        items.add(
//            Tuple5(
//                R.drawable.bg_button_geen,
//                R.drawable.ic_bg_category_2,
//                "Knigi",
//                "Common Common 70",
//                null
//            )
//        )

        backgroundDrawable.add(R.drawable.bg_button_blue_grey)
        backgroundColor.add(R.drawable.ic_bg_category_9)
//        textTitles.add("Knigi")
//        textDetail.add("Common Common 70")
//
        backgroundDrawable.add(R.drawable.bg_button_geen)
        backgroundColor.add(R.drawable.ic_bg_category_2)
//        textTitles.add("Knigi")
//        textDetail.add("Common Common 70")

//        items.add(
//            Tuple5(
//                R.drawable.bg_button_yellow,
//                R.drawable.ic_bg_category_3,
//                "Knigi",
//                "Common Common 70",
//                null
//            )
//        )

        backgroundDrawable.add(R.drawable.bg_button_yellow)
        backgroundColor.add(R.drawable.ic_bg_category_3)
//        textTitles.add("Knigi")
//        textDetail.add("Common Common 70")

//        items.add(
//            Tuple5(
//                R.drawable.bg_button_fialetiviy,
//                R.drawable.ic_bg_category_9,
//                "Knigi",
//                "Common Common 70",
//                null
//            )
//        )

        backgroundDrawable.add(R.drawable.bg_button_fialetiviy)
        backgroundColor.add(R.drawable.ic_bg_category_9)
//        textTitles.add("Knigi")
//        textDetail.add("Common Common 70")


//        items.add(
//            Tuple5(
//                R.drawable.bg_button_grey,
//                R.drawable.ic_bg_category_5,
//                "Knigi",
//                "Common Common 70",
//                null
//            )
//        )


        backgroundDrawable.add(R.drawable.bg_button_grey)
        backgroundColor.add(R.drawable.ic_bg_category_5)
//        textTitles.add("Knigi")
//        textDetail.add("Common Common 70")

//        items.add(
//            Tuple5(
//                R.drawable.bg_button_blue,
//                R.drawable.ic_bg_category_6,
//                "Knigi",
//                "Common Common 70",
//                null
//            )
//        )
//
        backgroundDrawable.add(R.drawable.bg_button_blue)
        backgroundColor.add(R.drawable.ic_bg_category_6)
//        textTitles.add("Knigi")
//        textDetail.add("Common Common 70")

//        items.add(
//            Tuple5(
//                R.drawable.bg_button_blue_grey,
//                R.drawable.ic_bg_category_7,
//                "Knigi",
//                "Common Common 70",
//                null
//            )
//        )
//
        backgroundDrawable.add(R.drawable.bg_button_blue_grey)
        backgroundColor.add(R.drawable.ic_bg_category_7)
//        textTitles.add("Knigi")
//        textDetail.add("Common Common 70")

//        items.add(
//            Tuple5(
//                R.drawable.bg_button_yellow,
//                R.drawable.ic_bg_category_8,
//                "Knigi",
//                "Common Common 70",
//                null
//            )
//        )
//
        backgroundDrawable.add(R.drawable.bg_button_yellow)
        backgroundColor.add(R.drawable.ic_bg_category_8)
//        textTitles.add("Knigi")
//        textDetail.add("Common Common 70")
        backgroundDrawable.add(R.drawable.bg_button_blue_grey)
        backgroundColor.add(R.drawable.ic_bg_category_9)

        backgroundDrawable.add(R.drawable.bg_button_geen)
        backgroundColor.add(R.drawable.ic_bg_category_2)

        category.forEach {
            items.add(
                Tuple5(
                    backgroundDrawable[if (it.idColumn >= 8) it.idColumn - 8 else it.idColumn],
                    backgroundColor[if (it.idColumn >= 8) it.idColumn - 8 else it.idColumn],
                    it.amount.toString(),
                    it.name,
                    it
                )
            )
        }

        postViewAction { adapter.setData(items) }
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

}