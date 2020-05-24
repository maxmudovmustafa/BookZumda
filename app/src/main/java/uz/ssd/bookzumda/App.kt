package uz.ssd.bookzumda

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import timber.log.Timber
import toothpick.Toothpick
import toothpick.configuration.Configuration
import uz.ssd.bookzumda.di.DI
import uz.ssd.bookzumda.di.module.AppModule
import uz.ssd.bookzumda.di.module.ServerModule
import uz.ssd.bookzumda.model.system.LocalManager

/**
 * Created by MrShoxruxbek on 21,May,2020
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
//        KBarcode.setDebugging(BuildConfig.DEBUG)
        initLogger()
        initStetho()
        initToothpick()
        initAppScope()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocalManager.setLocale(base))
    }

    override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        super.onConfigurationChanged(newConfig)
        LocalManager.setLocale(this)
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }
    }

    private fun initAppScope() {
        val appScope = Toothpick.openScope(DI.APP_SCOPE)
        appScope.installModules(AppModule(this))
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}