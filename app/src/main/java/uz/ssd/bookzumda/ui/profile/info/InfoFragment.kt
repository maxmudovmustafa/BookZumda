package uz.ssd.bookzumda.ui.profile.info

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import com.davemorrissey.labs.subscaleview.ImageSource
//import com.yandex.mapkit.mapview.MapView
import kotlinx.android.synthetic.main.fragment_info_dialog.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uz.ssd.bookzumda.BuildConfig
import uz.ssd.bookzumda.R
import uz.ssd.bookzumda.presentation.profile.info.InfoPresenter
import uz.ssd.bookzumda.presentation.profile.info.InfoView
import uz.ssd.bookzumda.ui.global.BaseFragment


/**
 * Created by MrShoxruxbek on 31,May,2020
 */
class InfoFragment : BaseFragment(), InfoView {
    override val layoutRes: Int
        get() = R.layout.fragment_info_dialog

    @InjectPresenter
    lateinit var presenter: InfoPresenter

    @ProvidePresenter
    fun providePresenter(): InfoPresenter =
        scope.getInstance(InfoPresenter::class.java)

//    private var mapview: MapView? = null

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvAppVersion.text =
            getString(R.string.app_name_version) + " " + BuildConfig.VERSION_NAME

        val phoneNumber = getColoredSpanned("+(99899) 857-80-86", "#aa1f1f1f")
        val phonce = tvPhone.text.toString() + "\n"+ phoneNumber
        tvPhone.text = Html.fromHtml(phonce)

        val email = getColoredSpanned("shohruh.maxmudov@mail.ru", "#aa1f1f1f")
        val mail = tvAddress.text.toString() + "\n"+ email
        tvAddress.text = Html.fromHtml(mail)

        imageView.setImage(ImageSource.resource(R.drawable.geo_location))

        toolbar.setOnClickListener {
            presenter.onBackPressed()
        }
    }


    private fun getColoredSpanned(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }


    override fun showInfo(boolean: Boolean) {

    }

    override fun showMessage(message: String) {
    }

}