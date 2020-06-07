package uz.ssd.bookzumda.ui.dashboard.splash

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class WalkThroughPager(fragmentManager: FragmentManager?, private val mResources: Int) :
        FragmentPagerAdapter(fragmentManager!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = WalkThrough().newInstance(position)

    override fun getCount(): Int = mResources
}