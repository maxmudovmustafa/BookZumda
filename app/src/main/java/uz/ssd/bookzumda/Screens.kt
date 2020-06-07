package uz.ssd.bookzumda

import ru.terrakok.cicerone.android.support.SupportAppScreen
import uz.ssd.bookzumda.ui.auth.LoginFragment
import uz.ssd.bookzumda.ui.categories.CategoriesFragment
import uz.ssd.bookzumda.ui.dashboard.DashboardFragment
import uz.ssd.bookzumda.ui.dashboard.detail.DetailBookFragment
import uz.ssd.bookzumda.ui.main.MainFlowFragment
import uz.ssd.bookzumda.ui.main.MainFragment
import uz.ssd.bookzumda.ui.profile.MyBooksFragment
import uz.ssd.bookzumda.ui.profile.ProfileFragment
import uz.ssd.bookzumda.ui.profile.info.InfoFragment
import uz.ssd.bookzumda.ui.search.SearchsFragment

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
object Screens {


    object MainFlow : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

    // Screens
    object Main : SupportAppScreen() {
        override fun getFragment() = MainFragment()
    }

    object Dashboard : SupportAppScreen() {
        override fun getFragment() = DashboardFragment()
    }

    object Search : SupportAppScreen() {
        override fun getFragment() = SearchsFragment()
    }

    object Categories : SupportAppScreen() {
        override fun getFragment() = CategoriesFragment()
    }

    object Profile : SupportAppScreen() {
        override fun getFragment() = ProfileFragment()
    }

   object Login : SupportAppScreen() {
        override fun getFragment() = LoginFragment()
    }

   object Info : SupportAppScreen() {
        override fun getFragment() = InfoFragment()
    }

    data class MyBooks(val type: Int) : SupportAppScreen() {
        override fun getFragment() = MyBooksFragment.create(type)
    }

    data class DetailBook(val id_book: Int): SupportAppScreen() {
        override fun getFragment() = DetailBookFragment.create(id_book)
    }

}