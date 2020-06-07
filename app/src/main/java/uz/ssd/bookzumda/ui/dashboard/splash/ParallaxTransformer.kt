package uz.ssd.bookzumda.ui.dashboard.splash

import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import uz.ssd.bookzumda.R
import kotlin.math.abs


class ParallaxTransformer : ViewPager.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        val absPosition = abs(position)
        when {
            position < -1 -> // This page is way off-screen to the left.
                view.alpha = 1f
            position <= 1 -> {
                val image = view.findViewById<ImageView>(R.id.introImage)
                image?.apply {
                    scaleX = 1.0f - absPosition * 2
                    scaleY = 1.0f - absPosition * 2
                    alpha = 1.0f - absPosition * 2
                }
                val shadow = view.findViewById<ImageView>(R.id.iv_bike_shadow)
                shadow?.apply {
                    scaleX = 1.0f - absPosition * 2
                    scaleY = 1.0f - absPosition * 2
                    alpha = 1.0f - absPosition * 2
                }
            }
            else -> // This page is way off-screen to the right.
                view.alpha = 1f
        }
    }
}

