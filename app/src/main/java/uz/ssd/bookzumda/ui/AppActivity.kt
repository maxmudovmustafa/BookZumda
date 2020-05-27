package uz.ssd.bookzumda.ui

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import io.reactivex.disposables.Disposable
import moxy.MvpAppCompatActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import toothpick.Toothpick
import uz.ssd.bookzumda.R
import uz.ssd.bookzumda.di.DI
import uz.ssd.bookzumda.model.system.message.SystemMessageNotifier
import uz.ssd.bookzumda.model.system.message.SystemMessageType
import uz.ssd.bookzumda.presentation.AppLauncher
import uz.ssd.bookzumda.ui.global.BaseFragment
import uz.ssd.bookzumda.ui.global.MessageDialogFragment
import javax.inject.Inject

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
class AppActivity: MvpAppCompatActivity() {

    @Inject
    lateinit var appLauncher: AppLauncher

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var systemMessageNotifier: SystemMessageNotifier

    private var notifierDisposable: Disposable? = null

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    private val navigator: Navigator =
        object : SupportAppNavigator(this, supportFragmentManager, R.id.container) {
            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                // Fix incorrect order lifecycle callback of MainFragment
                fragmentTransaction.setReorderingAllowed(true)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        Toothpick.inject(this, Toothpick.openScope(DI.APP_SCOPE))
        appLauncher.onLaunch()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_container)

        if (savedInstanceState == null) {
            appLauncher.coldStart()
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        subscribeOnSystemMessages()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        unsubscribeOnSystemMessages()
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    private fun showAlertMessage(message: String) {
        MessageDialogFragment.create(
            message = message
        ).show(supportFragmentManager, null)
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun subscribeOnSystemMessages() {
        notifierDisposable = systemMessageNotifier.notifier
            .subscribe { msg ->
                when (msg.type) {
                    SystemMessageType.ALERT -> showAlertMessage(msg.text)
                    SystemMessageType.TOAST -> showToastMessage(msg.text)
                }
            }
    }

    private fun unsubscribeOnSystemMessages() {
        notifierDisposable?.dispose()
    }
}