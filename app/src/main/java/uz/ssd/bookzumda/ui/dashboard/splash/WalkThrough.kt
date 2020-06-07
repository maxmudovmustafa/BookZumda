package uz.ssd.bookzumda.ui.dashboard.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.intro_layout.*
import uz.ssd.bookzumda.R


class WalkThrough : Fragment() {

    private var position = 0
    private val mResources = intArrayOf(R.drawable.ic_walkthrought1,
        R.drawable.walk_2,
        R.drawable.group_39)

    fun newInstance(position: Int): WalkThrough {
        val fragment = WalkThrough()
        val arguments = Bundle()
        arguments.putInt("POSITION", position)
        fragment.arguments = arguments
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.intro_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args = arguments
        position = args!!.getInt("POSITION")

        introImage.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                mResources[position],
                null
            )
        )
        title.text = resources.getStringArray(R.array.splash_title)[position]
        description.text = resources.getStringArray(R.array.splash_detail)[position]
    }
}

