package uz.ssd.bookzumda.ui.main

import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.aurelhubert.ahbottomnavigation.notification.AHNotification
import kotlinx.android.synthetic.main.fragment_main.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.terrakok.cicerone.android.support.SupportAppScreen
import uz.ssd.bookzumda.R
import uz.ssd.bookzumda.Screens
import uz.ssd.bookzumda.entity.AccountMainBadge
import uz.ssd.bookzumda.presentation.main.MainPresenter
import uz.ssd.bookzumda.presentation.main.MainView
import uz.ssd.bookzumda.ui.global.BaseFragment
import uz.ssd.bookzumda.ui.global.color
import uz.ssd.bookzumda.ui.global.visible

/**
 * Created by MrShoxruxbek on 22,May,2020
 */

@Suppress("DEPRECATION")
class MainFragment : BaseFragment(), MainView {
    override val layoutRes = R.layout.fragment_main

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter =
        scope.getInstance(MainPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bottomBar.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottomBar.setTitleTextSizeInSp(12F, 12F)
        AHBottomNavigationAdapter(activity, R.menu.main_bottom_menu).apply {
            setupWithBottomNavigation(bottomBar)
        }
        with(bottomBar) {
            accentColor = context.color(R.color.colorAccent)
            inactiveColor = context.color(R.color.grey)

            setOnTabSelectedListener { position, wasSelected ->
                if (!wasSelected) selectTab(
                    when (position) {
                        0 -> dashboardTab
                        1 -> sreachTab
                        2 -> categoryTab
                        else -> profileTab
                    }
                )
                true
            }
            val leftMargin =
                resources.getDimension(R.dimen.bottom_bar_notification_left_margin).toInt()
            setNotificationMarginLeft(leftMargin, leftMargin)
        }

        selectTab(
            when (currentTabFragment?.tag) {
                dashboardTab.screenKey -> dashboardTab
                sreachTab.screenKey -> sreachTab
                categoryTab.screenKey -> categoryTab
                profileTab.screenKey -> profileTab
                else -> dashboardTab
            }
        )
    }

    private fun selectTab(tab: SupportAppScreen) {
        val currentFragment = currentTabFragment
        val newFragment = childFragmentManager.findFragmentByTag(tab.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return

//        if(tab.screenKey == paymentTab.screenKey) {
//            startActivity(
//                AGRBilling.createP2PIntent(
//                    context!!,
//                    "998998578086"
//                )
//            )
//        } else {
        childFragmentManager.beginTransaction().apply {
            if (newFragment == null) add(
                R.id.mainScreenContainer,
                createTabFragment(tab),
                tab.screenKey
            )

            currentFragment?.let {
                hide(it)
                it.userVisibleHint = false
            }
            newFragment?.let {
                show(it)
                it.userVisibleHint = true
            }
        }.commitNow()
//        }
    }

    private fun createTabFragment(tab: SupportAppScreen) = tab.fragment

    override fun onBackPressed() {
        currentTabFragment?.onBackPressed()
    }

    override fun showBottomBar(show: Boolean) {
        if (show) {
            bottomBar.visible(show)
            bottomBar.restoreBottomNavigation(true)
        } else {
            bottomBar.hideBottomNavigation(false)
            bottomBar.visible(show)
        }
    }

    override fun setAssignedNotifications(badges: AccountMainBadge) {
        with(bottomBar) {
            setNotification(buildBottomBarNotification(R.color.fruit_salad, badges.issueCount), 1)
            setNotification(
                buildBottomBarNotification(
                    R.color.brandy_punch,
                    badges.mergeRequestCount
                ), 2
            )
            setNotification(buildBottomBarNotification(R.color.mariner, badges.todoCount), 3)
        }
    }

    private fun buildBottomBarNotification(@ColorRes backgroundColor: Int, count: Int): AHNotification {
        return if (count > 0) {
            AHNotification.Builder()
                .setBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColor))
                .setText(count.toString())
                .build()
        } else {
            AHNotification()
        }
    }

    companion object {
        private val dashboardTab = Screens.Dashboard
        private val sreachTab = Screens.Search
        private val categoryTab = Screens.Categories
        private val profileTab = Screens.Profile
    }
}