package uz.ssd.bookzumda.ui.profile

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.fragment_profile.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uz.ssd.bookzumda.R
import uz.ssd.bookzumda.presentation.profile.ProfilePresenter
import uz.ssd.bookzumda.presentation.profile.ProfileView
import uz.ssd.bookzumda.ui.global.BaseFragment
import uz.ssd.bookzumda.ui.global.color
import uz.ssd.bookzumda.ui.global.showSnackMessage

/**
 * Created by MrShoxruxbek on 22,May,2020
 */
class ProfileFragment : BaseFragment(), ProfileView {

    override val layoutRes = R.layout.fragment_profile

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter(): ProfilePresenter =
        scope.getInstance(ProfilePresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        layoutLogout.setOnClickListener { presenter.onClickLogout() }

        imgSave.setOnClickListener {
            presenter.saveData(tvName.text.toString(), tvPoneNumber.text.toString())
        }

        supportInfo.setOnClickListener {
            presenter.openInfo()
        }

        layoutBoughtBooks.setOnClickListener {
            presenter.showMyBooks()
        }

        layoutFavorite.setOnClickListener {
            presenter.showFavorites()
        }
    }

    override fun showDetails(details: ProfileView.Details) {
        if (details.fullName.isNotEmpty())
            tvName.setText(details.fullName)
        if (details.phoneNumber.isNotEmpty())
            tvPoneNumber.setText(details.phoneNumber)
    }

    override fun showLogoutConfirmDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage(R.string.common)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.ps_logout) { _, _ ->
                presenter.onClickConfirmLogout()
            }.create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(requireContext().color(R.color.red))
        }

        dialog.show()
    }

    override fun showSuccessDialog() {
        val view = layoutInflater.inflate(R.layout.item_successfull, null)
        val dialog = createDialog(view, false)
        dialog.show()
        val animation = view.findViewById<LottieAnimationView>(R.id.animationView)
        animation.speed = 0.9f
        animation.postDelayed({
            animation.playAnimation()
        }, 300)
        Handler().postDelayed({
            dialog.dismiss()
        }, 2000)

    }

    private fun createDialog(view: View, cancel : Boolean): Dialog {
        val dialog = Dialog(activity!!, R.style.Theme_Dim_Dialog)
        dialog.setContentView(view)
        dialog.setCancelable(cancel)
        dialog.setCanceledOnTouchOutside(cancel)
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        dialog.window?.setGravity(Gravity.CENTER)
        return dialog
    }

    override fun showMessage(message: String) {
        showSnackMessage(message)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}
