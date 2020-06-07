package uz.ssd.bookzumda.ui.dashboard.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.fragment_splash.*
import uz.ssd.bookzumda.R
import uz.ssd.bookzumda.model.data.storage.Prefs
import uz.ssd.bookzumda.ui.AppActivity

class SplashActivity : AppCompatActivity(R.layout.fragment_splash) {
    lateinit var adapter: WalkThroughPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)

        if(Prefs(this, Moshi.Builder().build()).item.isNotEmpty()) {
            startActivity(Intent(this, AppActivity::class.java))
            finish()
        }
        startBtn.setOnClickListener {
            startActivity(Intent(this, AppActivity::class.java))
            finish()
        }

        adapter = WalkThroughPager(supportFragmentManager, 3)
        viewPager.adapter = adapter

        val pageTransformer = ParallaxTransformer()
        viewPager.setPageTransformer(true, pageTransformer)

        tabLayout.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                val blue = resources.getDrawable(R.drawable.walkthourght)
                val silver = resources.getDrawable(R.drawable.walkthought2)
                when (position) {
                    0 -> {
                        v_1.background = blue
                        v_2.background = silver
                        v_3.background = silver
                    }
                    1 -> {
                        v_1.background = silver
                        v_2.background = blue
                        v_3.background = silver
                    }
                    2 -> {
                        v_1.background = silver
                        v_2.background = silver
                        v_3.background = blue
                    }
                }
            }
        })
    }
}